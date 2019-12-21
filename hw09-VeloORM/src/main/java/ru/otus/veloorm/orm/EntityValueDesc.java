package ru.otus.veloorm.orm;

import java.util.ArrayList;
import java.util.List;

public final class EntityValueDesc {
    private final EntityDesc entityDesc;
    private final String pkValue;
    private final List<String> columnValues;

    public EntityValueDesc(EntityDesc entityDesc, String pkValue, List<String> columnValues) {
        this.entityDesc = entityDesc;
        this.pkValue = pkValue;
        this.columnValues = List.copyOf(columnValues != null ? columnValues : new ArrayList<>());
    }

    public EntityDesc getEntityDesc() {
        return entityDesc;
    }

    public String getPkValue() {
        return pkValue;
    }

    public List<String> getColumnValues() {
        return List.copyOf(columnValues);
    }

}
