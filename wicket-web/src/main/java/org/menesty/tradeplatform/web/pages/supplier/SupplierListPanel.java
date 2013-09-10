package org.menesty.tradeplatform.web.pages.supplier;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.menesty.tradeplatform.persistent.domain.Catalog;
import org.menesty.tradeplatform.persistent.domain.Supplier;
import org.menesty.tradeplatform.service.CatalogService;
import org.menesty.tradeplatform.service.SupplierService;
import org.menesty.tradeplatform.web.data.provider.SimpleCompanyEntityDataProvider;
import org.menesty.tradeplatform.web.pages.catalog.CatalogPage;
import org.menesty.tradeplatform.web.security.SecureAuthenticatedSession;

/**
 * User: Menesty
 * Date: 8/22/13
 * Time: 8:55 AM
 */
public class SupplierListPanel extends Panel {
    public SupplierListPanel(String id) {
        super(id);


        DataView<Supplier> list = new DataView<Supplier>("list", new SimpleCompanyEntityDataProvider<Supplier>(SecureAuthenticatedSession.get().getCompanyId()) {

            @Override
            public Class<SupplierService> getServiceClass() {
                return SupplierService.class;
            }
        }) {
            @Override
            protected void populateItem(Item<Supplier> item) {
                item.add(new Label("name"));
                item.add(new Label("createdDate"));
                item.add(new Label("updatedDate"));

                PageParameters params = new PageParameters();
                params.add("action", "edit");
                params.add("id", item.getModelObject().getId());

                item.add(new BookmarkablePageLink<Void>("editLink", SupplierPage.class, params));
            }
        };
        add(list);
    }
}
