package ru.otus.hw07DepartmentBankomats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw06Bankomat.Banknote;
import ru.otus.hw06Bankomat.atm.ATM;
import ru.otus.hw06Bankomat.atm.ATMException;
import ru.otus.hw06Bankomat.atm.ATMImpl;
import ru.otus.hw06Bankomat.cassette.Cassette;
import ru.otus.hw06Bankomat.cassette.CassetteException;
import ru.otus.hw06Bankomat.cassette.CassetteImpl;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {
    private static final int MAX_FIFTY = 80;
    private static final int MAX_ONE_HUNDRED = 60;
    private static final int MAX_TWO_HUNDRED = 40;
    private static final int MAX_FIVE_HUNDRED = 20;
    private static final int MAX_ONE_THOUSAND = 10;
    private DepartmentImpl department;

    @BeforeEach
    void setUp() throws CassetteException {
        ATM depATM7000 = depATMCreator7000();
        ATM depATM4000 = depATMCreator4000();
        ATM depATM9000 = depATMCreator9000();
        Collection<ATM> depATMS = new ArrayList<>();
        depATMS.add(depATM7000);
        depATMS.add(depATM4000);
        depATMS.add(depATM9000);
        department = new DepartmentImpl(depATMS);
    }

    private ATM depATMCreator7000() throws CassetteException {
        Collection<Cassette> cassettes = new ArrayList<>();
        Cassette cassetteOneHundred = new CassetteImpl(Banknote.ONE_HUNDRED, MAX_ONE_HUNDRED);
        cassetteOneHundred.putBanknotes(MAX_ONE_HUNDRED);
        cassettes.add(cassetteOneHundred);
        Cassette cassetteOneThousand = new CassetteImpl(Banknote.ONE_THOUSAND, MAX_ONE_THOUSAND);
        cassetteOneThousand.putBanknotes(1);
        cassettes.add(cassetteOneThousand);
        return new ATMImpl(cassettes);
    }

    private ATM depATMCreator4000() throws CassetteException {
        Collection<Cassette> cassettes = new ArrayList<>();
        Cassette cassetteFifty = new CassetteImpl(Banknote.FIFTY, MAX_FIFTY);
        cassetteFifty.putBanknotes(MAX_FIFTY);
        cassettes.add(cassetteFifty);
        return new ATMImpl(cassettes);
    }

    private ATM depATMCreator9000() throws CassetteException {
        Collection<Cassette> cassettes = new ArrayList<>();
        Cassette cassetteTwoHundred = new CassetteImpl(Banknote.TWO_HUNDRED, MAX_TWO_HUNDRED);
        cassetteTwoHundred.putBanknotes(20);
        cassettes.add(cassetteTwoHundred);
        Cassette cassetteFiveHundred = new CassetteImpl(Banknote.FIVE_HUNDRED, MAX_FIVE_HUNDRED);
        cassetteFiveHundred.putBanknotes(10);
        cassettes.add(cassetteFiveHundred);
        return new ATMImpl(cassettes);
    }

    @Test
    void addATM() throws CassetteException {
        assertEquals(20000, department.balance());
        department.addATM(depATMCreator7000());
        assertEquals(20000 + 7000, department.balance());
    }

    @Test
    void removeATM() throws CassetteException {
        assertEquals(20000, department.balance());
        ATM depATM = depATMCreator7000();
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
    void restore() throws CassetteException, ATMException {
        //add, remove ATMs, without change money in any ATM
        assertEquals(20000, department.balance());
        ATM depATM7000 = depATMCreator7000();
        department.addATM(depATM7000);
        assertEquals(20000 + 7000, department.balance());
        department.removeATM(depATM7000);
        assertEquals(20000, department.balance());
        ATM depATM9000 = depATMCreator9000();
        department.addATM(depATM9000);
        assertEquals(29000, department.balance());

        //put/get money in any ATM
        depATM9000.getMoney(900);
        assertEquals(29000 - 900, department.balance());

        //Restore department ATMs and check balance
        department.restore();
        assertEquals(29000, department.balance());
    }
}