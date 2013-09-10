package org.menesty.tradeplatform.web.pages.layout;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.menesty.tradeplatform.web.bootstrap.Bootstrap;

import java.util.List;

/**
 * User: Menesty
 * Date: 8/4/13
 * Time: 10:06 PM
 */
public class BaseLayout extends WebPage {
    public enum ActionType {
        LIST, VIEW, ADD, EDIT;

        public static ActionType get(String value, ActionType defaultAction) {
            if (value == null || value.isEmpty()) return defaultAction;
            for (ActionType type : values())
                if (type.toString().equals(value.toUpperCase())) return type;
            return defaultAction;
        }
    }

    public BaseLayout() {
    }

    public BaseLayout(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        System.out.println(this.getClass().getName());
        List<Link> breadcrumb = getBreadcrumb();
        List<WebMarkupContainer> buttons = getButtons();
        WebMarkupContainer breadcrumbContainer = new WebMarkupContainer("breadcrumbContainer");
        if ((breadcrumb == null || breadcrumb.isEmpty()) && (buttons == null || buttons.isEmpty()))
            breadcrumbContainer.setVisible(false);

        add(breadcrumbContainer);
    }

    public void renderHead(IHeaderResponse response) {
        Bootstrap.renderHeadPlain(response);
        // Bootstrap.renderHeadResponsive(response);
        response.render(CssHeaderItem.forUrl("styles/admin.css"));
    }


    protected List<Link> getBreadcrumb() {
        return null;
    }

    protected List<WebMarkupContainer> getButtons() {
        return null;
    }
}
