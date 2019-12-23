package ru.otus.veloorm.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.veloorm.jdbc.DbExecutor;
import ru.otus.veloorm.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class JdbcTemplateImpl implements JdbcTemplate {
    private static Logger logger = LoggerFactory.getLogger(JdbcTemplateImpl.class);

    private final EntityMetadataHolder entityMetadataHolder = new EntityMetadataHolder();
    private final Map<String, String> createSqlCache = new HashMap<>();
    private final Map<String, String> loadSqlCache = new HashMap<>();

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor dbExecutor;

    public JdbcTemplateImpl(SessionManagerJdbc sessionManager) {
        this.sessionManager = sessionManager;
        this.dbExecutor = new DbExecutor();
    }

    @Override
    public <T> long create(T objectData) {
        try {
            EntityValueDesc entityValueDesc = entityMetadataHolder.parse(objectData);
            return dbExecutor.insertRecord(getConnection(),
                    getCreateSql(objectData.getClass().getName(), entityValueDesc),
                    entityValueDesc.getColumnValues());
        } catch (NoSuchFieldException | IllegalAccessException | SQLException e) {
            logger.error(e.getMessage(), e);
            return -1;
        }
    }

    @Override
    public <T> T load(long id, Class<T> clazz) {
        EntityDesc entityDesc = entityMetadataHolder.parse(clazz);
        try {
            var result = dbExecutor.selectRecord(getConnection(), getLoadSql(clazz.getName(), entityDesc), id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        return entityMetadataHolder.deserialize(resultSet, clazz, entityDesc);
                    }
                } catch (SQLException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchFieldException e) {
                    logger.error(e.getMessage(), e);
                }
                return null;
            });
            if (result.isPresent()) {
                return (T) result.get();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public <T> void update(T objectData) {
        try {
            EntityValueDesc entityValueDesc = entityMetadataHolder.parse(objectData);
            dbExecutor.insertRecord(getConnection(),
                    "update " +
                            entityValueDesc.getEntityDesc().getClassName() +
                            " set " +
                            constructSetters(entityValueDesc) +
                            " where " +
                            entityValueDesc.getEntityDesc().getPkColumnName() +
                            " = ?", Collections.singletonList(entityValueDesc.getPkValue()));
        } catch (NoSuchFieldException | IllegalAccessException | SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String constructSetters(EntityValueDesc entityValueDesc) {
        List<String> setters = new ArrayList<>();
        var columnNames = entityValueDesc.getEntityDesc().getColumnNames();
        var columnValues = entityValueDesc.getColumnValues();
        for (int i = 0; i < columnNames.size(); i++) {
            setters.add(columnNames.get(i) + " = '" + columnValues.get(i) + "'");
        }
        return String.join(", ", setters);
    }

    private String getCreateSql(String className, EntityValueDesc entityValueDesc) {
        if (createSqlCache.containsKey(className)) {
            return createSqlCache.get(className);
        }
        String result = "insert into " + entityValueDesc.getEntityDesc().getClassName() +
                "(" + String.join(", ", entityValueDesc.getEntityDesc().getColumnNames()) + ")" +
                " values (" + "?" + " ,?".repeat(entityValueDesc.getEntityDesc().getColumnNames().size() - 1) + ")";
        createSqlCache.put(className, result);
        return createSqlCache.get(className);
    }

    private String getLoadSql(String className, EntityDesc entityDesc) {
        if (loadSqlCache.containsKey(className)) {
            return loadSqlCache.get(className);
        }
        String result = "select " + entityDesc.getPkColumnName() + ", " + String.join(", ",
                entityDesc.getColumnNames()) + " from " + entityDesc.getClassName() +
                " where " + entityDesc.getPkColumnName() + "  = ?";
        loadSqlCache.put(className, result);
        return loadSqlCache.get(className);
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
