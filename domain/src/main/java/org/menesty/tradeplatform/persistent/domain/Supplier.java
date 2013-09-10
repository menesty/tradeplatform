package org.menesty.tradeplatform.persistent.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Supplier extends CompanyEntity {
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
