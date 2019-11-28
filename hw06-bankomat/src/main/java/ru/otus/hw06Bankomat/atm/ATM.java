package ru.otus.hw06Bankomat.atm;

import ru.otus.hw06Bankomat.Banknote;

import java.util.List;

public interface ATM {

    /**
     * @param banknotes List of banknotes({@link Banknote}) for put in the current cassette
     * @throws NominalATMException If the ATM have no cassette for all given banknotes
     * @throws MaxSizeATMException If the ATM cassettes have no size for all given banknotes
     */
    void putBanknotes(List<Banknote> banknotes) throws NominalATMException, MaxSizeATMException;

    /**
     * @param sum Amount for return
     * @return Returns the requested amount from an ATM as a banknote list
     * @throws MoneySelectionATMException If it is not possible to issue a given sum
     */
    List<Banknote> getMoney(int sum) throws MoneySelectionATMException;

    /**
     * @return Return amount of the current ATM
     */
    int balance();

}
