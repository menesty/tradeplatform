package org.menesty.tradeplatform.service.impl;

import org.menesty.tradeplatform.persistent.domain.security.QUser;
import org.menesty.tradeplatform.persistent.domain.security.User;
import org.menesty.tradeplatform.persistent.repository.BaseRepository;
import org.menesty.tradeplatform.persistent.repository.UserRepository;
import org.menesty.tradeplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Menesty
 * Date: 8/5/13
 * Time: 9:39 PM
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends BaseServiceImpl<User, QUser> implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    protected BaseRepository<User> getRepository() {
        return repository;
    }

    @Override
    public UserDetails findByUserName(String userName) {
        return repository.findByCredentialsUserName(userName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getCompanyId(User user) {
        return repository.save(user).getCompany().getId();
    }
}
