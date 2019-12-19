package ru.otus.veloorm.orm;

import java.util.ArrayList;
import java.util.List;

public class EntityDesc {
    private String className;
    private List<String> columnNames = new ArrayList<>();
    private String pkColumnName;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public String getPkColumnName() {
        return pkColumnName;
    }

    public void setPkColumnName(String pkColumnName) {
        this.pkColumnName = pkColumnName;
    }

}
