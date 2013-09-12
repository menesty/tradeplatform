package org.menesty.tradeplatform.web.bootstrap;

import org.apache.wicket.ajax.WicketEventJQueryResourceReference;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import static java.util.Arrays.asList;

/**
 * User: Menesty
 * Date: 8/6/13
 * Time: 8:37 PM
 */
public class Bootstrap
{
    /**
     * Defines a resource reference for plain, non-responsive bootstrap. This resource reference
     * depends on jquery, bootstrap.css and bootstrap.js
     *
     * @return a bootstrap resource reference that includes everything necessary for bootstrap
     */
    public static JavaScriptResourceReference plain()
    {
        return bootstrapPlain;
    }


    /**
     * Convenience method for rendering a dependency on bootstrap (without responsive layout) in
     * your header.
     */
    public static void renderHeadPlain(IHeaderResponse response)
    {
        response.render(JavaScriptHeaderItem.forReference(Bootstrap.plain()));
        response.render(JavaScriptHeaderItem.forReference(bootBox));
    }


    private static final BootstrapResourceReference bootstrapPlain = new BootstrapResourceReference();

    private static final BootBoxResourceReference  bootBox = new BootBoxResourceReference();


    public static final CssResourceReference BOOTSTRAP_CSS = new CssResourceReference(
            Bootstrap.class, "css/bootstrap.css");

    private Bootstrap()
    {
    }

    private static class BootstrapResourceReference extends JavaScriptResourceReference
    {
        private static final long serialVersionUID = 1L;

        public BootstrapResourceReference()
        {
            super(Bootstrap.class, "js/bootstrap.js");
        }

        @Override
        public Iterable<? extends HeaderItem> getDependencies()
        {
            HeaderItem jquery = JavaScriptHeaderItem.forReference(WicketEventJQueryResourceReference.get());
            HeaderItem stylesheet = CssHeaderItem.forReference(BOOTSTRAP_CSS);

            return asList(jquery, stylesheet);
        }
    }
    private static class BootBoxResourceReference extends JavaScriptResourceReference
    {
        private static final long serialVersionUID = 1L;

        public BootBoxResourceReference()
        {
            super(Bootstrap.class, "js/plugin/bootbox.min.js");
        }

        @Override
        public Iterable<? extends HeaderItem> getDependencies()
        {
            HeaderItem bootstrap = JavaScriptHeaderItem.forReference(bootstrapPlain);

            return asList(bootstrap);
        }
    }


}
