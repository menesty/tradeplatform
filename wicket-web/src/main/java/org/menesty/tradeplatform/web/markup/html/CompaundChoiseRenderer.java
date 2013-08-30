package org.menesty.tradeplatform.web.markup.html;

import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class CompaundChoiseRenderer<Entity> implements IChoiceRenderer<IModel<Entity>> {

    /**
     * expression for getting the display value.
     */
    private final String displayExpression;

    /**
     * expression for getting the id.
     */
    private final String idExpression;

    public CompaundChoiseRenderer() {
        super();
        this.displayExpression = null;
        this.idExpression = null;
    }

    public CompaundChoiseRenderer(String displayExpression) {
        super();
        this.displayExpression = displayExpression;
        this.idExpression = null;
    }

    public CompaundChoiseRenderer(String displayExpression, String idExpression) {
        super();
        this.displayExpression = displayExpression;
        this.idExpression = idExpression;
    }

    @Override
    public Object getDisplayValue(IModel<Entity> object) {
        Object returnValue = object;
        if ((displayExpression != null) && (object != null) && object.getObject() != null) {
            returnValue = PropertyResolver.getValue(displayExpression, object.getObject());
        }

        if (returnValue == null) {
            return "";
        }

        return returnValue;
    }

    @Override
    public String getIdValue(IModel<Entity> object, int index) {
        if (idExpression == null) {
            return Integer.toString(index);
        }

        if (object == null || object.getObject() == null) {
            return "";
        }

        Object returnValue = PropertyResolver.getValue(idExpression, object.getObject());
        if (returnValue == null) {
            return "";
        }

        return returnValue.toString();
    }
}
