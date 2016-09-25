package com.iowniwant.service;

public interface Service<T> {
    T getById(Long id);

    T save(T entity);
}
