package com.podorozhnick.moneytracker.controller;

import com.podorozhnick.moneytracker.model.CategoryRelations;
import com.podorozhnick.moneytracker.service.CategoryRelationsService;
import com.podorozhnick.moneytracker.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("relations")
public class CategoryRelationsController {

    @Autowired
    private CategoryRelationsService categoryRelationsService;

    @RequestMapping(value = "{id}", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> getCategoryRelations(@PathVariable Long id) {
        CategoryRelations categoryRelations = categoryRelationsService.getByCategoryId(id);
        if (categoryRelations == null) {
            return new ResponseEntity<>("Bad Json", null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(JsonUtils.toJson(categoryRelations), null, HttpStatus.OK);
    }

    @RequestMapping(value = "test/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> test(@PathVariable Long id) {
        return new ResponseEntity<>("test", null, HttpStatus.CREATED);
    }

}
