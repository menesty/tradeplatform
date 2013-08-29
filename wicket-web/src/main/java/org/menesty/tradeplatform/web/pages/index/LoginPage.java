package org.menesty.tradeplatform.web.pages.index;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.model.CompoundPropertyModel;
import org.menesty.tradeplatform.web.MountPath;
import org.menesty.tradeplatform.web.pages.layout.BaseLayout;
import org.menesty.tradeplatform.web.pages.panel.signup.PlatformFeedbackPanel;

/**
 * User: Menesty
 * Date: 8/5/13
 * Time: 10:05 PM
 */
@MountPath(path = "/login")
public class LoginPage extends BaseLayout {

    public LoginPage() {
        add(new LoginForm("loginForm"));
        add(new PlatformFeedbackPanel("feedback"));

    }

    private static class LoginForm extends StatelessForm {
        private String username;
        private String password;

        public LoginForm(String id) {
            super(id);
            setModel(new CompoundPropertyModel(this));
            add(new EmailTextField("username").setRequired(true));
            add(new PasswordTextField("password"));
        }

        @Override
        protected void onSubmit() {
            AuthenticatedWebSession session = AuthenticatedWebSession.get();
            if (session.signIn(username, password)) {
                setDefaultResponsePageIfNecessary();
            } else {
                //error(getString("login.failed.badcredentials"));
            }
        }

        private void setDefaultResponsePageIfNecessary() {
            continueToOriginalDestination();
            setResponsePage(getApplication().getHomePage());
        }
    }
}
