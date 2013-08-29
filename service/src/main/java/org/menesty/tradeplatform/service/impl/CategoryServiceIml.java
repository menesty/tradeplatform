package org.menesty.tradeplatform.service.impl;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.path.BooleanPath;
import org.menesty.tradeplatform.persistent.domain.*;
import org.menesty.tradeplatform.persistent.repository.BaseRepository;
import org.menesty.tradeplatform.persistent.repository.CategoryRepository;
import org.menesty.tradeplatform.persistent.repository.CompanyEntityRepository;
import org.menesty.tradeplatform.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 4/30/13
 * Time: 7:02 AM
 * To change this template use File | Settings | File Templates.
 */
@Transactional(readOnly = true)
@Service
public class CategoryServiceIml extends CompanyEntityServiceImpl<Category, QCategory> implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getChildrens(Company company, Category parent) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(QCategory.category.deleted.isFalse());
        builder.and(QCategory.category.parent.eq(parent));
        builder.and(this.<QCompany>getFieldPath("company").id.eq(company.getId()));

        return Lists.newArrayList(categoryRepository.findAll(builder));
    }

    @Override
    public List<Category> getRoot(Company company, Catalog catalog) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(QCategory.category.deleted.isFalse());
        builder.and(QCategory.category.parent.isNull());
        builder.and(this.<QCompany>getFieldPath("company").id.eq(company.getId()));
        if (catalog == null)
            builder.and(this.<QCatalog>getFieldPath("catalog").isNull());
        else
            builder.and(this.<QCatalog>getFieldPath("catalog").eq(catalog));

        return Lists.newArrayList(categoryRepository.findAll(builder));
    }

    @Override
    public List<Category> getRoot(Company company) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(QCategory.category.deleted.isFalse());
        builder.and(QCategory.category.parent.isNull());
        builder.and(this.<QCompany>getFieldPath("company").id.eq(company.getId()));

        return Lists.newArrayList(categoryRepository.findAll(builder));
    }

    @Override
    protected CompanyEntityRepository<Category> getRepository() {
        return categoryRepository;
    }
}
