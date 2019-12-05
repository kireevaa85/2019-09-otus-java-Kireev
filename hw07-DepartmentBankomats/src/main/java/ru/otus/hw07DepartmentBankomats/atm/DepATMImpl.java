package ru.otus.hw07DepartmentBankomats.atm;

import ru.otus.hw06Bankomat.Banknote;
import ru.otus.hw06Bankomat.atm.ATMException;
import ru.otus.hw06Bankomat.atm.ATMImpl;
import ru.otus.hw06Bankomat.cassette.Cassette;
import ru.otus.hw06Bankomat.cassette.CassetteImpl;

import java.lang.reflect.Field;
import java.util.*;

public class DepATMImpl implements DepATM {
    private ATMImpl atm;
    private final String uuid;
    private Memento memento;

    public DepATMImpl(ATMImpl atm) throws NoSuchFieldException, IllegalAccessException {
        this.atm = atm;
        this.uuid = UUID.randomUUID().toString();
        this.memento = new Memento();
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
    public String uuid() {
        return uuid;
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

    @Override
    public void restore() throws ATMException {
        try {
            this.atm = cloneATMImpl(memento.getAtmStamp());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ATMException("Restore ATM error, please call support!");
        }
    }

    private class Memento {
        private final ATMImpl atmStamp;

        private Memento() throws NoSuchFieldException, IllegalAccessException {
            this.atmStamp = cloneATMImpl(atm);
        }

        private ATMImpl getAtmStamp() {
            return atmStamp;
        }
    }

    private ATMImpl cloneATMImpl(ATMImpl src) throws NoSuchFieldException, IllegalAccessException {
        Field fCassette = src.getClass().getDeclaredField("cassettes");
        fCassette.setAccessible(true);
        TreeMap<Banknote, CassetteImpl> cassettes = (TreeMap<Banknote, CassetteImpl>) fCassette.get(src);
        Collection<Cassette> cassettesCollection = new ArrayList<>();
        for (CassetteImpl cassette : cassettes.values()) {
            Field fMaxCount = cassette.getClass().getDeclaredField("maxCount");
            cassettesCollection.add(new CassetteImpl(cassette.nominal(), (Integer) fMaxCount.get(cassette), cassette.count()));
        }
        return new ATMImpl(cassettesCollection);
    }

}
