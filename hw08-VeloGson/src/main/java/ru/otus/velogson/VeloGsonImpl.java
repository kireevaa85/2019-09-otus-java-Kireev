package ru.otus.velogson;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
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
            constructJsonObject(objectBuilder, src, f);
        }
        return objectBuilder.build().toString();
    }

    private void constructJsonObject(JsonObjectBuilder objectBuilder, Object src, Field f) throws IllegalAccessException {
        f.setAccessible(true);
        var fValue = f.get(src);
        if (fValue == null) {
            return;
        }
        Class<?> fType = f.getType();
        if (Collection.class.isAssignableFrom(fType)) {
            int x = 0;
        } else if (Map.class.isAssignableFrom(fType)) {
            int x = 0;
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
                objectBuilder.add(f.getName(), Json.createArrayBuilder().ad);
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
