package org.menesty.tradeplatform.persistent.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Menesty
 * Date: 4/30/13
 * Time: 6:23 AM
 */
@Entity
public class Category extends CompanyEntity {

    public Category(){

    }

    public Category(Company company, Catalog catalog, Category parent){
        setCompany(company);
        this.catalog = catalog;
        this.parent = parent;
    }

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

    private boolean children = false;

    @OneToMany
    private Set<Product> products = new HashSet<>();

    private boolean visible = true;

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
