package org.menesty.tradeplatform.web.pages.user;

import org.menesty.tradeplatform.persistent.domain.security.Authority;
import org.menesty.tradeplatform.web.MountPath;
import org.menesty.tradeplatform.web.pages.layout.BaseLayout;
import org.menesty.tradeplatform.web.security.PlatformAuthorizeInstantiation;

/**
 * User: Menesty
 * Date: 8/10/13
 * Time: 12:15 AM
 */
@MountPath(path = "/company/users")
@PlatformAuthorizeInstantiation({Authority.ROLE_COMPANY_ADMIN, Authority.ROLE_COMPANY_STAFF})
public class CompanyUserPage extends BaseLayout {
}
