package org.menesty.tradeplatform.web.pages.category;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
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
import org.menesty.tradeplatform.web.markup.html.VisibleLabel;
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

    public CategoryPage(PageParameters params) {
        super(params);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        final CategoryListProvider categoryListProvider = new CategoryListProvider(SecureAuthenticatedSession.get().getCompanyId(), null, null);
        final CategoryTreeProvider categoryTreeProvider = new CategoryTreeProvider(null);
        final CategoryListPanel list = new CategoryListPanel("categoryContainer", categoryListProvider);
        list.setOutputMarkupId(true);

        add(list);


        final SelectableTree<Category> treeComponent = new SelectableTree<Category>("tree", categoryTreeProvider) {
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
                Category newCategory = new Category(SecureAuthenticatedSession.get().getCompany(), selectedCatalog.getObject(), selectedCategory.getObject());
                addOrReplace(new CategoryManagePanel("categoryContainer", EntityModelUtil.getCompoundModel(newCategory, null)) {
                    @Override
                    public void onSave(AjaxRequestTarget target, Category entity) {
                        categoryService.save(entity);
                        treeComponent.expand(entity.getParent());
                        target.add(list);
                    }
                });
            }
        });

    }

    class SelectableFolderContent implements IDetachable {

        private static final long serialVersionUID = 1L;

        private ITreeProvider<Category> provider;

        private IModel<Category> selected;

        public SelectableFolderContent(ITreeProvider<Category> provider) {
            this.provider = provider;
        }

        @Override
        public void detach() {
            if (selected != null) {
                selected.detach();
            }
        }

        protected boolean isSelected(Category category) {
            IModel<Category> model = provider.model(category);

            try {
                return selected != null && selected.equals(model);
            } finally {
                model.detach();
            }
        }

        protected void select(Category foo, NestedTree<Category> tree, final AjaxRequestTarget target) {
            if (selected != null) {
                tree.updateNode(selected.getObject(), target);

                selected.detach();
                selected = null;
            }

            selected = provider.model(foo);

            tree.updateNode(foo, target);
        }

        public Component newContentComponent(String id, final NestedTree<Category> tree, IModel<Category> model) {
            return new Folder<Category>(id, tree, model) {
                private static final long serialVersionUID = 1L;

                /**
                 * Always clickable.
                 */
                @Override
                protected boolean isClickable() {
                    return true;
                }

                @Override
                protected void onClick(AjaxRequestTarget target) {
                    SelectableFolderContent.this.select(getModelObject(), tree, target);
                }

                @Override
                protected boolean isSelected() {
                    return SelectableFolderContent.this.isSelected(getModelObject());
                }
            };
        }


    }
}
