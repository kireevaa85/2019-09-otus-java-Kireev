package ru.otus.hw07DepartmentBankomats;

import ru.otus.hw06Bankomat.atm.ATM;
import java.util.Collection;

public class DepartmentImpl implements Department {
    private final Collection<ATM> atmList;

    public DepartmentImpl(Collection<ATM> atmList) {
        this.atmList = atmList;
    }

    @Override
    public void addATM(ATM atm) {
        atmList.add(atm);
    }

    @Override
    public boolean removeATM(ATM atm) {
        return atmList.remove(atm);
    }

    @Override
    public int balance() {
        return atmList.stream().mapToInt(ATM::balance).sum();
    }

    @Override
    public void restore() {
        atmList.forEach(ATM::restore);
    }

}
