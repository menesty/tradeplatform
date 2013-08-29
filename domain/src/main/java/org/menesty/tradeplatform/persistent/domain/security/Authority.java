package org.menesty.tradeplatform.persistent.domain.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 4/27/13
 * Time: 9:01 AM
 * To change this template use File | Settings | File Templates.
 */
public enum Authority implements GrantedAuthority {
    ROLE_ADMIN(""), ROLE_COMPANY_ADMIN(""), ROLE_COMPANY_STAFF(""), ROLE_COMPANY_CLIENT("");

    private final String name;

    private Authority(String name) {
        this.name = name;
    }

    public static Authority[] getCompanyRoles() {
        return new Authority[]{ROLE_COMPANY_ADMIN, ROLE_COMPANY_STAFF, ROLE_COMPANY_CLIENT};
    }
    public String getName(){
        return name;
    }

    @Override
    public String getAuthority() {
        return toString();
    }
}
