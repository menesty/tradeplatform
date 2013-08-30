package org.menesty.tradeplatform.web.pages.catalog;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.menesty.tradeplatform.persistent.domain.Catalog;
import org.menesty.tradeplatform.service.CatalogService;
import org.menesty.tradeplatform.service.CompanyEntityService;
import org.menesty.tradeplatform.web.PlatformApplication;
import org.menesty.tradeplatform.web.data.provider.SimpleCompanyEntityDataProvider;
import org.menesty.tradeplatform.web.security.SecureAuthenticatedSession;

/**
 * User: Menesty
 * Date: 8/22/13
 * Time: 8:55 AM
 */
public class CatalogListPanel extends Panel {
    public CatalogListPanel(String id) {
        super(id);


        DataView<Catalog> list = new DataView<Catalog>("list", new SimpleCompanyEntityDataProvider<Catalog>(SecureAuthenticatedSession.get().getCompanyId()) {

            @Override
            public Class<CatalogService> getServiceClass() {
                return CatalogService.class;
            }
        }) {
            @Override
            protected void populateItem(Item<Catalog> item) {
                item.add(new Label("name"));
                item.add(new Label("createdDate"));
                item.add(new Label("updatedDate"));

                PageParameters params = new PageParameters();
                params.add("action", "edit");
                params.add("id", item.getModelObject().getId());

                item.add(new BookmarkablePageLink<Void>("editLink", CatalogPage.class, params));
            }
        };
        add(list);
    }
}
