package com.auctionwebsite.service.impl;

import com.auctionwebsite.dto.CategoryDTO;
import com.auctionwebsite.model.Category;
import com.auctionwebsite.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
    private static final int ID_VALUE = 1;
    private Category firstCategory;
    private Category secondCategory;
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository);

        firstCategory = new Category();
        firstCategory.setId(ID_VALUE);
        firstCategory.setName("Test");
        firstCategory.setDescription("PC");

        secondCategory = new Category();
        secondCategory.setId(ID_VALUE);
        secondCategory.setName("Test");
        secondCategory.setDescription("PC");
    }

    @Test
    void getAllCategories() {
        //given
        List<Category> getAllCategories = new ArrayList<>();
        getAllCategories.add(firstCategory);
        getAllCategories.add(secondCategory);
        //when
        when(categoryRepository.findAll()).thenReturn(getAllCategories);
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();
        //then
        assertEquals(getAllCategories.size(), categoryDTOList.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getCategoryById() {
        //when
        when(categoryRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstCategory));
        CategoryDTO categoryDTO = categoryService.getCategoryById(ID_VALUE);
        //then
        assertEquals(firstCategory.getId(), categoryDTO.getId());
        assertEquals(firstCategory.getName(), categoryDTO.getName());
        assertEquals(firstCategory.getDescription(), categoryDTO.getDescription());
        verify(categoryRepository, times(1)).findById(ID_VALUE);
    }

    @Test
    void deleteCategoryById() {
        //when
        when(categoryRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstCategory));
        categoryRepository.deleteById(ID_VALUE);
        //then
        verify(categoryRepository, times(1)).deleteById(ID_VALUE);
        CategoryDTO categoryDTO = categoryService.deleteCategoryById(ID_VALUE);
        assertEquals(firstCategory.getId(), categoryDTO.getId());
        assertEquals(firstCategory.getName(), categoryDTO.getName());
        assertEquals(firstCategory.getDescription(), categoryDTO.getDescription());
    }
    @Test
    void updateAddressById() {
        //given
        CategoryDTO dto = new CategoryDTO();
        dto.setId(ID_VALUE);
        dto.setName("Test");
        dto.setDescription("PC");
        //when
        when(categoryRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstCategory));
        when(categoryRepository.save(firstCategory)).thenReturn(firstCategory);
        CategoryDTO updatedCategory = categoryService.updateCategoryById(dto, ID_VALUE);
        //then
        assertNotNull(updatedCategory);
        assertEquals(firstCategory.getId(), updatedCategory.getId());
        assertEquals(firstCategory.getName(), updatedCategory.getName());
        assertEquals(firstCategory.getDescription(), updatedCategory.getDescription());
        verify(categoryRepository, times(1)).save(firstCategory);
    }
    @Test
    void createAddress() {
        //given
        CategoryDTO dto = new CategoryDTO();
        dto.setId(ID_VALUE);
        dto.setName("Test");
        dto.setDescription("PC");
        //when
        when(categoryRepository.save(firstCategory)).thenReturn(firstCategory);
        CategoryDTO categoryDTO = categoryService.createCategory(dto);
        //then
        assertNotNull(firstCategory);
        assertEquals(firstCategory.getId(), categoryDTO.getId());
        assertEquals(firstCategory.getName(), categoryDTO.getName());
        assertEquals(firstCategory.getDescription(), categoryDTO.getDescription());
    }
}
