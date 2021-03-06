package org.menesty.tradeplatform.web.pages.catalog;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.menesty.tradeplatform.persistent.domain.Catalog;
import org.menesty.tradeplatform.persistent.domain.security.Authority;
import org.menesty.tradeplatform.service.CatalogService;
import org.menesty.tradeplatform.web.MountPath;
import org.menesty.tradeplatform.web.pages.layout.BaseLayout;
import org.menesty.tradeplatform.web.security.PlatformAuthorizeInstantiation;
import org.menesty.tradeplatform.web.security.SecureAuthenticatedSession;
import org.menesty.tradeplatform.web.util.EntityModelUtil;

/**
 * User: Menesty
 * Date: 8/5/13
 * Time: 8:06 AM
 */
@MountPath(path = "/catalogs/#{action}/#{id}")
@PlatformAuthorizeInstantiation({Authority.ROLE_COMPANY_ADMIN, Authority.ROLE_COMPANY_STAFF})
public class CatalogPage extends BaseLayout {


    @SpringBean
    private CatalogService catalogService;


    public CatalogPage(PageParameters parameters) {
        super(parameters);
        ActionType action = ActionType.get(parameters.get("action").toString(), ActionType.LIST);
        switch (action) {
            case LIST:
                add(new CatalogListPanel("view"));
                break;
            case EDIT:
            case ADD:
                Catalog catalog = catalogService.loadById(SecureAuthenticatedSession.get().getCompanyId(), getPageParameters().get("id").toLong(0));
                if (catalog == null) {
                    catalog = new Catalog();
                    catalog.setCompany(SecureAuthenticatedSession.get().getCompany());
                }
                add(new CatalogManagePanel("view", EntityModelUtil.getCompoundModel(catalog, CatalogService.class)) {
                    @Override
                    public void onSave(AjaxRequestTarget target, Catalog entity) {
                        catalogService.save(entity);
                        setResponsePage(CatalogPage.class);
                    }

                    @Override
                    public void onCancel(AjaxRequestTarget target) {
                    }
                });
                break;

        }


    }

}
