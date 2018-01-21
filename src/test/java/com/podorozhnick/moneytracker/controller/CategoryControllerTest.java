package com.podorozhnick.moneytracker.controller;

import com.podorozhnick.moneytracker.TestHelper;
import com.podorozhnick.moneytracker.controller.advice.RestErrorHandler;
import com.podorozhnick.moneytracker.controller.exception.ErrorMessage;
import com.podorozhnick.moneytracker.controller.exception.NoContentException;
import com.podorozhnick.moneytracker.db.model.Category;
import com.podorozhnick.moneytracker.db.model.enums.CategoryType;
import com.podorozhnick.moneytracker.service.CategoryService;
import com.podorozhnick.moneytracker.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class CategoryControllerTest extends ControllerTest {

    private MockMvc mockMvc;
    private static Random random = new Random(89);

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private static RestErrorHandler restErrorHandler;

    @BeforeClass
    public static void beforeClass() {
        restErrorHandler = new RestErrorHandler();
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(categoryController)
                .setMessageConverters(mappingJackson2HttpMessageConverter)
                .setControllerAdvice(restErrorHandler)
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

    @Test
    public void getCategoryListWithEmptyListTest() throws Exception {
        String request = ControllerAPI.CATEGORIES_CONTROLLER.concat(ControllerAPI.GENERAL_REQUEST);
        when(categoryService.list()).thenReturn(new ArrayList<>());
        mockMvc.perform(get(request))
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(JsonUtils.toJson(new NoContentException().getErrorMessage())));
        verify(categoryService, times(1)).list();
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void getCategoryListWithNullTest() throws Exception {
        String request = ControllerAPI.CATEGORIES_CONTROLLER.concat(ControllerAPI.GENERAL_REQUEST);
        when(categoryService.list()).thenReturn(null);
        mockMvc.perform(get(request))
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(JsonUtils.toJson(new NoContentException().getErrorMessage())));
        verify(categoryService, times(1)).list();
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void addCategoryTest() throws Exception {
        String request = ControllerAPI.CATEGORIES_CONTROLLER.concat(ControllerAPI.GENERAL_REQUEST);
        Category category = createCategory();
        Category savedCategory = new Category();
        BeanUtils.copyProperties(category, savedCategory);
        savedCategory.setId(random.nextLong());
        when(categoryService.add(eq(category))).thenReturn(savedCategory);
        String inputJson = JsonUtils.toJson(category);
        String outputJson = JsonUtils.toJson(savedCategory);
        mockMvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(inputJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(outputJson));
        verify(categoryService, times(1)).add(eq(category));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void editCategoryWithBadIdTest() throws Exception {
        String request = ControllerAPI.CATEGORIES_CONTROLLER.concat(ControllerAPI.ID_REQUEST);
        Category category = createCategory();
        Long id = random.nextLong();
        category.setId(id);
        when(categoryService.getById(eq(id))).thenReturn(null);
        String json = JsonUtils.toJson(category);
        mockMvc.perform(put(request, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(JsonUtils.toJson(new ErrorMessage("Bad id"))));
        verify(categoryService, times(1)).getById(eq(id));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void editCategoryTest() throws Exception {
        String request = ControllerAPI.CATEGORIES_CONTROLLER.concat(ControllerAPI.ID_REQUEST);
        Category category = createCategory();
        Long id = random.nextLong();
        category.setId(id);
        when(categoryService.getById(eq(id))).thenReturn(category);
        when(categoryService.update(eq(category))).thenReturn(category);
        String json = JsonUtils.toJson(category);
        mockMvc.perform(put(request, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(json));
        verify(categoryService, times(1)).getById(eq(id));
        verify(categoryService, times(1)).update(eq(category));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void deleteCategoryWithBadIdTest() throws Exception {
        String request = ControllerAPI.CATEGORIES_CONTROLLER.concat(ControllerAPI.ID_REQUEST);
        Long id = random.nextLong();
        when(categoryService.getById(eq(id))).thenReturn(null);
        mockMvc.perform(delete(request, id))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(JsonUtils.toJson(new ErrorMessage("Bad id"))));
        verify(categoryService, times(1)).getById(eq(id));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void deleteCategoryTest() throws Exception {
        String request = ControllerAPI.CATEGORIES_CONTROLLER.concat(ControllerAPI.ID_REQUEST);
        Category category = createCategory();
        Long id = random.nextLong();
        category.setId(id);
        when(categoryService.getById(eq(id))).thenReturn(category);
        when(categoryService.delete(eq(category))).thenReturn(category);
        String json = JsonUtils.toJson(category);
        mockMvc.perform(delete(request, id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(json));
        verify(categoryService, times(1)).getById(eq(id));
        verify(categoryService, times(1)).delete(eq(category));
        verifyNoMoreInteractions(categoryService);
    }

    private Category createCategory() {
        Category category = new Category();
        category.setType(CategoryType.values()[random.nextInt(CategoryType.values().length)]);
        category.setName(TestHelper.getRandomName());
        return category;
    }



}
