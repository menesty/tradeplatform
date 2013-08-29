package org.menesty.tradeplatform.web.pages.category;

import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.menesty.tradeplatform.persistent.domain.Catalog;
import org.menesty.tradeplatform.persistent.domain.Category;
import org.menesty.tradeplatform.persistent.domain.security.Authority;
import org.menesty.tradeplatform.service.CatalogService;
import org.menesty.tradeplatform.web.MountPath;
import org.menesty.tradeplatform.web.data.provider.CategoryTreeProvider;
import org.menesty.tradeplatform.web.pages.layout.BaseLayout;
import org.menesty.tradeplatform.web.security.PlatformAuthorizeInstantiation;
import org.menesty.tradeplatform.web.security.SecureAuthenticatedSession;

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

    public CategoryPage(PageParameters params) {
        super(params);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        DefaultNestedTree<Category> treeComponent = new DefaultNestedTree<>("tree", new CategoryTreeProvider(null));

        DropDownChoice<Catalog> catalogChoice = new DropDownChoice<>("catalogsList", new LoadableDetachableModel<List<Catalog>>() {
            @Override
            protected List<Catalog> load() {
                return catalogService.loadByCompany(SecureAuthenticatedSession.get().getCompany());
            }
        }, new ChoiceRenderer<>("name"));
        add(catalogChoice);
        add(treeComponent);

    }
}
