package org.menesty.tradeplatform.web.pages.site;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.menesty.tradeplatform.persistent.domain.security.Authority;
import org.menesty.tradeplatform.web.MountPath;
import org.menesty.tradeplatform.web.pages.layout.BaseLayout;
import org.menesty.tradeplatform.web.security.PlatformAuthorizeInstantiation;

/**
 * User: Menesty
 * Date: 8/26/13
 * Time: 11:20 PM
 */
@MountPath(path = "/sites/#{action}/#{id}")
@PlatformAuthorizeInstantiation({Authority.ROLE_COMPANY_ADMIN, Authority.ROLE_COMPANY_STAFF})
public class SitePage extends BaseLayout {
    public SitePage(PageParameters params) {
        super(params);
    }
}
