package org.menesty.tradeplatform.persistent.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 4/28/13
 * Time: 7:23 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Product extends CompanyEntity {

    @OneToMany(mappedBy = "product")
    private Set<ProductItem> productItems = new HashSet<ProductItem>();

    public Set<ProductItem> getProductItems() {
        return productItems;
    }

    public void setProductItems(Set<ProductItem> productItems) {
        this.productItems = productItems;
    }
}
