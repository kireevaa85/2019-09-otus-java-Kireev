package ru.otus.hw07DepartmentBankomats.atm;

import ru.otus.hw06Bankomat.atm.ATM;
import ru.otus.hw06Bankomat.atm.ATMException;

public interface DepATM extends ATM {

    /**
     * @return Retutn unique UUID for current DepATM
     */
    String uuid();

    /**
     * Restores the ATM to its original state
     * @throws ATMException If to restore the ATM failed
     */
    void restore() throws ATMException;

}
