package com.podorozhnick.moneytracker.service.validator;

import com.podorozhnick.moneytracker.controller.exception.ErrorMessage;
import com.podorozhnick.moneytracker.db.dao.CategoryDao;
import com.podorozhnick.moneytracker.db.model.Category;
import com.podorozhnick.moneytracker.db.model.enums.RelationType;
import com.podorozhnick.moneytracker.service.exception.ServiceValidationException;
import com.podorozhnick.moneytracker.util.ThrowableConsumer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CategoryServiceValidator implements Validator<Category> {

    private final CategoryDao categoryDao;

    public void validateCreate(Category category) throws ServiceValidationException {
        validate(getCreateValidators(), category);
    }

    public void validateEdit(Category category) throws ServiceValidationException {
        validate(getCreateValidators(), category);
    }

    private ThrowableConsumer<Category, ServiceValidationException> getCreateValidators()  {
        List<ThrowableConsumer<Category, ServiceValidationException>> validators = new ArrayList<>();
        validators.add(this::validateIfParentNotNullInParentRelation);
        validators.add(this::validateIfParentExists);
        return ThrowableConsumer.combine(validators);
    }

    private void validateIfParentNotNullInParentRelation(Category category) throws ServiceValidationException {
        if (RelationType.PARENT.equals(category.getRelation()) && Objects.isNull(category.getParent())) {
            throw new ServiceValidationException(new ErrorMessage("Parent is null in PARENT relation type"));
        }
    }

    private void validateIfParentExists(Category category) throws ServiceValidationException {
        if (Objects.nonNull(category.getParent()) && !categoryDao.isExistsById(category.getParent().getId())) {
            throw new ServiceValidationException(new ErrorMessage("No Such parent category"));
        }
    }

}
