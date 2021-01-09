package com.auctionwebsite.controller;

import com.auctionwebsite.dto.CategoryDTO;
import com.auctionwebsite.service.impl.CategoryServiceImpl;
import com.auctionwebsite.utils.ExceptionController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//This type of testing is used in case when we don't have any kind of security in our REST API
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CategoryController.class)
public class CategoryControllerTest {
    private static final int ID_VALUE = 1;
    @Autowired
    private CategoryController categoryController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryServiceImpl categoryService;
    private CategoryDTO firstCategory;
    private CategoryDTO secondCategory;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        //setup the controller to MockMvc in order to have access to the information from the REST API
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new ExceptionController())
                .alwaysExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .build();
        //Inserting the data in order to be able to do the test of the endpoints
        firstCategory = new CategoryDTO();
        firstCategory.setId(ID_VALUE);
        firstCategory.setName("Test");
        firstCategory.setDescription("PC");

        secondCategory = new CategoryDTO();
        secondCategory.setId(ID_VALUE);
        secondCategory.setName("Test");
        secondCategory.setDescription("PC");
    }

    @Test
    void getAllCategories() throws Exception {
        //given
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categoryDTOList.add(firstCategory);
        categoryDTOList.add(secondCategory);
        //when
        when(categoryService.getAllCategories()).thenReturn(categoryDTOList);
        //then
        mockMvc.perform(get("/api/v1/categories")).andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON)).
                andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getCategoryById() throws Exception {
        //when
        when(categoryService.getCategoryById(anyInt())).thenReturn(firstCategory);
        //then
        this.mockMvc.perform(get("/api/v1/categories/{categoryId}", ID_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(ID_VALUE)));
    }

    @Test
    void updateCategoryById() throws Exception {
        //when
        when(categoryService.updateCategoryById(any(), anyInt())).thenReturn(secondCategory);
        //then
        this.mockMvc.perform(put("/api/v1/categories/{categoryId}", firstCategory.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(secondCategory)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("name", Matchers.is(secondCategory.getName())));
    }

    @Test
    void deleteCategoryById() throws Exception {
        //when
        when(categoryService.deleteCategoryById(ID_VALUE)).thenReturn(firstCategory);
        //then
        this.mockMvc.perform(delete("/api/v1/categories/{categoryId}", firstCategory.getId()))
                .andExpect(status().is2xxSuccessful());
        verify(categoryService, times(1)).deleteCategoryById(ID_VALUE);
    }

    @Test
    void createCategory() throws Exception {
        //when
        when(categoryService.createCategory(Mockito.any(CategoryDTO.class))).thenReturn(firstCategory);
        //then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/categories").
                contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(firstCategory));
        mockMvc.perform(builder).andExpect(status().isCreated()).andExpect(jsonPath("$.name", is("Test"))).
                andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(firstCategory)));
    }
}
