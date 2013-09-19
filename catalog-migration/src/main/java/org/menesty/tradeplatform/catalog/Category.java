package org.menesty.tradeplatform.catalog;

import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType
public class Category {
    private Long id;

    private String name;

    private List<Category> categories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
