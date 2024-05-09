package com.example.teamcity.api.requests;

public interface CrudInterface<T> {
    Object create(T obj);
    Object get(String id);
    Object update(T obj);
    Object delete(String id);
}
