package com.auctionwebsite.service;

import com.auctionwebsite.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(int id);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategoryById(CategoryDTO categoryDTO, int id);

    CategoryDTO deleteCategoryById(int id);
}
