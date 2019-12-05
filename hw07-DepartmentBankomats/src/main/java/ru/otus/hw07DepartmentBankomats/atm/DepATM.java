package ru.otus.hw07DepartmentBankomats.atm;

import ru.otus.hw06Bankomat.atm.ATM;
import ru.otus.hw06Bankomat.atm.ATMException;

public interface DepATM extends ATM {

    String uuid();

    void restore() throws ATMException;

}
