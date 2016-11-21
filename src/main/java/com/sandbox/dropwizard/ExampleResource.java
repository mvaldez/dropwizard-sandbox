package com.sandbox.dropwizard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author {@link "mark.valdez@gmail.com"}
 */

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ExampleResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id") int id) {

        // TODO: query for contact

        return Response.ok("{contact_id: " + id + ", name: \"Dummy Name\",phone: \"+0123456789\" }")
                .build();
    }

    @POST
    public Response createContact(
            @FormParam("name") String name,
            @FormParam("phone") String phone) {

        // TODO: store contact

        return Response.created(null)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id) {

        // TODO: delete contact for the id argument

        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    public Response updateContact(@PathParam("id") int id,
                                  @FormParam("name") String name,
                                  @FormParam("phone") String phone) {

        // TODO: update contact given id

        return Response.ok("{contact_id: "+ id +", name: \""+ name +"\",phone: \""+ phone +"\" }")
                .build();
    }
}
