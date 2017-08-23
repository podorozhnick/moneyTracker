package com.podorozhnick.moneytracker.controller;

import com.podorozhnick.moneytracker.controller.exception.BadRequestException;
import com.podorozhnick.moneytracker.controller.exception.ErrorMessage;
import com.podorozhnick.moneytracker.controller.exception.NoContentException;
import com.podorozhnick.moneytracker.controller.exception.RestException;
import com.podorozhnick.moneytracker.db.model.Category;
import com.podorozhnick.moneytracker.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.podorozhnick.moneytracker.controller.ControllerAPI.*;

@Controller
@RequestMapping(CATEGORIES_CONTROLLER)
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(GENERAL_REQUEST)
    public ResponseEntity<List<Category>> getCategoryList() throws RestException {
        List<Category> categoryList = categoryService.list();
        if (CollectionUtils.isEmpty(categoryList)) {
            throw new NoContentException();
        }
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    @PostMapping(GENERAL_REQUEST)
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        category = categoryService.add(category);
        return new ResponseEntity<>(category, HttpStatus.CREATED);

    }

    @PutMapping(ID_REQUEST)
    public ResponseEntity<Category> editCategory(@PathVariable Long id, @RequestBody Category category) throws RestException {
        Category loadedCategory = categoryService.getById(id);
        if (loadedCategory == null) {
            throw new BadRequestException(new ErrorMessage("Bad id"));
        }
        BeanUtils.copyProperties(category, loadedCategory);
        categoryService.save(loadedCategory);
        return new ResponseEntity<>(loadedCategory, HttpStatus.CREATED);
    }

}
