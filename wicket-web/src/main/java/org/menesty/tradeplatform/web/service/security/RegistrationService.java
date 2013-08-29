package org.menesty.tradeplatform.web.service.security;

import org.menesty.tradeplatform.persistent.domain.Company;
import org.menesty.tradeplatform.persistent.domain.security.Authority;
import org.menesty.tradeplatform.persistent.domain.security.User;
import org.menesty.tradeplatform.service.CompanyService;
import org.menesty.tradeplatform.service.UserService;
import org.menesty.tradeplatform.web.data.model.CompanySignUpModel;
import org.menesty.tradeplatform.web.service.security.PlatformMessageDigestPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * User: Menesty
 * Date: 8/9/13
 * Time: 11:34 PM
 */
@Service
public class RegistrationService {

    @Autowired
    private PlatformMessageDigestPasswordEncoder passwordEncoder;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;


    public void registerCompany(CompanySignUpModel model){
        Company company = new Company();
        company.setName(model.getCompanyName());
        company.setCompanyKey(model.getCompanyKey());
        company.setEmail(model.getEmail());
        company = companyService.save(company);
        User user = new User();
        user.getCredentials().setUserName(model.getEmail());
        user.getCredentials().setPassword(passwordEncoder.encodePassword(model.getPassword(), null));
        user.setCompany(company);
        user.setRoles(Collections.singleton(Authority.ROLE_COMPANY_ADMIN));
        userService.save(user);

        //TODO P3 send mail with activation link
    }
}
