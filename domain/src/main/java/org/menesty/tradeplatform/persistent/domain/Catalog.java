package org.menesty.tradeplatform.persistent.domain;

import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 5/11/13
 * Time: 6:24 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Catalog extends CompanyEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
