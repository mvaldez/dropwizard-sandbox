package com.sandbox.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

/**
 * TODO: JAVADOC ME
 *
 * @author {@link "markvaldez@gmail.com"}
 */
public class PhonebookConfiguration extends Configuration {

    @JsonProperty
    private String message;

    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    public String getMessage() {
        return message;
    }

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

}
