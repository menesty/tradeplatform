package org.menesty.tradeplatform.web.pages.category;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

public class SelectableTree<T> extends DefaultNestedTree<T> {

    public SelectableTree(String id, ITreeProvider<T> provider) {
        super(id, provider);
        setOutputMarkupId(true);
        selectableFolderContent = new SelectableFolderContent();
    }

    private SelectableFolderContent selectableFolderContent;


    @Override
    protected Component newContentComponent(String id, IModel<T> model) {
        return selectableFolderContent.newContentComponent(id, this, model);
    }

    class SelectableFolderContent implements IDetachable {

        private static final long serialVersionUID = 1L;


        private IModel<T> selected;


        @Override
        public void detach() {
            if (selected != null) {
                selected.detach();
            }
        }

        protected boolean isSelected(T entity) {
            IModel<T> model = getProvider().model(entity);

            try {
                return selected != null && selected.equals(model);
            } finally {
                model.detach();
            }
        }

        protected void select(IModel<T> model, NestedTree<T> tree, final AjaxRequestTarget target) {
            if (selected != null) {
                tree.updateNode(selected.getObject(), target);

                selected.detach();
                selected = null;
            }

            selected = model;

            tree.updateNode(model.getObject(), target);
            onSelect(target, model);
        }

        public Component newContentComponent(String id, final NestedTree<T> tree, IModel<T> model) {
            return new Folder<T>(id, tree, model) {
                private static final long serialVersionUID = 1L;

                /**
                 * Always clickable.
                 */
                @Override
                protected boolean isClickable() {
                    return true;
                }

                @Override
                protected void onClick(AjaxRequestTarget target) {
                    selectableFolderContent.select(getModel(), tree, target);
                }

                @Override
                protected boolean isSelected() {
                    return selectableFolderContent.isSelected(getModelObject());
                }
            };
        }

    }

    public void onSelect(AjaxRequestTarget target, IModel<T> model){
        onSelect(target, model.getObject());

    }
    public void onSelect(AjaxRequestTarget target, T entity){

    }
}
