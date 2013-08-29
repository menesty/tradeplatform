package org.menesty.tradeplatform.persistent.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 4/30/13
 * Time: 6:23 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Category extends CompanyEntity {
    @NotNull
    @Size(max = 255)
    private String name;
    @Lob
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Catalog catalog;

    public boolean hasChildren() {
        return children;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }

    private boolean children;

    @OneToMany
    private Set<Product> products = new HashSet<Product>();

    private boolean visible;

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
