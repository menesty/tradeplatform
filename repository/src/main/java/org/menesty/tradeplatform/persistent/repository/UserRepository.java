package org.menesty.tradeplatform.persistent.repository;


import org.menesty.tradeplatform.persistent.domain.security.User;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 4/27/13
 * Time: 9:53 AM
 * To change this template use File | Settings | File Templates.
 */
public interface UserRepository extends BaseRepository<User> {
    User findByCredentialsUserName(String userName);
}
