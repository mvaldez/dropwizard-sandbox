package com.sandbox.dropwizard.phonebook.views;

import com.sandbox.dropwizard.phonebook.representations.Contact;
import io.dropwizard.views.View;
/**
 * TODO: JAVADOC ME
 *
 * @author {@link "markvaldez@gmail.com"}
 */
public class ContactView extends View {
    private final Contact contact;

    public ContactView(Contact contact) {
        super("/views/contact.mustache");
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

}
