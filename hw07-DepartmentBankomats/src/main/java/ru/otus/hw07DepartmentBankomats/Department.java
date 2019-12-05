package ru.otus.hw07DepartmentBankomats;

import ru.otus.hw07DepartmentBankomats.atm.DepATM;

public interface Department {

    /**
     * @param atm DepATM for add to the current department
     */
    void addATM(DepATM atm);

    /**
     * @param atm DepATM for remove to the current department
     * @return {@code true} if an element was removed as a result of this call
     */
    boolean removeATM(DepATM atm);

    /**
     * @return Sum of all DepATMs balance
     */
    int balance();

    /**
     * Restores the all department ATMs to its original state
     * @throws DepartmentException If to restore the current department failed
     */
    void restore() throws DepartmentException;

}
