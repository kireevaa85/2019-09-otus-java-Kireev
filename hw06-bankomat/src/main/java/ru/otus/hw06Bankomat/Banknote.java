package ru.otus.hw06Bankomat;

public enum Banknote {
    TEN(10),
    FIFTY(50),
    ONE_NUDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000);

    private int nominal;

    Banknote(int nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }
}
