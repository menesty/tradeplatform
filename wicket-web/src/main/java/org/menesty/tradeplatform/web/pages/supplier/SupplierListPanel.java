package org.menesty.tradeplatform.web.pages.supplier;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.menesty.tradeplatform.persistent.domain.Supplier;
import org.menesty.tradeplatform.service.SupplierService;
import org.menesty.tradeplatform.web.data.provider.SimpleCompanyEntityDataProvider;
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
                item.add(new Label("name"));
                item.add(new Label("createdDate"));
                item.add(new Label("updatedDate"));

                PageParameters params = new PageParameters();
                params.add("action", "edit");
                params.add("id", item.getModelObject().getId());

                item.add(new BookmarkablePageLink<Void>("editLink", SupplierPage.class, params));

                final AjaxLink<Supplier> deleteLink =  new AjaxLink<Supplier>("deleteLink", item.getModel()) {
                    protected AjaxEventBehavior newAjaxEventBehavior(String event)
                    {
                        AjaxEventBehavior ajaxEventBehavior =  new AjaxEventBehavior(event)
                        {
                            private static final long serialVersionUID = 1L;

                            @Override
                            protected void onEvent(AjaxRequestTarget target)
                            {
                                onClick(target);
                            }

                            @Override
                            protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
                            {
                                super.updateAjaxAttributes(attributes);
                                AjaxCallListener ajaxCallListener = new AjaxCallListener();
                                ajaxCallListener.onPrecondition(" var component = $(\"#\"+attrs[\"c\"]);\n" +
                                        "  if(!component.attr(\"confirm\")){\n" +
                                        "    bootbox.confirm(\"Hello world!\", function() {\n" +
                                        "        component.attr(\"confirm\", true);\n" +
                                        "        component.trigger(\"click\");\n" +
                                        "    });\n" +
                                        "    return false;\n" +
                                        "  }\n" +
                                        "  return true;");
                                attributes.getAjaxCallListeners().add(ajaxCallListener);
                            }
                        };

                        return ajaxEventBehavior;
                    }
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        onDelete(target, getModelObject());
                    }
                };

                item.add(deleteLink);
            }
        };
        add(list);
    }

    public abstract  void onDelete(AjaxRequestTarget target, Supplier supplier);
}
/*
function(attrs){
  var component = $("#"+attrs["c"]);
  alert(component.attr("confirm"));
  if(!component.attr("confirm")){
    bootbox.confirm("Hello world!", function() {
        component.attr("confirm", true);
        component.trigger("click");
    });
    return false;
  }
  return true;
}
 */
