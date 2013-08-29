package org.menesty.tradeplatform.web;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.reflections.Reflections;

import java.util.Set;

/**
 * User: Menesty
 * Date: 8/5/13
 * Time: 9:35 AM
 */
class AutoMountPage {
    public static void mount(WebApplication application, String path) {
        Reflections reflections =
                true ? Reflections.collect() : new Reflections(path);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MountPath.class);
        for (Class<?> clazz : classes) {
            application.mountPage(clazz.getAnnotation(MountPath.class).path(), (Class<Page>) clazz);
        }
    }
}
