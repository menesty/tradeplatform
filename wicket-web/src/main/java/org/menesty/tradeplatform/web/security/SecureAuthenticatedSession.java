package org.menesty.tradeplatform.web.security;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.menesty.tradeplatform.persistent.domain.Company;
import org.menesty.tradeplatform.persistent.domain.security.User;
import org.menesty.tradeplatform.service.UserService;
import org.menesty.tradeplatform.web.PlatformApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * User: Menesty
 * Date: 8/5/13
 * Time: 9:54 PM
 */
public class SecureAuthenticatedSession extends AuthenticatedWebSession {
    private Logger logger = LoggerFactory.getLogger(SecureAuthenticatedSession.class);
    @SpringBean(name = "authenticationManager")
    private AuthenticationManager authenticationManager;

    private Long companyId;

    public SecureAuthenticatedSession(Request request) {
        super(request);
        injectDependencies();
    }

    private void injectDependencies() {
        Injector.get().inject(this);
    }

    @Override
    public boolean authenticate(String username, String password) {
        boolean authenticated = false;
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            companyId = PlatformApplication.get().getService(UserService.class).getCompanyId(getUser());
            return authentication.isAuthenticated();
        } catch (AuthenticationException e) {
            error(String.format("Problem occurred while login : %s", e.getMessage()));
            logger.warn(String.format("User '%s' failed to login. Reason: %s", username, e.getMessage()));
        }
        return authenticated;
    }

    public User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public Roles getRoles() {
        return getRolesIfSignedIn(new Roles());
    }

    private Roles getRolesIfSignedIn(Roles roles) {
        if (isSignedIn()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            for (GrantedAuthority authority : authentication.getAuthorities())
                roles.add(authority.getAuthority());
        }
        return roles;
    }

    public static SecureAuthenticatedSession get() {
        return (SecureAuthenticatedSession) AuthenticatedWebSession.get();
    }


    public Long getCompanyId() {
        return companyId;
    }


    public Company getCompany() {
        if (companyId == null) return null;
        Company company = new Company();
        company.setId(companyId);
        return company;
    }
}
