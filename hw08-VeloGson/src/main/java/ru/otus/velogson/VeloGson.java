package ru.otus.velogson;

public interface VeloGson {

    /**
     * @param src source object
     * @return source object in json {@link String} format
     */
    String toJson(Object src) throws IllegalAccessException;

}
