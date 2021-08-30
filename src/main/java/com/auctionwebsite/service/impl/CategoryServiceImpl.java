package com.auctionwebsite.service.impl;

import com.auctionwebsite.dto.CategoryDTO;
import com.auctionwebsite.exception.ApplicationException;
import com.auctionwebsite.exception.ExceptionType;
import com.auctionwebsite.mapper.CategoryMapper;
import com.auctionwebsite.mapper.NotificatorMappingContext;
import com.auctionwebsite.model.Category;
import com.auctionwebsite.repository.CategoryRepository;
import com.auctionwebsite.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> CategoryMapper.INSTANCE.toCategoryDto(category, new NotificatorMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(int id) {
        final Category getCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.CATEGORY_NOT_FOUND));
        return CategoryMapper.INSTANCE.toCategoryDto(getCategory, new NotificatorMappingContext());
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        final Category createCategory = CategoryMapper.INSTANCE.fromCategoryDto(categoryDTO, new NotificatorMappingContext());
        final Category saveCategory = categoryRepository.save(createCategory);
        return CategoryMapper.INSTANCE.toCategoryDto(saveCategory, new NotificatorMappingContext());
    }

    @Override
    public CategoryDTO updateCategoryById(CategoryDTO categoryDTO, int id) {
        final Category updateCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.CATEGORY_NOT_FOUND));
        updateCategory.setName(categoryDTO.getName());
        updateCategory.setDescription(categoryDTO.getDescription());
        categoryRepository.save(updateCategory);
        return CategoryMapper.INSTANCE.toCategoryDto(updateCategory, new NotificatorMappingContext());
    }

    @Override
    public CategoryDTO deleteCategoryById(int id) {
        final Category deleteCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.CATEGORY_NOT_FOUND));
        categoryRepository.delete(deleteCategory);
        return CategoryMapper.INSTANCE.toCategoryDto(deleteCategory, new NotificatorMappingContext());
    }
}
