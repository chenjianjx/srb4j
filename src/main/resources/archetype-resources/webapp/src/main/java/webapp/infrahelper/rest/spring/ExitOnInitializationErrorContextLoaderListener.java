package com.github.chenjianjx.srb4jfullsample.webapp.infrahelper.rest.spring;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * If
 * Created by chenjianjx@gmail.com on 11/11/18.
 */
public class ExitOnInitializationErrorContextLoaderListener extends ContextLoaderListener {


    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            super.contextInitialized(event);
        } catch (BeanCreationException e) {
            System.exit(-1);
        }
    }
}
