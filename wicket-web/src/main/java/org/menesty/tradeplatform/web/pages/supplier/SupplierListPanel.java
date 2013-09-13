package org.menesty.tradeplatform.web.pages.supplier;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.menesty.tradeplatform.persistent.domain.Supplier;
import org.menesty.tradeplatform.service.SupplierService;
import org.menesty.tradeplatform.web.data.provider.SimpleCompanyEntityDataProvider;
import org.menesty.tradeplatform.web.markup.html.ConfirmAjaxButton;
import org.menesty.tradeplatform.web.pages.supplier.product.SupplierProductPage;
import org.menesty.tradeplatform.web.security.SecureAuthenticatedSession;

/**
 * User: Menesty
 * Date: 8/22/13
 * Time: 8:55 AM
 */
public abstract class SupplierListPanel extends Panel {
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
                PageParameters pageParameters = new PageParameters();
                pageParameters.add("supplierId", item.getModelObject().getId());
                BookmarkablePageLink link = new BookmarkablePageLink<>("listLink", SupplierProductPage.class, pageParameters);

                link.add(new Label("name"));
                item.add(link);
                item.add(new Label("createdDate"));
                item.add(new Label("updatedDate"));

                PageParameters params = new PageParameters();
                params.add("action", "edit");
                params.add("id", item.getModelObject().getId());

                item.add(new BookmarkablePageLink<Void>("editLink", SupplierPage.class, params));

                item.add(new ConfirmAjaxButton<Supplier>("deleteLink", "Are you sure delete this item ?", item.getModel()) {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        onDelete(target, getModelObject());
                    }
                });
            }
        };
        add(list);
    }

    public abstract void onDelete(AjaxRequestTarget target, Supplier supplier);
}