package ru.otus.hw07DepartmentBankomats;

import ru.otus.hw07DepartmentBankomats.atm.DepATM;

public interface Department {

    void addATM(DepATM atm);

    boolean removeATM(DepATM atm);

    int balance();

    void restore() throws DepartmentException;

}
