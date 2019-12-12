package ru.otus.velogson;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;

public class VeloGsonImpl implements VeloGson {

    @Override
    public String toJson(Object src) throws IllegalAccessException {
        if (src == null) {
            return "null";
        }
        return toJsonObjectBuilder(src).build().toString();
    }

    private JsonObjectBuilder toJsonObjectBuilder(Object src) throws IllegalAccessException {
        var objectBuilder = Json.createObjectBuilder();
        Class<?> aClass = src.getClass();
        while (!aClass.equals(Object.class)) {
            var fields = aClass.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                Object fValue = f.get(src);
                if (fValue == null || Modifier.isTransient(f.getModifiers())) {
                    continue;
                }
                fillJsonObjectBuilder(objectBuilder, f.getName(), f.getType(), fValue);
            }
            aClass = aClass.getSuperclass();
        }
        return objectBuilder;
    }

    private JsonObjectBuilder toJsonMapBuilder(Object fValue) throws IllegalAccessException {
        var objectBuilder = Json.createObjectBuilder();
        for (Object key : ((Map) fValue).keySet()) {
            Object value = ((Map) fValue).get(key);
            fillJsonObjectBuilder(objectBuilder, key.toString(), value.getClass(), value);
        }
        return objectBuilder;
    }

    private JsonArrayBuilder toJsonArrayBuilder(Collection fCollection) throws IllegalAccessException {
        return toJsonArrayBuilder(fCollection.toArray());
    }

    private JsonArrayBuilder toJsonArrayBuilder(Object fArray) throws IllegalAccessException {
        var jsonArrayBuilder = Json.createArrayBuilder();
        int length = Array.getLength(fArray);
        for (int i = 0; i < length; i++) {
            Object value = Array.get(fArray, i);
            if (value == null) {
                jsonArrayBuilder.addNull();
            } else {
                fillJsonArrayBuilder(jsonArrayBuilder, value.getClass(), value);
            }
        }
        return jsonArrayBuilder;
    }

    private void fillJsonObjectBuilder(JsonObjectBuilder builder, String fName, Class<?> fType, Object fValue) throws IllegalAccessException {
        if (Collection.class.isAssignableFrom(fType)) {
            builder.add(fName, toJsonArrayBuilder((Collection) fValue));
        } else if (Map.class.isAssignableFrom(fType)) {
            builder.add(fName, toJsonMapBuilder(fValue));
        } else if (Object.class.isAssignableFrom(fType)) {
            if (String.class.isAssignableFrom(fType)) {
                builder.add(fName, (String) fValue);
            } else if (BigInteger.class.isAssignableFrom(fType)) {
                builder.add(fName, (BigInteger) fValue);
            } else if (BigDecimal.class.isAssignableFrom(fType)) {
                builder.add(fName, (BigDecimal) fValue);
            } else if (Integer.class.isAssignableFrom(fType)) {
                builder.add(fName, (Integer) fValue);
            } else if (Long.class.isAssignableFrom(fType)) {
                builder.add(fName, (Long) fValue);
            } else if (Float.class.isAssignableFrom(fType)) {
                builder.add(fName, (Float) fValue);
            } else if (Double.class.isAssignableFrom(fType)) {
                builder.add(fName, (Double) fValue);
            } else if (Boolean.class.isAssignableFrom(fType)) {
                builder.add(fName, (Boolean) fValue);
            } else if (Enum.class.isAssignableFrom(fType)) {
                builder.add(fName, fValue.toString());
            } else if (fType.isArray()) {
                builder.add(fName, toJsonArrayBuilder(fValue));
            } else {
                builder.add(fName, toJsonObjectBuilder(fValue));
            }
        } else if (fType.isPrimitive()) {
            if (byte.class.isAssignableFrom(fType)) {
                builder.add(fName, (byte) fValue);
            } else if (char.class.isAssignableFrom(fType)) {
                builder.add(fName, String.valueOf((char) fValue));
            } else if (short.class.isAssignableFrom(fType)) {
                builder.add(fName, (short) fValue);
            } else if (int.class.isAssignableFrom(fType)) {
                builder.add(fName, (int) fValue);
            } else if (long.class.isAssignableFrom(fType)) {
                builder.add(fName, (long) fValue);
            } else if (float.class.isAssignableFrom(fType)) {
                builder.add(fName, (float) fValue);
            } else if (double.class.isAssignableFrom(fType)) {
                builder.add(fName, (double) fValue);
            } else if (boolean.class.isAssignableFrom(fType)) {
                builder.add(fName, (boolean) fValue);
            }
        }
    }

    private void fillJsonArrayBuilder(JsonArrayBuilder builder, Class<?> fType, Object fValue) throws IllegalAccessException {
        if (Collection.class.isAssignableFrom(fType)) {
            builder.add(toJsonArrayBuilder((Collection) fValue));
        } else if (Map.class.isAssignableFrom(fType)) {
            builder.add(toJsonMapBuilder(fValue));
        } else if (Object.class.isAssignableFrom(fType)) {
            if (String.class.isAssignableFrom(fType)) {
                builder.add((String) fValue);
            } else if (BigInteger.class.isAssignableFrom(fType)) {
                builder.add((BigInteger) fValue);
            } else if (BigDecimal.class.isAssignableFrom(fType)) {
                builder.add((BigDecimal) fValue);
            } else if (Integer.class.isAssignableFrom(fType)) {
                builder.add((Integer) fValue);
            } else if (Long.class.isAssignableFrom(fType)) {
                builder.add((Long) fValue);
            } else if (Float.class.isAssignableFrom(fType)) {
                builder.add((Float) fValue);
            } else if (Double.class.isAssignableFrom(fType)) {
                builder.add((Double) fValue);
            } else if (Boolean.class.isAssignableFrom(fType)) {
                builder.add((Boolean) fValue);
            } else if (Enum.class.isAssignableFrom(fType)) {
                builder.add(fValue.toString());
            } else if (fType.isArray()) {
                builder.add(toJsonArrayBuilder(fValue));
            } else {
                builder.add(toJsonObjectBuilder(fValue));
            }
        } else if (fType.isPrimitive()) {
            if (byte.class.isAssignableFrom(fType)) {
                builder.add((byte) fValue);
            } else if (char.class.isAssignableFrom(fType)) {
                builder.add(String.valueOf((char) fValue));
            } else if (short.class.isAssignableFrom(fType)) {
                builder.add((short) fValue);
            } else if (int.class.isAssignableFrom(fType)) {
                builder.add((int) fValue);
            } else if (long.class.isAssignableFrom(fType)) {
                builder.add((long) fValue);
            } else if (float.class.isAssignableFrom(fType)) {
                builder.add((float) fValue);
            } else if (double.class.isAssignableFrom(fType)) {
                builder.add((double) fValue);
            } else if (boolean.class.isAssignableFrom(fType)) {
                builder.add((boolean) fValue);
            }
        }
    }

}
