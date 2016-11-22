package com.sandbox.dropwizard.phonebook;

import com.sandbox.dropwizard.phonebook.resources.ClientResource;
import com.sandbox.dropwizard.phonebook.resources.ContactResource;
import com.sun.jersey.api.client.Client;
import io.dropwizard.Application;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyApplication extends Application<PhonebookConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyApplication.class);

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

        // register the authenticator with the environment
        environment.jersey().register(new BasicAuthProvider<Boolean>(
                new PhonebookAuthenticator(jdbi), "Web Service Realm"));
//        client.addFilter(new HTTPBasicAuthFilter("john_doe", "secret"));
    }
}
