package com.sandbox.dropwizard.phonebook.resources;

import com.sandbox.dropwizard.phonebook.dao.ContactDAO;
import com.sandbox.dropwizard.phonebook.representations.Contact;
import io.dropwizard.auth.Auth;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author {@link "mark.valdez@gmail.com"}
 */

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ContactDAO contactDao;
    private final Validator validator;

    public ContactResource(DBI jdbi, Validator validator) {
        contactDao = jdbi.onDemand(ContactDAO.class);
        this.validator = validator;
    }

    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id") int id, @Auth(required = false) Boolean isAuthenticated) {

        // retrieve information about the contact with the provided id
        Contact contact = contactDao.getContactById(id);

        logger.info("Successfully retrieved contact with id " + id + ". Contact: " + contact);

        return Response.ok(contact)
                .build();
    }

    @POST
    public Response createContact(Contact contact, @Auth Boolean isAuthenticated) throws URISyntaxException {
        // Validate the contact's data
        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
        // Are there any constraint violations?
        if (violations.size() > 0) {
            // Validation errors occurred
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<Contact> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(validationMessages).build();
        } else {      // OK, no validation errors
            // Store the new contact
            int newContactId = contactDao.createContact(contact.getFirstName(),
                    contact.getLastName(), contact.getPhone());

            return Response.created(
                    new URI(String.valueOf(newContactId)))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id, @Auth Boolean isAuthenticated) {

        // delete the contact with the provided id
        contactDao.deleteContact(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    public Response updateContact(@PathParam("id") int id, Contact contact, @Auth Boolean isAuthenticated) {

        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<>();
            for (ConstraintViolation<Contact> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(validationMessages)
                    .build();
        } else {
            // update the contact with the provided ID
            contactDao.updateContact(id, contact.getFirstName(),
                    contact.getLastName(), contact.getPhone());

            return Response.ok(
                    new Contact(id, contact.getFirstName(),
                            contact.getLastName(), contact.getPhone()))
                    .build();
        }
    }
}
