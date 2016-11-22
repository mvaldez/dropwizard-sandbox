package com.sandbox.dropwizard.phonebook;

import com.sandbox.dropwizard.phonebook.resources.ClientResource;
import com.sandbox.dropwizard.phonebook.resources.ContactResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import io.dropwizard.jdbi.DBIFactory;
import com.sun.jersey.api.client.Client;
import io.dropwizard.client.JerseyClientBuilder;

public class MyApplication extends Application<PhonebookConfiguration> {
    static final Logger LOGGER = LoggerFactory.getLogger(MyApplication.class);

    public static void main(final String[] args) throws Exception {
        new MyApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<PhonebookConfiguration> b) {
    }

    @Override
    public void run(PhonebookConfiguration configuration, Environment environment) throws Exception {
        LOGGER.info("Application name: {}", configuration.getMessage());

        // Create a DBI factory and build a JDBI instance
         final DBIFactory factory = new DBIFactory();
         final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");

        // Add the resource to the environment
        environment.jersey().register(new ContactResource(jdbi, environment.getValidator()));

        // build the client and add the resource to the environment
        final Client client = new JerseyClientBuilder(environment).build("REST Client");
        environment.jersey().register(new ClientResource(client));
    }
}
