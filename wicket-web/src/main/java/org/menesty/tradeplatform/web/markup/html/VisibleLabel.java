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
        Object modelObject = getDefaultModelObject();
        if (modelObject != null && modelObject.getClass().isAssignableFrom(Boolean.class)) {
            replaceComponentTagBody(markupStream, openTag, ((Boolean) modelObject) ? "visible" : "hidden");
            add(new AttributeAppender("class", ((Boolean) modelObject) ? "label-success" : "label-default"));
        } else
            replaceComponentTagBody(markupStream, openTag, getDefaultModelObjectAsString());
    }
}
