package ru.otus.veloorm.orm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class EntityHelper {

    public static EntityDesc parse(Class clazz) {
        EntityDesc result = new EntityDesc();
        result.setClassName(clazz.getSimpleName());
        var fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            if (Modifier.isTransient(f.getModifiers())) {
                continue;
            }
            result.getColumnNames().add(f.getName());
            if (f.getDeclaredAnnotation(Id.class) != null) {
                result.setPkColumnName(f.getName());
            }
        }
        return result;
    }

    public static <T> EntityValueDesc parse(T objectData) throws NoSuchFieldException, IllegalAccessException {
        Class<?> aClass = objectData.getClass();
        EntityValueDesc result = new EntityValueDesc(parse(aClass));
        for (String fName : result.getEntityDesc().getColumnNames()) {
            var f = aClass.getDeclaredField(fName);
            f.setAccessible(true);
            result.getColumnValues().add(f.get(objectData).toString());
            if (fName.equals(result.getEntityDesc().getPkColumnName())) {
                result.setPkValue(f.get(objectData).toString());
            }
        }
        return result;
    }

    public static <T> T deserialize(ResultSet resultSet, Class<T> clazz, EntityDesc entityDesc) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, SQLException {
        var instance = clazz.getConstructor().newInstance();
        var fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            if (entityDesc.getColumnNames().contains(f.getName())) {
                f.setAccessible(true);
                f.set(instance, resultSet.getObject(f.getName()));
            }
        }
        return instance;
    }

}
