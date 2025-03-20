package cz.kavka.service.serviceinterface;

/**
 * Creates or edits a record in database. Main principle is that there will be always one record in specific
 * database table (Long id = 1L) serviced by classes which implements NameAndContent.
 * @param <D> An DTO object with data of new object
 */
public interface TitleAndContentService<D>{

    D createOrEdit(D D);

    D get ();
}
