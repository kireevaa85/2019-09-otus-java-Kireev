package ru.otus.velogson;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.lang.model.type.ArrayType;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class VeloGsonImpl implements VeloGson {

    @Override
    public String toJson(Object src) throws IllegalAccessException {
        if (src == null) {
            return null;
        }
        var objectBuilder = Json.createObjectBuilder();
        var fields = src.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            constructJsonObject(objectBuilder, f, f.get(src));
        }
        return objectBuilder.build().toString();
    }

    private void constructJsonObject(JsonObjectBuilder objectBuilder, Field f, Object fValue) {
        if (fValue == null) {
            return;
        }
        Class<?> fType = f.getType();
        if (Collection.class.isAssignableFrom(fType)) {

        } else if (Map.class.isAssignableFrom(fType)) {

        } else if (Object.class.isAssignableFrom(fType)) {
            if (String.class.isAssignableFrom(fType)) {
                objectBuilder.add(f.getName(), (String) fValue);
            } else if (BigInteger.class.isAssignableFrom(fType)) {
                objectBuilder.add(f.getName(), (BigInteger) fValue);
            } else if (BigDecimal.class.isAssignableFrom(fType)) {
                objectBuilder.add(f.getName(), (BigDecimal) fValue);
            } else if (Integer.class.isAssignableFrom(fType)) {
                objectBuilder.add(f.getName(), (Integer) fValue);
            } else if (Long.class.isAssignableFrom(fType)) {
                objectBuilder.add(f.getName(), (Long) fValue);
            } else if (Float.class.isAssignableFrom(fType)) {
                objectBuilder.add(f.getName(), (Float) fValue);
            } else if (Double.class.isAssignableFrom(fType)) {
                objectBuilder.add(f.getName(), (Double) fValue);
            } else if (Boolean.class.isAssignableFrom(fType)) {
                objectBuilder.add(f.getName(), (Boolean) fValue);
            } else if (Enum.class.isAssignableFrom(fType)) {
                objectBuilder.add(f.getName(), fValue.toString());
            } else if (fType.isArray()) {
                JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

                for (int i = 0; i < Array.getLength(fValue); i++) {
                    var arrayObjectBuilder = Json.createObjectBuilder();
                    constructJsonObject(arrayObjectBuilder, Field f, Object fValue);
                    jsonArrayBuilder.add(arrayObjectBuilder);
                }

                objectBuilder.add(f.getName(), jsonArrayBuilder);
            }
        } else if (fType.isPrimitive()) {
            if (byte.class.isAssignableFrom(fType)) {
                objectBuilder.add(f.getName(), (byte) fValue);
            } else if (char.class.isAssignableFrom(fType)) {
                objectBuilder.add(f.getName(), String.valueOf((char) fValue));
            } else if (short.class.isAssignableFrom(fType)) {
                objectBuilder.add(f.getName(), (short) fValue);
            } else if (int.class.isAssignableFrom(fType)) {
                objectBuilder.add(f.getName(), (int) fValue);
            } else if (long.class.isAssignableFrom(fType)) {
                objectBuilder.add(f.getName(), (long) fValue);
            } else if (float.class.isAssignableFrom(fType)) {
                objectBuilder.add(f.getName(), (float) fValue);
            } else if (double.class.isAssignableFrom(fType)) {
                objectBuilder.add(f.getName(), (double) fValue);
            } else if (boolean.class.isAssignableFrom(fType)) {
                objectBuilder.add(f.getName(), (boolean) fValue);
            }
        }
    }

}
