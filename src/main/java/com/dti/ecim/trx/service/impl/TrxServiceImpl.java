package com.dti.ecim.trx.service.impl;

import com.dti.ecim.auth.dto.UserIdResponseDto;
import com.dti.ecim.auth.service.AuthService;
import com.dti.ecim.discount.dto.ProcessDiscountRequestDto;
import com.dti.ecim.discount.dto.ProcessDiscountResponseDto;
import com.dti.ecim.discount.service.DiscountService;
import com.dti.ecim.event.service.EventService;
import com.dti.ecim.exceptions.DataNotFoundException;
import com.dti.ecim.trx.entity.Tix;
import com.dti.ecim.trx.dto.CreateTixDto;
import com.dti.ecim.trx.dto.CreateTrxRequestDto;
import com.dti.ecim.trx.dto.TrxResponseDto;
import com.dti.ecim.trx.entity.Trx;
import com.dti.ecim.trx.enums.Status;
import com.dti.ecim.trx.enums.TimeSpecifier;
import com.dti.ecim.trx.helper.TrxHelper;
import com.dti.ecim.trx.repository.TrxRepository;
import com.dti.ecim.trx.repository.TrxSpecifications;
import com.dti.ecim.trx.service.TrxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Log
@Validated
public class TrxServiceImpl implements TrxService {
    private final TrxRepository trxRepository;
    private final EventService eventService;
    private final AuthService authService;
    private final DiscountService discountService;
    private final ModelMapper modelMapper;


    @Override
    public TrxResponseDto retrieveTrx(Long trxId) {
        UserIdResponseDto userIdResponseDto = authService.getCurrentUserId();
        Optional<Trx> trxOptional = switch (userIdResponseDto.getRole()) {
            case ATTENDEE -> trxRepository.findByIdAndAttendeeId(trxId, userIdResponseDto.getId());
            case ORGANIZER -> trxRepository.findByIdAndOrganizerId(trxId, userIdResponseDto.getId());
            default -> throw new IllegalStateException("Unexpected role: " + userIdResponseDto.getRole());
        };
        if (trxOptional.isEmpty()) {
            throw new DataNotFoundException("Trx with id " + trxId + " not found");
        }
        return modelMapper.map(trxOptional.get(), TrxResponseDto.class);
    }

    @Override
    @Transactional
    public TrxResponseDto createTrx(CreateTrxRequestDto createTrxRequestDto) throws NoSuchAlgorithmException, BadRequestException {
        int totalPrice = 0;
        UserIdResponseDto userIdResponseDto = authService.getCurrentUserId();
        Trx trx = new Trx();
        trx.setEventId(createTrxRequestDto.getEventId());
        trx.setOrganizerId(createTrxRequestDto.getOrganizerId());
        trx.setAttendeeId(userIdResponseDto.getId());
        trx.setStatus(Status.WAITING_PAYMENT);
        Set<CreateTixDto> tixDtos = createTrxRequestDto.getTixes();
        for (CreateTixDto tixDto : tixDtos) {
            var eventOffering = eventService.getEventOffering(tixDto.getOfferingId());
            if (eventOffering.getAvailability() < tixDto.getQuantity()) {
                throw new BadRequestException("Offering is not available");
            }
            if (!Objects.equals(eventOffering.getEvent().getId(), createTrxRequestDto.getEventId())) {
                throw new BadRequestException("Invalid event offering");
            }
            for (int i = 0; i < tixDto.getQuantity(); i++) {
                Tix tix = new Tix();
                tix.setCode(TrxHelper.generateTixCode(eventOffering.getName()));
                tix.setEventOffering(eventOffering);
                trx.addTix(tix);
                totalPrice += eventOffering.getPrice();
            }
            eventOffering.setAvailability(eventOffering.getAvailability() - tixDto.getQuantity());
            eventService.updateOffering(eventOffering);
        }
        trx.setPrice(totalPrice);
        if (createTrxRequestDto.getDiscountId() != null || createTrxRequestDto.isPoint())  {
            ProcessDiscountResponseDto processDiscountResponseDto = discountService.processDiscount(
                    new ProcessDiscountRequestDto(
                            createTrxRequestDto.getDiscountId(),
                            createTrxRequestDto.getEventId(),
                            createTrxRequestDto.isPoint(),
                            totalPrice
                    ));
            trx.setDiscountValue(processDiscountResponseDto.getDiscountValue());
            trx.setFinalPrice(processDiscountResponseDto.getFinalPrice());
            trx.setDiscountId(trx.getDiscountId());
        } else {
            trx.setDiscountValue(0);
            trx.setFinalPrice(totalPrice);
        }
        Trx savedTrx = trxRepository.save(trx);
        return modelMapper.map(savedTrx, TrxResponseDto.class);
    }

    @Override
    public Page<TrxResponseDto> retrieveAllTrx(Pageable pageable, Instant date, TimeSpecifier timeSpecifier) {
        UserIdResponseDto userIdResponseDto = authService.getCurrentUserId();
        log.info(userIdResponseDto.getRole().name());
        Specification<Trx> specification = switch (userIdResponseDto.getRole()) {
            case ATTENDEE -> Specification.where(TrxSpecifications.byTime(date, timeSpecifier))
                    .and(TrxSpecifications.byAttendeeId(userIdResponseDto.getId()));
            case ORGANIZER -> Specification.where(TrxSpecifications.byTime(date, timeSpecifier))
                    .and(TrxSpecifications.byOrganizerId(userIdResponseDto.getId()));
            default -> throw new IllegalStateException("Unexpected role: " + userIdResponseDto.getRole());
        };

        var trxPage = trxRepository.findAll(specification, pageable);

        return trxPage.map(trx -> modelMapper.map(trx, TrxResponseDto.class));
    }
}
