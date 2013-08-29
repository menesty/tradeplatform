package org.menesty.tradeplatform.web.pages.panel.signup;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.menesty.tradeplatform.web.data.model.CompanySignUpModel;
import org.menesty.tradeplatform.web.pages.signup.SignUpSuccessPage;
import org.menesty.tradeplatform.web.service.security.RegistrationService;

/**
 * User: Menesty
 * Date: 8/7/13
 * Time: 9:36 PM
 */
public class CompanySignUpPanel extends Panel {

    private CompanySignUpModel model;
    @SpringBean
    private RegistrationService registrationService;


    public CompanySignUpPanel(String id) {
        super(id);
        SpringComponentInjector.get().inject(this);

        final FeedbackPanel feedback = new PlatformFeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);

        add(feedback);
        final StatelessForm<CompanySignUpModel> signUpForm;
        add(signUpForm = new StatelessForm<>("form", new CompoundPropertyModel<>(model = new CompanySignUpModel())));
        signUpForm.add(new EmailTextField("email"));
        signUpForm.add(new RequiredTextField<String>("companyName").add(new StringValidator(2, 255)));
        signUpForm.add(new RequiredTextField<String>("companyKey").add(new StringValidator(2, 255)).add(new PatternValidator("[a-zA-Z]+")));
        signUpForm.add(new PasswordTextField("password").add(new StringValidator(4, 10)));
        signUpForm.add(new PasswordTextField("repeatPassword").add(new StringValidator(4, 10)));
        signUpForm.add(new EqualPasswordInputValidator((FormComponent) signUpForm.get("password"), (FormComponent) signUpForm.get("repeatPassword")));
        signUpForm.add(new AjaxButton("submit-button", signUpForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                registrationService.registerCompany(signUpForm.getModel().getObject());
                setResponsePage(SignUpSuccessPage.class);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedback);
            }
        });

    }


}
