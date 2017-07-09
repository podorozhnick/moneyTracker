package com.podorozhnick.moneytracker.service;

import com.podorozhnick.moneytracker.dao.CategoryRelationsDao;
import com.podorozhnick.moneytracker.model.CategoryRelations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryRelationsService {

    @Autowired
    private CategoryRelationsDao categoryRelationsDao;

    public CategoryRelations getByCategoryId(Long categoryId) {
        return categoryRelationsDao.getByCategoryId(categoryId);
    }

}
