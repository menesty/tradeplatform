package org.menesty.tradeplatform.catalog;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="catalog")
public class Catalog {
    private Long id;

    private String name;

    private String description;

    private List<Category> categories;

    private String imageBase;

    private List<Product> products;
}
