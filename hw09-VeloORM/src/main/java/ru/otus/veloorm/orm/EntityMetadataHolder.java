package ru.otus.veloorm.orm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityMetadataHolder {

    private final Map<String, EntityDesc> entityDescCache = new HashMap<>();

    public EntityDesc parse(Class clazz) {
        if (entityDescCache.containsKey(clazz.getName())) {
            return entityDescCache.get(clazz.getName());
        }
        String pkColumnName = null;
        List<String> columnNames = new ArrayList<>();
        var fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            if (Modifier.isTransient(f.getModifiers())) {
                continue;
            }
            if (f.getDeclaredAnnotation(Id.class) != null) {
                pkColumnName = f.getName();
                continue;
            }
            columnNames.add(f.getName());
        }
        entityDescCache.put(clazz.getName(), new EntityDesc(clazz.getSimpleName(), columnNames, pkColumnName));
        return entityDescCache.get(clazz.getName());
    }

    public <T> EntityValueDesc parse(T objectData) throws NoSuchFieldException, IllegalAccessException {
        Class<?> aClass = objectData.getClass();
        EntityDesc entityDesc = parse(aClass);
        List<String> columnValues = new ArrayList<>();
        String pkValue;
        for (String fName : entityDesc.getColumnNames()) {
            var f = aClass.getDeclaredField(fName);
            f.setAccessible(true);
            columnValues.add(f.get(objectData).toString());
        }
        var pkField = aClass.getDeclaredField(entityDesc.getPkColumnName());
        pkField.setAccessible(true);
        pkValue = pkField.get(objectData).toString();
        return new EntityValueDesc(entityDesc, pkValue, columnValues);
    }

    public <T> T deserialize(ResultSet resultSet, Class<T> clazz, EntityDesc entityDesc) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, SQLException {
        var instance = clazz.getConstructor().newInstance();
        var fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            if (entityDesc.getColumnNames().contains(f.getName()) || entityDesc.getPkColumnName().equals(f.getName())) {
                f.setAccessible(true);
                f.set(instance, resultSet.getObject(f.getName()));
            }
        }
        return instance;
    }

}
