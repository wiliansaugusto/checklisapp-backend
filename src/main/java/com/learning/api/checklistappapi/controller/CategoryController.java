package com.learning.api.checklistappapi.controller;

import com.learning.api.checklistappapi.dto.CategoryDTO;
import com.learning.api.checklistappapi.entity.CategoryEntity;
import com.learning.api.checklistappapi.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController("/api/v1/checklist")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryDTO>> findAllCategories() {

        List<CategoryDTO> resp = StreamSupport.stream(
                        this.categoryService.findAllCategories().spliterator(), false
                ).map(categoryEntity -> CategoryDTO.toDTO(categoryEntity))
                .collect(Collectors.toList());

        return new ResponseEntity<List<CategoryDTO>>(resp, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addNewCategory(@RequestBody CategoryDTO categoryDTO) {

        CategoryEntity resp = categoryService.addNewCatergory(categoryDTO.getName());
        return new ResponseEntity<>(resp.getGuid(), HttpStatus.CREATED);

    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCategoruy(@RequestBody CategoryDTO categoryDTO) {

        this.categoryService.updateCategory(categoryDTO.getGuid(), categoryDTO.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping(value = "{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCategory(@PathVariable String guid){
        this.categoryService.deleteCategory(guid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
