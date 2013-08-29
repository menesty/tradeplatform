package org.menesty.tradeplatform.service;

import org.menesty.tradeplatform.persistent.domain.Company;
import org.menesty.tradeplatform.persistent.domain.security.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User: Menesty
 * Date: 8/5/13
 * Time: 9:38 PM
 */
public interface UserService extends BaseService<User>{
    UserDetails findByUserName(String userName);

    Long getCompanyId(User user);
}
