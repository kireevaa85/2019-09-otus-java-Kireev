package ru.otus.hw07DepartmentBankomats;

import ru.otus.hw06Bankomat.atm.ATM;

public interface Department {

    /**
     * @param atm ATM for add to the current department
     */
    void addATM(ATM atm);

    /**
     * @param atm ATM for remove to the current department
     * @return {@code true} if an element was removed as a result of this call
     */
    boolean removeATM(ATM atm);

    /**
     * @return Sum of all ATMs balance
     */
    int balance();

    /**
     * Restores the all department ATMs to its original state
     */
    void restore();

}
