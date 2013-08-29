package org.menesty.tradeplatform.web.pages.signup;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.menesty.tradeplatform.web.MountPath;
import org.menesty.tradeplatform.web.pages.layout.BaseLayout;
import org.menesty.tradeplatform.web.pages.panel.signup.CompanySignUpPanel;

/**
 * User: Menesty
 * Date: 8/7/13
 * Time: 8:38 PM
 */
@MountPath(path = "/signup/#{type}")
public class SignUpPage extends BaseLayout {
    private enum SignUpType {
        COMPANY, COMPANY_USER;

        public static SignUpType get(String value){
            if(value == null || value.isEmpty()) return null;
            for(SignUpType type: values())
                if(type.toString().toLowerCase().replaceAll("_","").equals(value)) return type;
            return null;
        }
    }
    public SignUpPage(PageParameters parameters) {
        super(parameters);
        SignUpType type;
        if((type = SignUpType.get(parameters.get("type").toString(null))) == null)
        throw new RestartResponseException(SignUpPage.class, new PageParameters().set("type", SignUpType.COMPANY.toString().toLowerCase()));
        if(SignUpType.COMPANY == type){
             add(new CompanySignUpPanel("signup-form"));
        }
    }

}
