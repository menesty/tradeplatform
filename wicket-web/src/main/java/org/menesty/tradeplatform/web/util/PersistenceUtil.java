package org.menesty.tradeplatform.web.util;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class PersistenceUtil {

    private static Object getValue(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            boolean change = false;
            if (!field.isAccessible()) {
                field.setAccessible(true);
                change = true;
            }
            Object value = field.get(object);
            if (change) field.setAccessible(false);
            return value;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    public static boolean isProxy(Object object, String fieldName) {
        Object value = getValue(object, fieldName);
        return  isProxy(value);
    }

    public static boolean isProxy(Object object) {
        if (object != null)
            return Proxy.isProxyClass(object.getClass());
        return false;
    }
}
