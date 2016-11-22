package com.sandbox.dropwizard.phonebook;

import com.google.common.base.Optional;
import com.sandbox.dropwizard.phonebook.dao.UserDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.skife.jdbi.v2.DBI;


public class PhonebookAuthenticator implements Authenticator<BasicCredentials, Boolean> {

    private final UserDAO userDao;

    public PhonebookAuthenticator(DBI jdbi) {
        this.userDao = jdbi.onDemand(UserDAO.class);
    }

    @Override
    public Optional<Boolean> authenticate(BasicCredentials c) throws AuthenticationException {
        Boolean validUser = (userDao.getUser(c.getUsername(), c.getPassword()) == 1);
        if (validUser) {
            return Optional.of(true);
        }
        return Optional.absent();
    }
}
