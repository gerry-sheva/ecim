package com.dti.ecim.metadata.service.impl;

import com.dti.ecim.exceptions.DataNotFoundException;
import com.dti.ecim.metadata.entity.Category;
import com.dti.ecim.metadata.repository.CategoryRepository;
import com.dti.ecim.metadata.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category findById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            throw new DataNotFoundException("Category with id " + id + " not found");
        }
        return categoryOptional.get();
    }

    @Override
    public Category findByName(String name) {
        Optional<Category> categoryOptional = categoryRepository.findByName(name);
        if (categoryOptional.isEmpty()) {
            throw new DataNotFoundException("Category with name " + name + " not found");
        }
        return categoryOptional.get();
    }
}
