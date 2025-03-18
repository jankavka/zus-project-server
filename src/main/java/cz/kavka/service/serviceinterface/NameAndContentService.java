package cz.kavka.service.serviceinterface;

/**
 * Interface created as pattern for several service classes with same methods.
 * @param <D>
 */
public interface NameAndContentService<D>{

    D createOrEdit(D D);

    D get (D d);
}
