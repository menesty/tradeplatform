package TradePlatform;

import junit.framework.Assert;
import org.menesty.tradeplatform.persistent.domain.Category;
import org.menesty.tradeplatform.persistent.domain.Company;
import org.menesty.tradeplatform.service.CategoryService;
import org.menesty.tradeplatform.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 7/13/13
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */

public class CategoryTest {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CompanyService companyService;

    public void setUp() {
        Company company = new Company();
        company = companyService.save(company);

        Category rootCategory = new Category();
        rootCategory.setName("Root");
        rootCategory.setCompany(company);
        rootCategory = categoryService.save(rootCategory);

        int childrenCount = 5;

        for (int i = 0; i < childrenCount; i++) {
            Category category = new Category();
            category.setName("category " + i);
            category.setCompany(company);
            category.setParent(rootCategory);
            categoryService.save(category);
        }
        List<Category> childrens = categoryService.getChildrens(company, rootCategory);
        Assert.assertEquals(childrenCount, childrens.size());

    }
}
