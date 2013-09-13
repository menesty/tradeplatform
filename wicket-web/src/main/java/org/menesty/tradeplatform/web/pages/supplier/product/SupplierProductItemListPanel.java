package org.menesty.tradeplatform.web.pages.supplier.product;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.menesty.tradeplatform.persistent.domain.Supplier;
import org.menesty.tradeplatform.persistent.domain.SupplierProductItem;
import org.menesty.tradeplatform.service.SupplierProductItemService;
import org.menesty.tradeplatform.web.data.provider.SimpleCompanyEntityDataProvider;
import org.menesty.tradeplatform.web.data.provider.SupplierProductListProvider;
import org.menesty.tradeplatform.web.markup.html.ConfirmAjaxButton;
import org.menesty.tradeplatform.web.pages.supplier.SupplierPage;
import org.menesty.tradeplatform.web.security.SecureAuthenticatedSession;
import org.menesty.tradeplatform.web.util.PersistenceUtil;

/**
 * User: Menesty
 * Date: 8/22/13
 * Time: 8:55 AM
 */
public abstract class SupplierProductItemListPanel extends Panel {
    public SupplierProductItemListPanel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        Long supplierId = ((Supplier) getDefaultModelObject()).getId();
        PageParameters pageParameters = new PageParameters();
        pageParameters.add("supplierId", supplierId);
        pageParameters.add("action", "add");
        add(new BookmarkablePageLink<>("addLink", SupplierProductPage.class, pageParameters));
        DataView<SupplierProductItem> list = new DataView<SupplierProductItem>("list", new SupplierProductListProvider(SecureAuthenticatedSession.get().getCompanyId(), supplierId)) {
            @Override
            protected void populateItem(Item<SupplierProductItem> item) {

                WebMarkupContainer indicator = new WebMarkupContainer("connected");

                if (item.getModelObject().getProductItem() == null)
                    indicator.setVisible(false);


                PageParameters params = new PageParameters();
                params.add("action", "edit");
                params.add("id", item.getModelObject().getId());

                item.add(new Label("name"));
                item.add(new Label("createdDate"));
                item.add(new Label("updatedDate"));
                item.add(indicator);
                item.add(new BookmarkablePageLink<Void>("editLink", SupplierPage.class, params));
                item.add(new ConfirmAjaxButton<SupplierProductItem>("deleteLink", "Are you sure delete this item ?", item.getModel()) {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        onDelete(target, getModelObject());
                    }
                });
            }
        };
        add(list);
    }

    public abstract void onDelete(AjaxRequestTarget target, SupplierProductItem supplier);
}