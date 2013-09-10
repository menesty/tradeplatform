package org.menesty.tradeplatform.persistent.domain;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * User: Menesty
 * Date: 4/30/13
 * Time: 7:09 AM
 */
@MappedSuperclass
public class CompanyEntity extends Trackable {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
