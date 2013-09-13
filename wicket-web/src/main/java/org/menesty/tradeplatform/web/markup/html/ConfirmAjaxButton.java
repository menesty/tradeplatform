package org.menesty.tradeplatform.web.markup.html;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public abstract class ConfirmAjaxButton<T> extends AjaxLink<T> {
    private IModel<String> messageModel;

    public ConfirmAjaxButton(String id) {
        super(id);
    }

    public ConfirmAjaxButton(String id, String message, IModel<T> model) {
        this(id, Model.of(message), model);

    }

    public ConfirmAjaxButton(String id, IModel<String> message, IModel<T> model) {
        super(id, model);
        messageModel = message;

    }


    @Override
    protected AjaxEventBehavior newAjaxEventBehavior(String event) {
        AjaxEventBehavior ajaxEventBehavior = new AjaxEventBehavior(event) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onEvent(AjaxRequestTarget target) {
                onClick(target);
            }

            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                super.updateAjaxAttributes(attributes);
                AjaxCallListener ajaxCallListener = new AjaxCallListener();
                ajaxCallListener.onPrecondition(" var component = $('#' + attrs['c']);\n" +
                        "  if(!component.attr('confirm')){\n" +
                        "    bootbox.confirm('" + getMessage() + "', function() {\n" +
                        "        component.attr('confirm', true);\n" +
                        "        component.trigger('click');\n" +
                        "    });\n" +
                        "    return false;\n" +
                        "  }\n" +
                        "  return true;");
                attributes.getAjaxCallListeners().add(ajaxCallListener);
            }
        };

        return ajaxEventBehavior;
    }

    private String getMessage() {
        String message = null;
        if (messageModel != null) message = messageModel.getObject();
        if (message != null)
            message = message.replaceAll("'", "\\\\'");
        else
            message = "";
        return message;
    }
}
