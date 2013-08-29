package org.menesty.tradeplatform.web;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authorization.strategies.role.RoleAuthorizationStrategy;
import org.apache.wicket.core.util.resource.UrlResourceStream;
import org.apache.wicket.core.util.resource.locator.ResourceStreamLocator;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.resource.IResourceStream;
import org.menesty.tradeplatform.web.pages.index.IndexPage;
import org.menesty.tradeplatform.web.pages.index.LoginPage;
import org.menesty.tradeplatform.web.security.PlatformAnnotationRoleAuthorizationStrategy;
import org.menesty.tradeplatform.web.security.SecureAuthenticatedSession;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: Menesty
 * Date: 8/4/13
 * Time: 8:32 PM
 */
public class PlatformApplication extends AuthenticatedWebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return IndexPage.class;
    }

    @Override
    protected void init() {
        super.init();
        getResourceSettings().setResourceStreamLocator(new WebContextTemplateLoader());

        AutoMountPage.mount(this, "org.menesty.tradeplatform.web.pages");

        getComponentInstantiationListeners().add(new SpringComponentInjector(this));

        ((RoleAuthorizationStrategy) getSecuritySettings().getAuthorizationStrategy()).add(new PlatformAnnotationRoleAuthorizationStrategy(this));
        getMarkupSettings().setStripWicketTags(true);
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return SecureAuthenticatedSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return LoginPage.class;
    }

    private class WebContextTemplateLoader extends ResourceStreamLocator {

        @Override
        public IResourceStream locate(Class<?> clazz, String path) {
            URL url;
            try {
                url = getServletContext().getResource("/WEB-INF/templates/" + path);
                if (url != null) {
                    return new UrlResourceStream(url);
                }
            } catch (MalformedURLException e) {
                throw new WicketRuntimeException(e);
            }

            return super.locate(clazz, path);
        }

    }

    public <T> T getService(Class<T> service) {
        return WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean(service);
    }

    public static PlatformApplication get() {
        return (PlatformApplication) AuthenticatedWebApplication.get();
    }

}

