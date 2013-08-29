package org.menesty.tradeplatform.web.pages.catalog;

import org.apache.wicket.model.CompoundPropertyModel;
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
    public enum ActionType {
        LIST, VIEW, ADD, EDIT;

        public static ActionType get(String value, ActionType defaultAction) {
            if (value == null || value.isEmpty()) return defaultAction;
            for (ActionType type : values())
                if (type.toString().equals(value.toUpperCase())) return type;
            return defaultAction;
        }
    }

    @SpringBean
    private CatalogService catalogService;


    public CatalogPage(PageParameters parameters) {
        super(parameters);
        ActionType action = ActionType.get(parameters.get("action").toString("list"), ActionType.LIST);
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
                add(new CatalogManagePanel<Catalog>("view", EntityModelUtil.get(catalog, CatalogService.class)) {
                    @Override
                    public void onSave(Catalog entity) {
                        catalogService.save(entity);
                        setResponsePage(CatalogPage.class);
                    }
                });
                break;

        }


    }

}
