package org.menesty.tradeplatform.web.data.provider;

import org.apache.wicket.model.IModel;
import org.menesty.tradeplatform.persistent.domain.Catalog;
import org.menesty.tradeplatform.persistent.domain.Category;
import org.menesty.tradeplatform.service.CategoryService;
import org.menesty.tradeplatform.web.data.model.PageableImpl;

import java.util.Iterator;

public class CategoryListProvider extends SimpleDataProvider<Category> {

    private Long companyId;

    private Long catalogId;

    private Long parentId;

    public CategoryListProvider(Long companyId, Long catalogId, Long parentId) {
        this.catalogId = catalogId;
        this.companyId = companyId;
        this.parentId = parentId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public void setCatalog(Catalog catalog) {
        setCatalogId(catalog == null ? null : catalog.getId());
    }

    public void setCatalog(IModel<Catalog> catalog) {
        setCatalog(catalog.getObject());
    }

    public void setParent(Category parent) {
        setParentId(parent != null ? parent.getId() : null);
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public Iterator<Category> iterator(long first, long count) {
        return getService(getServiceClass()).getChildren(companyId, catalogId, parentId, new PageableImpl(first, count)).iterator();
    }

    @Override
    public long size() {
        return getService(getServiceClass()).count(companyId, catalogId, parentId);
    }


    @Override
    public Class<CategoryService> getServiceClass() {
        return CategoryService.class;
    }


}
