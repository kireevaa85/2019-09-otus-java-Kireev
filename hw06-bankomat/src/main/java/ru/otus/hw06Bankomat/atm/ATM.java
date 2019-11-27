package ru.otus.hw06Bankomat.atm;

import ru.otus.hw06Bankomat.Banknote;

import java.util.List;

public interface ATM {

    void putBanknotes(List<Banknote> banknotes) throws NominalATMException;

    List<Banknote> getMoney(int summ) throws Exception;

    int balance();

}
