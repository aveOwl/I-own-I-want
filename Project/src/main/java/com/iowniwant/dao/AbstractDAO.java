package com.iowniwant.dao;

import java.util.List;

public interface AbstractDAO<T> {

    T create(T entity);
    void delete(T entity);
    T update(T entity);
    T getById(Integer id);
    T getByNick(String nickname);
    List<T> getAll();
}