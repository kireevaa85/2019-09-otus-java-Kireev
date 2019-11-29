package ru.otus.hw06Bankomat.atm;

import ru.otus.hw06Bankomat.Banknote;

import java.util.List;

public interface ATM {

    /**
     * @param banknotes List of banknotes({@link Banknote}) for put in the current cassette
     * @throws ATMException If the ATM have no cassette for all given banknotes
     * @throws ATMException If the ATM cassettes have no size for all given banknotes
     */
    void putBanknotes(List<Banknote> banknotes) throws ATMException;

    /**
     * @param sum Amount for return
     * @return Returns the requested amount from an ATM as a banknote list
     * @throws ATMException If it is not possible to issue a given sum
     * @throws IllegalArgumentException If the sum zero or negative
     */
    List<Banknote> getMoney(int sum) throws ATMException;

    /**
     * @return Return amount of the current ATM
     */
    int balance();

}
