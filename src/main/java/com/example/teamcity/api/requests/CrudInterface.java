package com.example.teamcity.api.requests;

public interface CrudInterface {

   Object create(Object object);

   Object get(String id);

   Object update(Object object);

   Object delete(String id);
}
