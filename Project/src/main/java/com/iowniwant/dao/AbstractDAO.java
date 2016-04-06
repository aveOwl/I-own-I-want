package com.iowniwant.dao;

import java.util.List;

/**
 * Used for accessing basic CRUD operations on desired objects.
 * @param <T> the type of object to be manipulated
 */
public interface AbstractDAO<T> {

    /**
     * Makes transient object persistent. Attach it to the DataBase.
     * @param entity transient object of type T
     * @return persistent object of type T or null if SQLException was thrown
     */
    T create(T entity);

    /**
     * Makes persistent object transient. Removes it from the DataBase.
     * @param entity persistent object of type T
     */
    void delete(T entity);

    /**
     * Makes detached object persistent. Updates it in the DataBase.
     * @param entity detached object of type T
     * @return persistent object of type T or null if there is no such persistent object
     */
    T update(T entity);

    /**
     * Returns persistent object with given identifier
     * @param id object identifier
     * @return persistent object of the type T with the given identifier or null if
     * there is no such persistent object
     */
    T getById(Integer id);

    /**
     * Fetches all persistent objects from the DataBase
     * @return List of persistent objects of type T
     */
    List<T> getAll();
}
