package org.menesty.tradeplatform.web.pages.supplier;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.menesty.tradeplatform.persistent.domain.Catalog;
import org.menesty.tradeplatform.persistent.domain.Supplier;
import org.menesty.tradeplatform.web.pages.panel.AbstractManagePanel;

/**
 * User: Menesty
 * Date: 8/22/13
 * Time: 8:20 AM
 */
public abstract class SupplierManagePanel extends AbstractManagePanel<Supplier> {

    public SupplierManagePanel(String id, IModel<Supplier> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final FeedbackPanel feedback = createFeedbackPanel();
        add(feedback);

        final Form<Supplier> editForm = createForm("form");
        add(editForm);

        editForm.add(new RequiredTextField<String>("name"));
        editForm.add(getSubmitButton(editForm, feedback));

    }

}
