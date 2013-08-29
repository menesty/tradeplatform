package org.menesty.tradeplatform.web.pages.panel.signup;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * User: Menesty
 * Date: 8/9/13
 * Time: 12:07 AM
 */
public class PlatformFeedbackPanel extends FeedbackPanel{
    public PlatformFeedbackPanel(String id) {
        super(id);
    }

    public PlatformFeedbackPanel(String id, IFeedbackMessageFilter filter) {
        super(id, filter);
    }

    @Override
    protected void onBeforeRender() {
        this.add(AttributeModifier.replace("style", "display:" + (this.anyMessage() ? "block" : "none")));
        super.onBeforeRender();
    }
}
