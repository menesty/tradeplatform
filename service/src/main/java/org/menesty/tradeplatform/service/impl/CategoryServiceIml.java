package org.menesty.tradeplatform.service.impl;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import org.menesty.tradeplatform.persistent.domain.Catalog;
import org.menesty.tradeplatform.persistent.domain.Category;
import org.menesty.tradeplatform.persistent.domain.Company;
import org.menesty.tradeplatform.persistent.domain.QCategory;
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
    public List<Category> getChildren(Company company, Catalog catalog, Category parent) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(QCategory.category.deleted.isFalse());
        builder.and(QCategory.category.company.id.eq(company.getId()));

        if (parent == null)
            builder.and(QCategory.category.parent.isNull());
        else builder.and(QCategory.category.parent.id.eq(parent.getId()));
        if (catalog == null)
            builder.and(QCategory.category.catalog.isNull());
        else
            builder.and(QCategory.category.catalog.id.eq(catalog.getId()));

        return Lists.newArrayList(categoryRepository.findAll(builder));
    }

    @Override
    public List<Category> getChildren(Company company, Category parent) {
        return getChildren(company, null, parent);
    }

    @Override
    public List<Category> getChildrenByCatalog(Company company, Catalog catalog) {
        return getChildren(company, catalog, null);
    }


    @Override
    protected CompanyEntityRepository<Category> getRepository() {
        return categoryRepository;
    }

    @Override
    public Category save(Category category) {
        if (category.getId() == null || category.getId() == 0)
            if (category.getParent() != null)
                categoryRepository.updateHasChildren(category.getParent().getId(), true);
        return super.save(category);
    }


    @Override
    public void delete(Category category, boolean soft) {
        super.delete(category, soft);

        Category parent = categoryRepository.findByParent(category.getId());
        if (parent != null && categoryRepository.hasChildren(parent.getId()) == 0)
            categoryRepository.updateHasChildren(parent.getId(), false);
    }
}
