package ru.otus.hw07DepartmentBankomats.atm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw06Bankomat.Banknote;
import ru.otus.hw06Bankomat.atm.ATMException;
import ru.otus.hw06Bankomat.atm.ATMImpl;
import ru.otus.hw06Bankomat.cassette.Cassette;
import ru.otus.hw06Bankomat.cassette.CassetteException;
import ru.otus.hw06Bankomat.cassette.CassetteImpl;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class DepATMTest {
    private static final int MAX_ONE_HUNDRED = 60;
    private static final int MAX_ONE_THOUSAND = 10;
    private DepATMImpl depATM;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException, CassetteException {
        Collection<Cassette> cassettes = new ArrayList<>();

        Cassette cassetteOneHundred = new CassetteImpl(Banknote.ONE_HUNDRED, MAX_ONE_HUNDRED);
        cassetteOneHundred.putBanknotes(MAX_ONE_HUNDRED);
        cassettes.add(cassetteOneHundred);

        Cassette cassetteOneThousand = new CassetteImpl(Banknote.ONE_THOUSAND, MAX_ONE_THOUSAND);
        cassetteOneThousand.putBanknotes(1);
        cassettes.add(cassetteOneThousand);

        depATM = new DepATMImpl(new ATMImpl(cassettes));
    }

    @Test
    void uuid() {
        assertNotNull(depATM.uuid());
    }

    @Test
    void restore() throws ATMException {
        assertEquals(Banknote.ONE_HUNDRED.getNominal() * MAX_ONE_HUNDRED + Banknote.ONE_THOUSAND.getNominal(), depATM.balance());
        depATM.getMoney(1100);
        assertEquals(Banknote.ONE_HUNDRED.getNominal() * (MAX_ONE_HUNDRED - 1), depATM.balance());
        depATM.restore();
        assertEquals(Banknote.ONE_HUNDRED.getNominal() * MAX_ONE_HUNDRED + Banknote.ONE_THOUSAND.getNominal(), depATM.balance());
    }
}