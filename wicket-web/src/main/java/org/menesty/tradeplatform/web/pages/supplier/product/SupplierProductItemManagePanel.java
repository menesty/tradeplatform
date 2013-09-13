package org.menesty.tradeplatform.web.pages.supplier.product;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.menesty.tradeplatform.persistent.domain.SupplierProductItem;
import org.menesty.tradeplatform.web.pages.panel.AbstractManagePanel;

/**
 * User: Menesty
 * Date: 8/22/13
 * Time: 8:20 AM
 */
public abstract class SupplierProductItemManagePanel extends AbstractManagePanel<SupplierProductItem> {

    public SupplierProductItemManagePanel(String id, IModel<SupplierProductItem> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final FeedbackPanel feedback = createFeedbackPanel();
        add(feedback);

        final Form<SupplierProductItem> editForm = createForm("form");
        add(editForm);

        editForm.add(new RequiredTextField<String>("name"));
        editForm.add(getSubmitButton(editForm, feedback));

    }

}
