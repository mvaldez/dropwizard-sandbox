package com.sandbox.dropwizard;

import com.sandbox.resources.ContactResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyApplication extends Application<PhonebookConfiguration> {
    public static final Logger LOGGER = LoggerFactory.getLogger(MyApplication.class);

    public static void main(final String[] args) throws Exception {
        new MyApplication().run(args);
    }

    @Override
    public void run(PhonebookConfiguration configuration, Environment environment) throws Exception {
        LOGGER.info("Application name: {}", configuration.getMessage());

        // Create a DBI factory and build a JDBI instance
         final DBIFactory factory = new DBIFactory();
         final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");

        // Add the resource to the environment
        environment.jersey().register(new ContactResource(jdbi));
    }
}
