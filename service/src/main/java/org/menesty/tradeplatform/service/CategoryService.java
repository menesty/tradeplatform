package org.menesty.tradeplatform.service;

import org.menesty.tradeplatform.persistent.domain.Catalog;
import org.menesty.tradeplatform.persistent.domain.Category;
import org.menesty.tradeplatform.persistent.domain.Company;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Menesty
 * Date: 4/30/13
 * Time: 7:01 AM
 * To change this template use Filee | Settings | File Templates.
 */
public interface CategoryService extends CompanyEntityService<Category> {

    List<Category> getChildrens(Company company, Category parent);

    List<Category> getRoot(Company company);

    List<Category> getRoot(Company company, Catalog catalog);

}
