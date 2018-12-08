package com.github.chenjianjx.srb4jfullsample.impl.support.template;

import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class FreemarkerTemplateFactory {

	private static FreemarkerTemplateFactory instance;

	public static FreemarkerTemplateFactory getInstance() {
		if (null == instance) {
			instance = new FreemarkerTemplateFactory();
		}
		return instance;
	}

	private FreemarkerTemplateFactory() {

	}

	public Template getTemplate(String ftlClasspath) {
		Configuration configuration = new Configuration();
		configuration.setLocalizedLookup(false);
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		configuration.setClassForTemplateLoading(
				FreemarkerTemplateFactory.class, "/");
		try {
			return configuration.getTemplate(ftlClasspath);
		} catch (IOException e) {
			throw new IllegalStateException(ftlClasspath, e);
		}
	}

}
