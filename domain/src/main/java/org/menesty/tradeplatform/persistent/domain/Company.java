package org.menesty.tradeplatform.persistent.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User: Menesty
 * Date: 4/30/13
 * Time: 7:08 AM
 */
@Entity
public class Company extends Identifiable {
    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    private String email;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(updatable = false)
    private String companyKey;

    public String getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(String companyKey) {
        this.companyKey = companyKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
