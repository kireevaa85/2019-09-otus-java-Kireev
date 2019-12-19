package ru.otus.veloorm.orm;

import java.util.ArrayList;
import java.util.List;

public class EntityValueDesc {
    private EntityDesc entityDesc;
    private String pkValue;
    private List<String> columnValues = new ArrayList<>();

    public EntityValueDesc(EntityDesc entityDesc) {
        this.entityDesc = entityDesc;
    }

    public EntityDesc getEntityDesc() {
        return entityDesc;
    }

    public void setEntityDesc(EntityDesc entityDesc) {
        this.entityDesc = entityDesc;
    }

    public String getPkValue() {
        return pkValue;
    }

    public void setPkValue(String pkValue) {
        this.pkValue = pkValue;
    }

    public List<String> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(List<String> columnValues) {
        this.columnValues = columnValues;
    }
}
