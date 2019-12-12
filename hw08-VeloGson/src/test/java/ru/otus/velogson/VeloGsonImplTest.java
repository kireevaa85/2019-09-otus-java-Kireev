package ru.otus.velogson;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class VeloGsonImplTest {
    private Gson gson = new Gson();
    private VeloGson veloGson = new VeloGsonImpl();
    private DemoClass objSrc;

    @BeforeEach
    void setUp() {
        objSrc = new DemoClass();
    }

    @Test
    void toJson() throws IOException, IllegalAccessException {
        assertEquals(gson.toJson(objSrc), veloGson.toJson(objSrc));
        //Array
        //Collection
        //Map
        assertEquals(gson.toJson(Sex.WOMAN), veloGson.toJson(Sex.WOMAN));
        assertEquals(gson.toJson(null), veloGson.toJson(null));
        assertEquals(gson.toJson(100), veloGson.toJson(100));
        assertEquals(gson.toJson("100"), veloGson.toJson("100"));
        assertEquals(gson.toJson(true), veloGson.toJson(true));
    }

}

class DemoClassFather {
    private int fatherField = 10;
}

class DemoClass extends DemoClassFather {
    private byte fByte = -30;
    private char fChar = 31;
    private short fShort = -32;
    private transient int fTransient = 33;
    private int age = 34;
    private Integer agrWrapper = 34;
    private String name = "MyName";
    private boolean resident = true;
    private Boolean residentWrapper = true;
    private double price = 10500.57;
    private Double priceWrapper = 10500.57;
    private BigDecimal salary = BigDecimal.valueOf(10500.57);
    private BigInteger salaryNull = null;

    private Sex fEnum = Sex.MAN;

    private Car carNull = null;
    private Car carObj = new Car(320, "Nissan Skyline");

    private int[] arrayEmpty = {};
    private int[] arrayNumbers = {1, 2, 3, 4, 5};
    private String[] arrayStrings = {"1", "2", "3", "4", "5"};
    private Car[] arrayObjEmpty = {};
    private Car[] arrayObj = {new Car(160, "Lada Kalina"), null, new Car(220, "VW Polo")};
    private Car[] arrayObjNull = null;

    private List<Car> garageEmptyList = Collections.emptyList();
    private Set<Car> garageEmptySet = Collections.emptySet();
    private Map<String, Car> garageEmptyMap = Collections.emptyMap();
    private Queue<Car> garageEmptyQueue = new ArrayDeque();

    private List<String> stringList = Arrays.asList("first", "second", "third", "etc");
    private List<Car> garageList = Arrays.asList(new Car(260, "VW Passat"), null, new Car(280, "VW Tiguan"));
    private Set<Car> garageSet = Set.of(new Car(260, "VW Passat"), new Car(280, "VW Tiguan"));
    private Map<String, Car> garageMap = Map.of("1", new Car(160, "Lada Kalina"),
            "2", new Car(220, "VW Polo"),
            "3", new Car(260, "VW Passat"));
    private Map<Car, String> garageMapInversion = Map.of(new Car(160, "Lada Kalina"), "1",
            new Car(220, "VW Polo"), "2",
            new Car(260, "VW Passat"), "3");
    private Queue<Car> garageQueue = new ArrayDeque();

    public DemoClass() {
        this.garageQueue.add(new Car(160, "Lada Kalina"));
        this.garageQueue.add(new Car(260, "VW Passat"));
        this.garageQueue.add(new Car(220, "VW Polo"));
    }

}

class Car {
    private final int maxSpeed;
    private final String name;

    Car(int maxSpeed, String name) {
        this.maxSpeed = maxSpeed;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return name.equals(car.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

enum Sex {
    MAN,
    WOMAN
}
