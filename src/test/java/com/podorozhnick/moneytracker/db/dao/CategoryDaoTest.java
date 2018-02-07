package com.podorozhnick.moneytracker.db.dao;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.podorozhnick.moneytracker.db.DbUnitDaoTest;
import com.podorozhnick.moneytracker.db.model.Category;
import com.podorozhnick.moneytracker.db.model.enums.CategoryType;
import com.podorozhnick.moneytracker.db.model.enums.RelationType;
import com.podorozhnick.moneytracker.pojo.search.CategorySearchFilter;
import com.podorozhnick.moneytracker.pojo.search.enums.CategorySortField;
import com.podorozhnick.moneytracker.pojo.search.enums.SortType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.podorozhnick.moneytracker.db.factory.PageFilterFactory.createPageFilter;
import static com.podorozhnick.moneytracker.db.factory.SearchFilterFactory.createCategorySearchFilter;
import static com.podorozhnick.moneytracker.db.factory.SearchParamsFactory.createCategorySearchParams;
import static com.podorozhnick.moneytracker.db.factory.SortFilterFactory.createCategorySortFilter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@DatabaseTearDown(value = "category/CategoryData.xml", type = DatabaseOperation.DELETE_ALL)
@Transactional
public class CategoryDaoTest extends DbUnitDaoTest {

    private static final String TEST_EXPENSES = "TestExpenses";

    @Autowired
    private CategoryDao categoryDao;

    @Test
    @DatabaseSetup("category/CategoryData.xml")
    public void getByNameTest() {
        Optional<Category> byName = categoryDao.getByName(TEST_EXPENSES);
        assertNotNull(byName);
        assertTrue(byName.isPresent());
        assertEquals(TEST_EXPENSES, byName.get().getName());
    }

    @Test
    @DatabaseSetup("category/CategoryFilterTestData.xml")
    public void filterIncomesTypeAllPagesNonSorted() {
        CategorySearchFilter filter = createCategorySearchFilter(
                createPageFilter(-1, null),
                null,
                createCategorySearchParams(null, CategoryType.INCOMES, null)
        );
        List<Category> categories = categoryDao.filter(filter);
        assertThat(categories).hasSize(5);
        assertThat(categories).allSatisfy(
                el -> assertThat(el.getType()).isEqualTo(CategoryType.INCOMES)
        );
    }

    @Test
    @DatabaseSetup("category/CategoryFilterTestData.xml")
    public void filterParentExpensesByUserTypeAllPagesNonSorted() {
        long userId = 1L;
        CategorySearchFilter filter = createCategorySearchFilter(
                createPageFilter(-1, null),
                null,
                createCategorySearchParams(userId, CategoryType.EXPENSES, RelationType.PARENT)
        );
        List<Category> categories = categoryDao.filter(filter);
        assertThat(categories).hasSize(1);
        assertThat(categories).allSatisfy(el -> {
                    assertThat(el.getType()).isEqualTo(CategoryType.EXPENSES);
                    assertThat(el.getRelation()).isEqualTo(RelationType.PARENT);
                    assertThat(el.getOwner().getId()).isEqualTo(userId);
                }
        );
    }

    @Test
    @DatabaseSetup("category/CategoryFilterTestData.xml")
    public void filterUnfilteredAllPagesSortedByNameAsc() {
        CategorySearchFilter filter = createCategorySearchFilter(
                createPageFilter(-1, null),
                createCategorySortFilter(SortType.ASC, CategorySortField.NAME),
                createCategorySearchParams(null, null, null)
        );
        List<Category> categories = categoryDao.filter(filter);
        assertThat(categories).hasSize(10);
        assertThat(categories).isSortedAccordingTo(Comparator.comparing(Category::getName));
    }

    @Test
    @DatabaseSetup("category/CategoryFilterTestData.xml")
    public void filterUnfilteredFirstPageByFiveUnsorted() {
        CategorySearchFilter filter = createCategorySearchFilter(
                createPageFilter(5, 1),
                null,
                null
        );
        List<Category> categories = categoryDao.filter(filter);
        assertThat(categories).hasSize(5);
    }

    @Test
    @DatabaseSetup("category/CategoryFilterTestData.xml")
    public void filterUnfilteredThirdPageByTwoSortedByNameDesc() {
        CategorySearchFilter filter = createCategorySearchFilter(
                createPageFilter(2, 3),
                createCategorySortFilter(SortType.DESC, CategorySortField.NAME),
                null
        );
        List<Category> categories = categoryDao.filter(filter);
        assertThat(categories).hasSize(2);
        assertThat(categories).allSatisfy(el -> assertThat(el.getId()).isBetween(4L, 5L));
        assertThat(categories).isSortedAccordingTo(Comparator.comparing(Category::getName).reversed());
    }

}
