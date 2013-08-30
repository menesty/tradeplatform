package org.menesty.tradeplatform.service.impl;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import org.menesty.tradeplatform.persistent.domain.*;
import org.menesty.tradeplatform.persistent.repository.CategoryRepository;
import org.menesty.tradeplatform.persistent.repository.CompanyEntityRepository;
import org.menesty.tradeplatform.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    public List<Category> getChildren(Long companyId, Long catalogId, Long parentId, Pageable pageable) {
        return categoryRepository.find(createPredicate(companyId, catalogId, parentId), pageable);
    }

    @Override
    public List<Category> getChildren(Company company, Catalog catalog, Category parent, Pageable pageable) {
        return getChildren(getId(company), getId(catalog), getId(parent), pageable);
    }

    @Override
    public long count(Long companyId, Long catalogId, Long parentId){
        return categoryRepository.count(createPredicate(companyId, catalogId, parentId));
    }

    @Override
    public long count(Company company, Catalog catalog, Category parent){
        return count(getId(company), getId(catalog), getId(parent));
    }

    @Override
    public List<Category> getChildren(Company company, Catalog catalog, Category parent) {
        return Lists.newArrayList(categoryRepository.findAll(createPredicate(getId(company), getId(catalog), getId(parent))));
    }

    private Long getId(Identifiable entity){
        if(entity != null) return entity.getId();
        return null;
    }

    private Predicate createPredicate(Long companyId, Long catalogId, Long parentId){
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(QCategory.category.deleted.isFalse());
        builder.and(QCategory.category.company.id.eq(companyId));

        if (parentId == null)
            builder.and(QCategory.category.parent.isNull());
        else builder.and(QCategory.category.parent.id.eq(parentId));
        if (catalogId == null)
            builder.and(QCategory.category.catalog.isNull());
        else
            builder.and(QCategory.category.catalog.id.eq(catalogId));
        return builder;
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
