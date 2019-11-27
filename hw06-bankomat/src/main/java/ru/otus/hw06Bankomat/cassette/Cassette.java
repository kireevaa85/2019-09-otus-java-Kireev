package ru.otus.hw06Bankomat.cassette;

import ru.otus.hw06Bankomat.Banknote;

import java.util.List;

public interface Cassette {

    Banknote nominal();

    int maxCount();

    void putBanknotes(List<Banknote> banknotes) throws NominalCassetteException, MaxSizeCassetteException;

    List<Banknote> getBanknotes(int count) throws InsufficientAmountCassetteException;

    int count();

}
