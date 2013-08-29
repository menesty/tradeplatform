package org.menesty.tradeplatform.web.data.provider;

import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.model.IModel;
import org.menesty.tradeplatform.persistent.domain.Catalog;
import org.menesty.tradeplatform.persistent.domain.Category;
import org.menesty.tradeplatform.service.CatalogService;
import org.menesty.tradeplatform.service.CategoryService;
import org.menesty.tradeplatform.web.PlatformApplication;
import org.menesty.tradeplatform.web.security.SecureAuthenticatedSession;
import org.menesty.tradeplatform.web.util.EntityModelUtil;

import java.util.Iterator;

/**
 * User: Menesty
 * Date: 8/27/13
 * Time: 10:30 AM
 */
public class CategoryTreeProvider implements ITreeProvider<Category> {

    private final IModel<Catalog> catalogModel;


    public CategoryTreeProvider(Catalog catalog) {
        catalogModel = EntityModelUtil.get(catalog, CatalogService.class);
    }


    @Override
    public Iterator<Category> getRoots() {
        return getService().getRoot(SecureAuthenticatedSession.get().getCompany(), catalogModel == null ? null : catalogModel.getObject()).iterator();
    }

    @Override
    public boolean hasChildren(Category node) {
        return node.hasChildren();
    }

    @Override
    public Iterator<Category> getChildren(Category node) {
        return getService().getChildrens(SecureAuthenticatedSession.get().getCompany(), node).iterator();
    }

    @Override
    public IModel<Category> model(Category object) {
        return EntityModelUtil.get(object, CategoryService.class);
    }

    protected CategoryService getService() {
        return PlatformApplication.get().getService(CategoryService.class);
    }

    @Override
    public void detach() {
    }
}
