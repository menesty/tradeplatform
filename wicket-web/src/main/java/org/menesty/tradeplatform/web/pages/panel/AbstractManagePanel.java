package org.menesty.tradeplatform.web.pages.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.menesty.tradeplatform.web.pages.panel.signup.PlatformFeedbackPanel;

public abstract class AbstractManagePanel<T> extends Panel {

    public AbstractManagePanel(String id, IModel<T> model) {
        super(id, model);
    }


    protected FeedbackPanel createFeedbackPanel() {
        return createFeedbackPanel("feedback");
    }

    protected Form<T> createForm(String id){
        return  new StatelessForm<>("form", (IModel<T>) getDefaultModel());
    }

    protected FeedbackPanel createFeedbackPanel(String feedbackId) {
        FeedbackPanel feedback = new PlatformFeedbackPanel(feedbackId);
        feedback.setOutputMarkupId(true);
        return feedback;
    }

    protected AjaxButton getSubmitButton(final Form<T> editForm, final FeedbackPanel feedbackPanel){
        return new AjaxButton("submit-button", editForm) {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, Form<?> form) {
                onSave(target, editForm.getModel().getObject());
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedbackPanel);
            }
        };
    }

    public abstract void onSave(AjaxRequestTarget target, T entity);
}
