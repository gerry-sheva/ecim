package com.dti.ecim.metadata.service;

import com.dti.ecim.metadata.entity.Category;

public interface CategoryService {
    Category findById(Long id);
    Category findByName(String name);
}
