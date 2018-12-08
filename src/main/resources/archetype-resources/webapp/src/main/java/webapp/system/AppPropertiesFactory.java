package com.github.chenjianjx.srb4jfullsample.webapp.system;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.ConfigurationConverter;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.tree.OverrideCombiner;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

import java.util.Properties;

/**
 * Load the properties using commons-configuration.  This class can be used for both in spring and outside spring.
 * Created by chenjianjx@gmail.com on 10/11/18.
 */
public class AppPropertiesFactory implements FactoryBean<Properties> {

    private static final Logger logger = LoggerFactory.getLogger(AppPropertiesFactory.class);

    private static String environment;
    private static final String ENVIRONMENT_KEY = "srb4jfullsample_environment";
    private static final String DEFAULT_ENV = "dev";

    private Properties properties;

    static {
        decideEnvironment();
    }

    public AppPropertiesFactory() {
    }


    public Properties getProperties() throws Exception {
        if (properties == null) {
            properties = buildProperties();
        }
        return this.properties;
    }

    private Properties buildProperties() throws org.apache.commons.configuration2.ex.ConfigurationException {
        String overridePropFilename = "app.override." + environment + ".properties";

        Parameters params = new Parameters();

        FileBasedConfigurationBuilder<FileBasedConfiguration> base =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties().setURL(AppPropertiesFactory.class.getResource("/config/app.properties")));

        FileBasedConfigurationBuilder<FileBasedConfiguration> override =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties().setURL(AppPropertiesFactory.class.getResource("/config/" + overridePropFilename)));

        CombinedConfiguration config = new CombinedConfiguration(new OverrideCombiner());
        //the order matters. The first ones take higher priority
        config.addConfiguration(override.getConfiguration());
        config.addConfiguration(base.getConfiguration());

        return ConfigurationConverter.getProperties(config);
    }

    private static void decideEnvironment() {
        environment = System.getenv(ENVIRONMENT_KEY);
        if (StringUtils.isBlank(environment)) {
            environment = DEFAULT_ENV;
            System.setProperty(ENVIRONMENT_KEY, environment); //Note: spring context will read this system property
        }
        logger.warn("{} = {}", ENVIRONMENT_KEY, environment);
    }

    @Override
    public Properties getObject() throws Exception {
        return getProperties();
    }

    @Override
    public Class<?> getObjectType() {
        return Properties.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
