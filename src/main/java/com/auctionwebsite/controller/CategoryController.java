package com.auctionwebsite.controller;

import com.auctionwebsite.dto.CategoryDTO;
import com.auctionwebsite.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    //Mapping name
    @GetMapping
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    //Mapping name
    @GetMapping(value = "/{categoryId}")
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryById(@PathVariable int categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    //Mapping name
    @PostMapping
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    //Mapping name
    @PutMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO updateCategory(@PathVariable int categoryId, @RequestBody CategoryDTO categoryDTO) {
        return categoryService.updateCategoryById(categoryDTO, categoryId);
    }

    //Mapping name
    @DeleteMapping(value = "/{categoryId}")
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CategoryDTO deleteCategoryById(@PathVariable int categoryId) {
        return categoryService.deleteCategoryById(categoryId);
    }
}
