package ru.otus.velogson;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class VeloGsonImplTest {
    private VeloGson veloGson = new VeloGsonImpl();
    private DemoClass objSrc;

    @BeforeEach
    void setUp() {
        objSrc = new DemoClass();
    }

    @Test
    void toJson() {
        assertEquals("{\"age\":34,\"agrWrapper\":34,\"name\":\"MyName\",\"resident\":true,\"residentWrapper\":true,\"price\":10500.57,\"priceWrapper\":10500.57,\"salary\":10500.57,\"arrayEmpty\":[],\"array\":[1,2,3,4,5],\"arrayObjEmpty\":[],\"arrayObj\":[{\"maxSpeed\":160,\"name\":\"Lada Kalina\"},null,{\"maxSpeed\":220,\"name\":\"VW Polo\"}],\"garageEmptyList\":[],\"garageEmptySet\":[],\"garageEmptyMap\":{},\"garageEmptyQueue\":[]}",
                veloGson.toJson(objSrc));
    }

}

class DemoClass {
    private int age = 34;
    private Integer agrWrapper = 34;
    private String name = "MyName";
    private boolean resident = true;
    private Boolean residentWrapper = true;
    private double price = 10500.57;
    private Double priceWrapper = 10500.57;
    private BigDecimal salary = BigDecimal.valueOf(10500.57);

    private int[] arrayEmpty = {};
    private int[] array = {1, 2, 3, 4, 5};
    private Car[] arrayObjEmpty = {};
    private Car[] arrayObj = {new Car(160, "Lada Kalina"), null, new Car(220, "VW Polo")};

    private List<Car> garageEmptyList = Collections.EMPTY_LIST;
    private Set<Car> garageEmptySet = Collections.EMPTY_SET;
    private Map<String, Car> garageEmptyMap = Collections.EMPTY_MAP;
    private Queue<Car> garageEmptyQueue = new ArrayDeque();
}

class Car {
    private final int maxSpeed;
    private final String name;

    Car(int maxSpeed, String name) {
        this.maxSpeed = maxSpeed;
        this.name = name;
    }
}
