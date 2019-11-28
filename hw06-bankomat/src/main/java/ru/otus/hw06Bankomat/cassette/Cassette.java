package ru.otus.hw06Bankomat.cassette;

import ru.otus.hw06Bankomat.Banknote;

import java.util.List;

public interface Cassette {

    Banknote nominal();

    void putBanknotes(int count) throws MaxSizeCassetteException;

    List<Banknote> getBanknotes(int count) throws InsufficientAmountCassetteException;

    int count();

    boolean noPlace(int addCount);

}
