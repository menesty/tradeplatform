package org.menesty.tradeplatform.web.pages.catalog;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.menesty.tradeplatform.web.pages.panel.signup.PlatformFeedbackPanel;

/**
 * User: Menesty
 * Date: 8/22/13
 * Time: 8:20 AM
 */
public class CatalogManagePanel<T> extends Panel {
    public CatalogManagePanel(String id) {
        super(id);
    }

    public CatalogManagePanel(String id, IModel<T> model) {
        super(id);
        final FeedbackPanel feedback = new PlatformFeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        add(feedback);

        final Form<T> editForm = new StatelessForm<T>("form", model);
        add(editForm);
        editForm.add(new RequiredTextField<String>("name"));


        editForm.add(new AjaxButton("submit-button", editForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onSave(editForm.getModel().getObject());
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedback);
            }
        });
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();


    }

    public void onSave(T entity){

    }
}
