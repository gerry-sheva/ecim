package com.dti.ecim.metadata.service.impl;

import com.dti.ecim.exceptions.DataNotFoundException;
import com.dti.ecim.metadata.entity.Interest;
import com.dti.ecim.metadata.repository.InterestRepository;
import com.dti.ecim.metadata.service.InterestService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log
public class InterestServiceImpl implements InterestService {
    private final InterestRepository interestRepository;

    @Override
    public Interest findById(Long id) {
        Optional<Interest> interestOptional = interestRepository.findById(id);
        if (interestOptional.isEmpty()) {
            throw new DataNotFoundException("Interest with id " + id + " not found");
        }
        return interestOptional.get();
    }

    @Override
    public Interest findByName(String name) {
        Optional<Interest> interestOptional = interestRepository.findByName(name);
        if (interestOptional.isEmpty()) {
            throw new DataNotFoundException("Interest with name " + name + " not found");
        }
        return interestOptional.get();
    }
}
