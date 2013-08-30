package org.menesty.tradeplatform.web.pages.category;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.menesty.tradeplatform.persistent.domain.Category;
import org.menesty.tradeplatform.web.data.provider.CategoryListProvider;
import org.menesty.tradeplatform.web.markup.html.VisibleLabel;

public class CategoryListPanel extends Panel {


    public CategoryListPanel(String id, CategoryListProvider categoryListProvider) {
        super(id);


        DataView<Category> list = new DataView<Category>("list", categoryListProvider) {
            @Override
            protected void populateItem(Item<Category> item) {
                item.add(new Label("name"));
                item.add(new VisibleLabel("visible"));
                item.add(new Label("createdDate"));
                item.add(new Label("updatedDate"));
            }
        };
        add(list);
    }


}
