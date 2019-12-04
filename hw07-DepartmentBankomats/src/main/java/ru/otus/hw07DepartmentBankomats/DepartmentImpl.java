package ru.otus.hw07DepartmentBankomats;

import ru.otus.hw06Bankomat.atm.ATM;
import ru.otus.hw07DepartmentBankomats.atm.DepATM;

import java.util.ArrayList;
import java.util.Collection;

public class DepartmentImpl implements Department {
    private final Collection<DepATM> atmList = new ArrayList<>();

    @Override
    public void addATM(DepATM atm) {
        atmList.add(atm);
    }

    @Override
    public boolean removeATM(DepATM atm) {
        return atmList.remove(atm);
    }

    @Override
    public int balance() {
        return atmList.stream().mapToInt(ATM::balance).sum();
    }

    @Override
    public void restore() {
        //// TODO: 05/12/2019
    }

}
