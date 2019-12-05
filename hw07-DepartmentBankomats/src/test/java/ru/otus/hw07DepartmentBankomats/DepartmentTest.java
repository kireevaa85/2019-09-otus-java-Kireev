package ru.otus.hw07DepartmentBankomats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw06Bankomat.Banknote;
import ru.otus.hw06Bankomat.atm.ATMException;
import ru.otus.hw06Bankomat.atm.ATMImpl;
import ru.otus.hw06Bankomat.cassette.Cassette;
import ru.otus.hw06Bankomat.cassette.CassetteException;
import ru.otus.hw06Bankomat.cassette.CassetteImpl;
import ru.otus.hw07DepartmentBankomats.atm.DepATM;
import ru.otus.hw07DepartmentBankomats.atm.DepATMImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {
    private static final int MAX_FIFTY = 80;
    private static final int MAX_ONE_HUNDRED = 60;
    private static final int MAX_TWO_HUNDRED = 40;
    private static final int MAX_FIVE_HUNDRED = 20;
    private static final int MAX_ONE_THOUSAND = 10;
    private DepartmentImpl department;

    @BeforeEach
    void setUp() throws CassetteException, NoSuchFieldException, IllegalAccessException {
        DepATM depATM7000 = depATMCreator7000();
        DepATM depATM4000 = depATMCreator4000();
        DepATM depATM9000 = depATMCreator9000();
        Collection<DepATM> depATMS = new ArrayList<>();
        depATMS.add(depATM7000);
        depATMS.add(depATM4000);
        depATMS.add(depATM9000);
        department = new DepartmentImpl(depATMS);
    }

    private DepATM depATMCreator7000() throws CassetteException, NoSuchFieldException, IllegalAccessException {
        Collection<Cassette> cassettes = new ArrayList<>();
        Cassette cassetteOneHundred = new CassetteImpl(Banknote.ONE_HUNDRED, MAX_ONE_HUNDRED);
        cassetteOneHundred.putBanknotes(MAX_ONE_HUNDRED);
        cassettes.add(cassetteOneHundred);
        Cassette cassetteOneThousand = new CassetteImpl(Banknote.ONE_THOUSAND, MAX_ONE_THOUSAND);
        cassetteOneThousand.putBanknotes(1);
        cassettes.add(cassetteOneThousand);
        return new DepATMImpl(new ATMImpl(cassettes));
    }

    private DepATM depATMCreator4000() throws CassetteException, NoSuchFieldException, IllegalAccessException {
        Collection<Cassette> cassettes = new ArrayList<>();
        Cassette cassetteFifty = new CassetteImpl(Banknote.FIFTY, MAX_FIFTY);
        cassetteFifty.putBanknotes(MAX_FIFTY);
        cassettes.add(cassetteFifty);
        return new DepATMImpl(new ATMImpl(cassettes));
    }

    private DepATM depATMCreator9000() throws CassetteException, NoSuchFieldException, IllegalAccessException {
        Collection<Cassette> cassettes = new ArrayList<>();
        Cassette cassetteTwoHundred = new CassetteImpl(Banknote.TWO_HUNDRED, MAX_TWO_HUNDRED);
        cassetteTwoHundred.putBanknotes(20);
        cassettes.add(cassetteTwoHundred);
        Cassette cassetteFiveHundred = new CassetteImpl(Banknote.FIVE_HUNDRED, MAX_FIVE_HUNDRED);
        cassetteFiveHundred.putBanknotes(10);
        cassettes.add(cassetteFiveHundred);
        return new DepATMImpl(new ATMImpl(cassettes));
    }

    @Test
    void addATM() throws NoSuchFieldException, IllegalAccessException, CassetteException {
        assertEquals(20000, department.balance());
        department.addATM(depATMCreator7000());
        assertEquals(20000 + 7000, department.balance());
    }

    @Test
    void removeATM() throws NoSuchFieldException, IllegalAccessException, CassetteException {
        assertEquals(20000, department.balance());
        DepATM depATM = depATMCreator7000();
        department.addATM(depATM);
        assertEquals(20000 + 7000, department.balance());
        department.removeATM(depATM);
        assertEquals(20000, department.balance());
    }

    @Test
    void balance() {
        assertEquals(20000, department.balance());
    }

    @Test
    void restore() throws NoSuchFieldException, IllegalAccessException, CassetteException, ATMException, DepartmentException {
        //add, remove ATMs, without change money in any ATM
        assertEquals(20000, department.balance());
        DepATM depATM7000 = depATMCreator7000();
        department.addATM(depATM7000);
        assertEquals(20000 + 7000, department.balance());
        department.removeATM(depATM7000);
        assertEquals(20000, department.balance());
        DepATM depATM9000 = depATMCreator9000();
        department.addATM(depATM9000);
        assertEquals(29000, department.balance());

        //put/get money in any ATM
        depATM7000.getMoney(1500);
        assertEquals(29000 - 1500, department.balance());
        depATM9000.getMoney(900);
        assertEquals(29000 - 1500 - 900, department.balance());

        //Restore department ATMs and check balance
        department.restore();
        assertEquals(29000, department.balance());
    }
}