package com.podorozhnick.moneytracker.controller;

import com.podorozhnick.moneytracker.TestHelper;
import com.podorozhnick.moneytracker.db.model.Category;
import com.podorozhnick.moneytracker.db.model.enums.CategoryType;
import com.podorozhnick.moneytracker.service.CategoryService;
import com.podorozhnick.moneytracker.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class CategoriesControllerTest extends ControllerTest {

    private MockMvc mockMvc;
    private static Random random = new Random(89);

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoriesController categoriesController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(categoriesController)
                .setMessageConverters(mappingJackson2HttpMessageConverter)
                .build();
    }

    @Test
    public void getCategoryListTest() throws Exception {
        String request = ControllerAPI.CATEGORIES_CONTROLLER.concat(ControllerAPI.GENERAL_REQUEST);
        List<Category> categoryList = new ArrayList<>();
        categoryList.addAll(Arrays.asList(createCategory(), createCategory(), createCategory()));
        when(categoryService.list()).thenReturn(categoryList);
        mockMvc.perform(get(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(JsonUtils.toJson(categoryList)));
        verify(categoryService, times(1)).list();
        verifyNoMoreInteractions(categoryService);
    }

    private Category createCategory() {
        Category category = new Category();
        category.setType(CategoryType.values()[random.nextInt(CategoryType.values().length)]);
        category.setName(TestHelper.getRandomName());
        return category;
    }



}
