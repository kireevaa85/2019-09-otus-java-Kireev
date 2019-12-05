package ru.otus.hw07DepartmentBankomats;

import ru.otus.hw06Bankomat.atm.ATMException;
import ru.otus.hw07DepartmentBankomats.atm.DepATM;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DepartmentImpl implements Department {
    private final Collection<DepATM> atmList;

    public DepartmentImpl(Collection<DepATM> atmList) {
        this.atmList = atmList;
    }

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
        return atmList.stream().mapToInt(DepATM::balance).sum();
    }

    @Override
    public void restore() throws DepartmentException {
        List<String> uuidsNotRestored = new ArrayList<>();
        atmList.forEach(depATM -> {
            try {
                depATM.restore();
            } catch (ATMException e) {
                uuidsNotRestored.add(depATM.uuid());
            }
        });
        if (!uuidsNotRestored.isEmpty()) {
            throw new DepartmentException("ATMs with uuids = {" + String.join(",", uuidsNotRestored) + "} not restored!");
        }
    }

}
