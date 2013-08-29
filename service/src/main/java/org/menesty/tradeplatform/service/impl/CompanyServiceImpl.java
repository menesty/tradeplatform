package org.menesty.tradeplatform.service.impl;

import org.menesty.tradeplatform.persistent.domain.Company;
import org.menesty.tradeplatform.persistent.domain.QCompany;
import org.menesty.tradeplatform.persistent.repository.BaseRepository;
import org.menesty.tradeplatform.persistent.repository.CompanyRepository;
import org.menesty.tradeplatform.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Menesty
 * Date: 7/13/13
 * Time: 10:21 AM
 */
@Service
public class CompanyServiceImpl extends BaseServiceImpl<Company, QCompany> implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;


    @Override
    protected BaseRepository<Company> getRepository() {
        return companyRepository;
    }
}
