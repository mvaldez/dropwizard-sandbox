package com.sandbox.dao;

import com.sandbox.dao.mappers.ContactMapper;
import com.sandbox.representations.Contact;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

/**
 * TODO: JAVADOC ME
 *
 * @author {@link "markvaldez@gmail.com"}
 */
public interface ContactDAO {

    @Mapper(ContactMapper.class)
    @SqlQuery("select * from contact where id = :id")
    Contact getContactById(@Bind("id") int id);
}
