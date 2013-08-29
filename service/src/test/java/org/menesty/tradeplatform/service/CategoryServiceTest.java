package org.menesty.tradeplatform.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.menesty.tradeplatform.persistent.domain.Catalog;
import org.menesty.tradeplatform.persistent.domain.Category;
import org.menesty.tradeplatform.persistent.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/applicationContext.xml"})
public class CategoryServiceTest {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private CategoryService categoryService;

    private Company company;

    private Catalog catalog;

    @Before
    public void setUp() {

        company = new Company();
        company.setName("Company 1");
        company.setCompanyKey("companykey");
        company = companyService.save(company);

        catalog = new Catalog();
        catalog.setCompany(company);
        catalog.setName("Catalog 1");

        catalog = catalogService.save(catalog);
    }

    @Test
    public void testLoadByCatalog() {
        generateCategory(company, catalog, null, 4);
        List<Category> rootCategories = categoryService.getChildrenByCatalog(company, catalog);
        Assert.assertEquals(4, rootCategories.size());
        for (Category root : rootCategories) {
            generateCategory(company, catalog, root, 4);

            List<Category> childrens = categoryService.getChildren(company, catalog, root);
            assertEquals(4, childrens.size());
        }

    }

    @Test
    public void testCatalogHasChildrenProperty() {
        Category category = new Category();
        category.setCompany(company);
        category.setName("Category Root ");
        category = categoryService.save(category);

        generateCategory(company, null, category, 2);

        category = categoryService.loadById(category.getId());
        assertTrue(category.hasChildren());
    }

    @Test
    public void testCatalogHasChildrenDelete() {
        Category category = new Category();
        category.setCompany(company);
        category.setName("Category Root Child Delete");
        category = categoryService.save(category);

        generateCategory(company, null, category, 1);

        Category child = categoryService.getChildren(company, category).get(0);
        categoryService.delete(child);

        child =  categoryService.loadById(child.getId());
        assertTrue(child.isDeleted());

        category = categoryService.loadById(category.getId());
        assertFalse(category.hasChildren());

    }

    private void generateCategory(Company company, Catalog catalog, Category parent, int count) {
        for (int i = 0; i < count; i++) {
            Category category = new Category();
            category.setCompany(company);
            category.setName("Category " + i);
            category.setParent(parent);
            category.setCatalog(catalog);
            categoryService.save(category);

        }

    }
}
