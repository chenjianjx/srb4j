package com.github.chenjianjx.srb4jfullsample.datamigration;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Run the migration using Java invocation
 * Created by chenjianjx@gmail.com on 10/11/18.
 */
public class MigrationRunner {

    private static final Logger logger = LoggerFactory.getLogger(MigrationRunner.class);
    private static final String SQL_PATH = "classpath:data-migration-flyway-sql";

    public void run(String jdbcUrl, String dbUsername, String dbPassword) {
        Flyway flyway = Flyway.configure()
                .dataSource(jdbcUrl, dbUsername, dbPassword)
                .locations(SQL_PATH)
                .load();
        logger.warn("Ready to do migration using sql files from {} ", SQL_PATH);
        // Start the migration
        int numOfMigrations = flyway.migrate();
        logger.warn("Successfully finished {} migrations. ", numOfMigrations);
    }
}
