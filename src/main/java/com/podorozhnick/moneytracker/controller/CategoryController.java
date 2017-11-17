package com.podorozhnick.moneytracker.controller;

import com.podorozhnick.moneytracker.controller.exception.BadRequestException;
import com.podorozhnick.moneytracker.controller.exception.ErrorMessage;
import com.podorozhnick.moneytracker.controller.exception.NoContentException;
import com.podorozhnick.moneytracker.controller.exception.RestException;
import com.podorozhnick.moneytracker.db.model.Category;
import com.podorozhnick.moneytracker.pojo.search.CategorySearchFilter;
import com.podorozhnick.moneytracker.pojo.search.CategorySearchResult;
import com.podorozhnick.moneytracker.service.CategoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.podorozhnick.moneytracker.controller.ControllerAPI.*;

@Controller
@RequestMapping(CATEGORIES_CONTROLLER)
public class CategoryController {

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

    @PostMapping(CATEGORIES_CONTROLLER_FILTER)
    public ResponseEntity<CategorySearchResult> filterCategories(@RequestBody @Valid CategorySearchFilter categorySearchFilter) {
        CategorySearchResult result = categoryService.filter(categorySearchFilter);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(GENERAL_REQUEST)
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        category = categoryService.add(category);
        return new ResponseEntity<>(category, HttpStatus.CREATED);

    }

    @PutMapping(ID_REQUEST)
    public ResponseEntity<Category> editCategory(@PathVariable Long id, @RequestBody Category category) throws RestException {
        if (!categoryService.isExistsById(id)) {
            throw new BadRequestException(new ErrorMessage("Bad id"));
        }
        category = categoryService.update(category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping(ID_REQUEST)
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) throws RestException {
        Category category = categoryService.getById(id);
        if (category == null) {
            throw new BadRequestException(new ErrorMessage("Bad id"));
        }
        category = categoryService.delete(category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

}
