package org.menesty.tradeplatform.web.pages.category;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.menesty.tradeplatform.persistent.domain.Category;
import org.menesty.tradeplatform.web.pages.panel.AbstractManagePanel;

public abstract class CategoryManagePanel extends AbstractManagePanel<Category> {

    public CategoryManagePanel(String id, IModel<Category> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        final FeedbackPanel feedback = createFeedbackPanel();
        add(feedback);

        final Form<Category> editForm = createForm("form");
        add(editForm);

        editForm.add(new RequiredTextField<String>("name"));
        editForm.add(new CheckBox("visible"));

        editForm.add(getSubmitButton(editForm, feedback));
    }
}
