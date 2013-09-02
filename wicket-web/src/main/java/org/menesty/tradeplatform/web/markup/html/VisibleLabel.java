package org.menesty.tradeplatform.web.markup.html;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;

public class VisibleLabel extends Label {

    public VisibleLabel(String id) {
        super(id);
    }

    @Override
    public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
        Boolean value = getBooleanValue();
        if (value != null)
            replaceComponentTagBody(markupStream, openTag, value ? "visible" : "hidden");
        else
            super.onComponentTagBody(markupStream, openTag);
    }

    @Override
    protected void onBeforeRender() {
        Boolean value = getBooleanValue();
        if (value != null) {
            add(new AttributeAppender("class", value ? " label-success" : " label-default"));
        }
        super.onBeforeRender();
    }

    private Boolean getBooleanValue() {
        Object modelObject = getDefaultModelObject();
        if (modelObject != null && modelObject.getClass().isAssignableFrom(Boolean.class)) {
            return ((Boolean) modelObject);
        }
        return null;
    }
}
