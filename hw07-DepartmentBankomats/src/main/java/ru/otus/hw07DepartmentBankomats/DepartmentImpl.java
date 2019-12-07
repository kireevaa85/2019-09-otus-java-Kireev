package ru.otus.hw07DepartmentBankomats;

import ru.otus.hw06Bankomat.atm.ATM;
import ru.otus.hw06Bankomat.atm.ATMListener;

import java.util.Collection;

public class DepartmentImpl implements Department {
    private final Collection<ATM> atmCollection;

    public DepartmentImpl(Collection<ATM> atmCollection) {
        this.atmCollection = atmCollection;
    }

    @Override
    public void addATM(ATM atm) {
        atmCollection.add(atm);
    }

    @Override
    public boolean removeATM(ATM atm) {
        return atmCollection.remove(atm);
    }

    @Override
    public int balance() {
        return atmCollection.stream().mapToInt(ATM::balance).sum();
    }

    @Override
    public void restore() {
        atmCollection.forEach(ATMListener::restore);
    }

}
