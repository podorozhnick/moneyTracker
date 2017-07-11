package com.podorozhnick.moneytracker.controller;

import com.podorozhnick.moneytracker.db.model.Category;
import com.podorozhnick.moneytracker.service.CategoryService;
import com.podorozhnick.moneytracker.util.JsonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.podorozhnick.moneytracker.controller.ControllerAPI.CATEGORIES_CONTROLLER;
import static com.podorozhnick.moneytracker.controller.ControllerAPI.GENERAL_REQUEST;
import static com.podorozhnick.moneytracker.controller.ControllerAPI.ID_REQUEST;

@Controller
@RequestMapping(CATEGORIES_CONTROLLER)
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(GENERAL_REQUEST)
    public ResponseEntity<List<Category>> getCategoryList() {
        List<Category> categoryList = categoryService.list();
        if (CollectionUtils.isEmpty(categoryList)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        }
    }

    @PostMapping(GENERAL_REQUEST)
    public ResponseEntity<Category> addCategory(@RequestBody String jsonCategory) {
        Category category = JsonUtils.fromJson(Category.class, jsonCategory);
        if (category == null) {
            return new ResponseEntity("Bad Json", HttpStatus.BAD_REQUEST);
        }
        category = categoryService.add(category);
        return new ResponseEntity<>(category, HttpStatus.CREATED);

    }

    @PutMapping(ID_REQUEST)
    public ResponseEntity<Category> editCategory(@PathVariable Long id, @RequestBody String jsonCategory) {
        Category loadedCategory = categoryService.getById(id);
        if (loadedCategory == null) {
            return new ResponseEntity("Bad id", HttpStatus.BAD_REQUEST);
        }
        Category category = JsonUtils.fromJson(Category.class, jsonCategory);
        if (category == null) {
            return new ResponseEntity("Bad json", HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(category, loadedCategory);
        categoryService.save(loadedCategory);
        return new ResponseEntity<>(loadedCategory, HttpStatus.CREATED);
    }

}
