package ru.otus.hw07DepartmentBankomats.atm;

import ru.otus.hw06Bankomat.Banknote;
import ru.otus.hw06Bankomat.atm.ATMException;
import ru.otus.hw06Bankomat.atm.ATMImpl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DepATMImpl implements DepATM {

    private final ATMImpl atm;
    private final String uuid;

    public DepATMImpl(ATMImpl atm) {
        this.atm = atm;
        this.uuid = UUID.randomUUID().toString();
    }

    @Override
    public void putBanknotes(List<Banknote> banknotes) throws ATMException {
        atm.putBanknotes(banknotes);
    }

    @Override
    public List<Banknote> getMoney(int sum) throws ATMException {
        return atm.getMoney(sum);
    }

    @Override
    public int balance() {
        return atm.balance();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepATMImpl depATM = (DepATMImpl) o;
        return uuid.equals(depATM.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
