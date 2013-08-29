package org.menesty.tradeplatform.persistent.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 5/11/13
 * Time: 6:37 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Site extends CompanyEntity {

    private String name;

    @OneToMany
    private Set<Catalog> catalogs = new HashSet<Catalog>();

    public Set<Catalog> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(Set<Catalog> catalogs) {
        this.catalogs = catalogs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
