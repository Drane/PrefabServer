package com.prefabsoft.jsf.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

public class MessageManager {

    Object ageCategory;

    public MessageManager() {
    }

    public String getMessage(String key) {

        // add the current context to the key and dive into the large resource bundle with all keys, simple and composed with context

        // use the JSF bundle mechanism, that requires <application><resource-bundle> elements in the faces-config.xml
        //        String msg = getMessageFromJSFBundle(key+"_"+ageCategory);
        //        if (msg==null || msg.startsWith("???"))
        //            msg = getMessageFromJSFBundle(key);

        // use the default Java ResourceBund;e mechanism
                String msg = getMessageFromResourceBundle(key+"_"+ageCategory,"");
                if (msg==null || "".equalsIgnoreCase(msg) ||msg.startsWith("???"))
                    msg = getMessageFromResourceBundle(key,"");


        // assume a resource bundle per specific context. the name of the context specific bundle is the base name with the context postfixed

        // for the JSF ResourceBundle mechanism, assume a second <application><resource-bundle> entry that specifies a variable called <context identifier>msgbundle
        //        String msg = getMessageFromPrefixedBundle(key, (String)ageCategory);
        //        if (msg==null || msg.startsWith("???"))
        //            msg = getMessageFromJSFBundle(key);

//        String msg = getMessageFromResourceBundle(key, (String)ageCategory);
//        if (msg == null || msg.startsWith("???"))
//            msg = getMessageFromResourceBundle(key, "");
        return msg;
    }


    private String getMessageFromResourceBundle(String key,
                                                String bundlePostfix) {
        ResourceBundle bundle = null;
        String bundleName =
            "nl.amis.appBundle" + (bundlePostfix == null ? "" : bundlePostfix);
        String message = "";
        Locale locale =
            FacesContext.getCurrentInstance().getViewRoot().getLocale();
        try {
            bundle =
                    ResourceBundle.getBundle(bundleName, locale, getCurrentLoader(bundleName));
        } catch (MissingResourceException e) {
            // bundle with this name not found;
        }
        if (bundle == null)
            return null;
        try {
            message = bundle.getString(key);
        } catch (Exception e) {
        }
        return message;

    }


    private String getMessageFromJSFBundle(String key) {
        return (String)resolveExpression("#{msgbundle['" + key + "']}");
    }

    private String getMessageFromPrefixedBundle(String key,
                                                String bundlePrefix) {
        return (String)resolveExpression("#{" + bundlePrefix + "msgbundle['" +
                                         key + "']}");
    }


    public static ClassLoader getCurrentLoader(Object fallbackClass) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null)
            loader = fallbackClass.getClass().getClassLoader();
        return loader;
    }

    public void setAgeCategory(Object ageCategory) {
        this.ageCategory = ageCategory;
    }

    public Object getAgeCategory() {
        return ageCategory;
    }

    // from JSFUtils in Oracle ADF 11g Storefront Demo

    public static Object resolveExpression(String expression) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, expression,
                                            Object.class);
        return valueExp.getValue(elContext);
    }


}
