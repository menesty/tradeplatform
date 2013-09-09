package org.menesty.tradeplatform.web.pages.category;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.menesty.tradeplatform.persistent.domain.Catalog;
import org.menesty.tradeplatform.persistent.domain.Category;
import org.menesty.tradeplatform.persistent.domain.security.Authority;
import org.menesty.tradeplatform.service.CatalogService;
import org.menesty.tradeplatform.service.CategoryService;
import org.menesty.tradeplatform.web.MountPath;
import org.menesty.tradeplatform.web.data.provider.CategoryListProvider;
import org.menesty.tradeplatform.web.data.provider.CategoryTreeProvider;
import org.menesty.tradeplatform.web.markup.html.CompaundChoiseRenderer;
import org.menesty.tradeplatform.web.pages.layout.BaseLayout;
import org.menesty.tradeplatform.web.security.PlatformAuthorizeInstantiation;
import org.menesty.tradeplatform.web.security.SecureAuthenticatedSession;
import org.menesty.tradeplatform.web.util.EntityModelUtil;

import java.util.List;

/**
 * User: Menesty
 * Date: 8/26/13
 * Time: 11:24 PM
 */
@MountPath(path = "/categories/#{action}/#{id}")
@PlatformAuthorizeInstantiation({Authority.ROLE_COMPANY_ADMIN, Authority.ROLE_COMPANY_STAFF})
public class CategoryPage extends BaseLayout {

    @SpringBean
    private CatalogService catalogService;
    @SpringBean
    private CategoryService categoryService;


    private IModel<Catalog> selectedCatalog;

    private IModel<Category> selectedCategory;

    private SelectableTree<Category> treeComponent;

    private CategoryListPanel list;

    private WebMarkupContainer cardPanel;

    public CategoryPage(PageParameters params) {
        super(params);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        final CategoryListProvider categoryListProvider = new CategoryListProvider(SecureAuthenticatedSession.get().getCompanyId(), null, null);
        final CategoryTreeProvider categoryTreeProvider = new CategoryTreeProvider(null);
        list = new CategoryListPanel("categoryContainer", categoryListProvider) {
            @Override
            public void onEdit(AjaxRequestTarget target, IModel<Category> category) {
                cardPanel.addOrReplace(getManagePanel(category));
                target.add(cardPanel);
            }
            @Override
            public void onDelete(AjaxRequestTarget target, IModel<Category> category){
                categoryService.delete(category.getObject());
                treeComponent.updateBranch(getValue(selectedCategory), target);
                target.add(list);
            }
        };

        cardPanel = new WebMarkupContainer("cardPanel");
        cardPanel.setOutputMarkupId(true);
        list.setOutputMarkupId(true);

        cardPanel.add(list);
        add(cardPanel);


        treeComponent = new SelectableTree<Category>("tree", categoryTreeProvider) {
            public void onSelect(AjaxRequestTarget target, IModel<Category> model) {
                categoryListProvider.setParent(model);
                selectedCategory = model;
                target.add(list);

            }

            public void onSelect(AjaxRequestTarget target, Category entity) {
                categoryListProvider.setParent(entity);
                target.add(list);
            }
        };


        DropDownChoice<IModel<Catalog>> catalogChoice = new DropDownChoice<>("catalogsList", new PropertyModel<IModel<Catalog>>(this, "selectedCatalog"), new LoadableDetachableModel<List<IModel<Catalog>>>() {
            @Override
            protected List<IModel<Catalog>> load() {
                return EntityModelUtil.getCompoundModel(catalogService.loadByCompany(SecureAuthenticatedSession.get().getCompany()), CatalogService.class);
            }
        }, new CompaundChoiseRenderer<Catalog>("name"));
        catalogChoice.add(new AjaxFormComponentUpdatingBehavior("change") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                categoryListProvider.setCatalog(selectedCatalog);
                categoryListProvider.setParentId(null);
                categoryTreeProvider.setCatalog(selectedCatalog);
                target.add(list);
                target.add(treeComponent);
            }
        });

        add(catalogChoice);
        add(treeComponent);


        add(new AjaxLink<Void>("add") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                Category newCategory = new Category(SecureAuthenticatedSession.get().getCompany(), getValue(selectedCatalog), getValue(selectedCategory));
                cardPanel.addOrReplace(getManagePanel(EntityModelUtil.getCompoundModel(newCategory, null)));
                target.add(cardPanel);
            }
        }.setOutputMarkupId(true));

    }

    private <T> T getValue(IModel<T> model) {
        if (model != null) return model.getObject();
        return null;
    }


    private CategoryManagePanel getManagePanel(IModel<Category> category) {
        return new CategoryManagePanel("categoryContainer", category) {
            @Override
            public void onSave(AjaxRequestTarget target, Category entity) {
                categoryService.save(entity);
                treeComponent.updateBranch(getValue(selectedCategory), target);
                cardPanel.addOrReplace(list);
                target.add(cardPanel);
            }

            @Override
            public void onCancel(AjaxRequestTarget target) {
                cardPanel.addOrReplace(list);
                target.add(cardPanel);
            }
        };


    }
}
