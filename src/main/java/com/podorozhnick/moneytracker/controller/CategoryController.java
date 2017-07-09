package com.podorozhnick.moneytracker.controller;

import com.podorozhnick.moneytracker.model.Category;
import com.podorozhnick.moneytracker.service.CategoryService;
import com.podorozhnick.moneytracker.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> getCategoryList() {
        List<Category> categoryList = categoryService.list();
        if (categoryList == null || categoryList.size() == 0) {
            return new ResponseEntity<>(null, null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(JsonUtils.toJson(categoryList), null, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addCategory(@RequestBody String jsonCategory) {
        Category category = JsonUtils.fromJson(Category.class, jsonCategory);
        if (category == null) {
            return new ResponseEntity<>("Bad Json", null, HttpStatus.BAD_REQUEST);
        }
        if (category.getParentId() != null) {
            Category parent = categoryService.getById(category.getParentId());
            if (parent == null) {
                return new ResponseEntity<>("Bad Json", null, HttpStatus.BAD_REQUEST);
            }
            category.setParentCategory(parent);
        }
        category = categoryService.add(category);
        return new ResponseEntity<>(JsonUtils.toJson(category), null, HttpStatus.CREATED);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<String> editCategory(@PathVariable Long id, @RequestBody String jsonCategory) {
        Category loadedCategory = categoryService.getById(id);
        if (loadedCategory == null) {
            return new ResponseEntity<>("Bad id", null, HttpStatus.BAD_REQUEST);
        }
        Category category = JsonUtils.fromJson(Category.class, jsonCategory);
        if (category == null) {
            return new ResponseEntity<>("Bad json", null, HttpStatus.BAD_REQUEST);
        }
        loadedCategory.setName(category.getName());
        loadedCategory.setType(category.getType());
        loadedCategory.setParentId(category.getParentId());
        if (loadedCategory.getParentId() != null) {
            Category parent = categoryService.getById(category.getParentId());
            if (parent == null) {
                return new ResponseEntity<>("Bad Json", null, HttpStatus.BAD_REQUEST);
            }
            loadedCategory.setParentCategory(parent);
        }
        categoryService.save(category);
        return new ResponseEntity<>(JsonUtils.toJson(category), null, HttpStatus.CREATED);
    }

}
