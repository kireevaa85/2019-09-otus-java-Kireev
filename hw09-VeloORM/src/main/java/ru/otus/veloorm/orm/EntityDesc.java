package ru.otus.veloorm.orm;

import java.util.ArrayList;
import java.util.List;

public final class EntityDesc {
    private final String className;
    private final List<String> columnNames;
    private final String pkColumnName;

    public EntityDesc(String className, List<String> columnNames, String pkColumnName) {
        this.className = className;
        this.columnNames = List.copyOf(columnNames != null ? columnNames : new ArrayList<>());
        this.pkColumnName = pkColumnName;
    }

    public String getClassName() {
        return className;
    }

    public List<String> getColumnNames() {
        return List.copyOf(columnNames);
    }

    public String getPkColumnName() {
        return pkColumnName;
    }

}
