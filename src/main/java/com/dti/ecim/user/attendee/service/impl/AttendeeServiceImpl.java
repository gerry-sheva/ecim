package com.dti.ecim.user.attendee.service.impl;

import com.dti.ecim.auth.entity.UserAuth;
import com.dti.ecim.auth.repository.UserAuthRepository;
import com.dti.ecim.exceptions.DataNotFoundException;
import com.dti.ecim.user.attendee.dto.CreateAttendeeDto;
import com.dti.ecim.user.attendee.entity.Attendee;
import com.dti.ecim.user.attendee.repository.AttendeeRepository;
import com.dti.ecim.user.attendee.service.AttendeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log
public class AttendeeServiceImpl implements AttendeeService {
    private final AttendeeRepository attendeeRepository;
    private final UserAuthRepository userAuthRepository;

    @Override
    public Attendee createAttendee(CreateAttendeeDto createAttendeeDto) throws NoSuchAlgorithmException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserAuth> userAuthOptional = userAuthRepository.findByEmail(authentication.getName());
        if (userAuthOptional.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }
        Attendee newAttendee = new Attendee();
        newAttendee.setUser(userAuthOptional.get().getUser());
        newAttendee.setFname(createAttendeeDto.getFname());
        newAttendee.setLname(createAttendeeDto.getLname());
        newAttendee.setDob(parseDate(createAttendeeDto.getDob()));
        newAttendee.setRefCode(generateReferralCode(authentication.getName()));
        newAttendee.setPoints(0L);
        newAttendee.setContact(createAttendeeDto.getContact());
        return attendeeRepository.save(newAttendee);
    }

    private String generateReferralCode(String email) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(email.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash).substring(0, 8);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private LocalDate parseDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new DataNotFoundException("Invalid date format");
        }

    }
}
