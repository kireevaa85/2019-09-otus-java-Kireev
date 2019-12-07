package ru.otus.hw06Bankomat.atm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw06Bankomat.Banknote;
import ru.otus.hw06Bankomat.cassette.Cassette;
import ru.otus.hw06Bankomat.cassette.CassetteException;
import ru.otus.hw06Bankomat.cassette.CassetteImpl;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ATMImplTest {

    private static final int MAX_FIFTY = 80;
    private static final int MAX_ONE_HUNDRED = 60;
    private static final int MAX_TWO_HUNDRED = 40;
    private static final int MAX_FIVE_HUNDRED = 20;
    private static final int MAX_ONE_THOUSAND = 10;
    private ATMImpl atm;

    @BeforeEach
    void setUp() {
        Collection<Cassette> cassettes = new ArrayList<>();
        cassettes.add(new CassetteImpl(Banknote.FIFTY, MAX_FIFTY));
        cassettes.add(new CassetteImpl(Banknote.ONE_HUNDRED, MAX_ONE_HUNDRED));
        cassettes.add(new CassetteImpl(Banknote.TWO_HUNDRED, MAX_TWO_HUNDRED));
        cassettes.add(new CassetteImpl(Banknote.FIVE_HUNDRED, MAX_FIVE_HUNDRED));
        cassettes.add(new CassetteImpl(Banknote.ONE_THOUSAND, MAX_ONE_THOUSAND));
        atm = new ATMImpl(cassettes);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void putBanknotes() throws ATMException {
        List<Banknote> banknotes = new ArrayList<>();
        addBanknotesToList(banknotes, Banknote.FIFTY, MAX_FIFTY);
        addBanknotesToList(banknotes, Banknote.ONE_HUNDRED, MAX_ONE_HUNDRED);
        addBanknotesToList(banknotes, Banknote.TWO_HUNDRED, MAX_TWO_HUNDRED);
        addBanknotesToList(banknotes, Banknote.FIVE_HUNDRED, MAX_FIVE_HUNDRED);
        addBanknotesToList(banknotes, Banknote.ONE_THOUSAND, MAX_ONE_THOUSAND);
        atm.putBanknotes(banknotes);
        final int sumExpected = Banknote.FIFTY.getNominal() * MAX_FIFTY
                + Banknote.ONE_HUNDRED.getNominal() * MAX_ONE_HUNDRED
                + Banknote.TWO_HUNDRED.getNominal() * MAX_TWO_HUNDRED
                + Banknote.FIVE_HUNDRED.getNominal() * MAX_FIVE_HUNDRED
                + Banknote.ONE_THOUSAND.getNominal() * MAX_ONE_THOUSAND;
        assertEquals(sumExpected, atm.balance());
    }

    @Test
    void putBanknotesATMException() {
        List<Banknote> banknotes = new ArrayList<>();
        addBanknotesToList(banknotes, Banknote.ONE_HUNDRED, MAX_ONE_HUNDRED + 1);
        assertThrows(ATMException.class, () -> atm.putBanknotes(banknotes));
    }

    @Test
    void putBanknotesATMException2() {
        List<Banknote> banknotes = new ArrayList<>();
        addBanknotesToList(banknotes, Banknote.TEN, 1);
        assertThrows(ATMException.class, () -> atm.putBanknotes(banknotes));
    }

    @Test
    void getMoney() throws ATMException {
        List<Banknote> banknotes = new ArrayList<>();
        addBanknotesToList(banknotes, Banknote.FIFTY, MAX_FIFTY);
        addBanknotesToList(banknotes, Banknote.ONE_HUNDRED, MAX_ONE_HUNDRED);
        addBanknotesToList(banknotes, Banknote.TWO_HUNDRED, MAX_TWO_HUNDRED);
        addBanknotesToList(banknotes, Banknote.FIVE_HUNDRED, MAX_FIVE_HUNDRED);
        addBanknotesToList(banknotes, Banknote.ONE_THOUSAND, MAX_ONE_THOUSAND);
        atm.putBanknotes(banknotes);

        final List<Banknote> money = atm.getMoney(1950);
        final Map<Banknote, Long> groupingBanknotes = money.stream()
                .collect(Collectors.groupingBy(banknote -> banknote, Collectors.counting()));
        assertEquals(Long.valueOf(1), groupingBanknotes.get(Banknote.ONE_THOUSAND));
        assertEquals(Long.valueOf(1), groupingBanknotes.get(Banknote.FIVE_HUNDRED));
        assertEquals(Long.valueOf(2), groupingBanknotes.get(Banknote.TWO_HUNDRED));
        assertNull(groupingBanknotes.get(Banknote.ONE_HUNDRED));
        assertEquals(Long.valueOf(1), groupingBanknotes.get(Banknote.FIFTY));
        assertNull(groupingBanknotes.get(Banknote.TEN));
    }

    @Test
    void getMoneyATMException() throws ATMException {
        List<Banknote> banknotes = new ArrayList<>();
        addBanknotesToList(banknotes, Banknote.ONE_HUNDRED, MAX_ONE_HUNDRED);
        addBanknotesToList(banknotes, Banknote.ONE_THOUSAND, MAX_ONE_THOUSAND);
        atm.putBanknotes(banknotes);
        assertThrows(ATMException.class, () -> atm.getMoney(1950));
    }

    @Test
    void balance() throws ATMException {
        assertEquals(0, atm.balance());

        List<Banknote> banknotes = new ArrayList<>();
        addBanknotesToList(banknotes, Banknote.ONE_HUNDRED, MAX_ONE_HUNDRED);
        addBanknotesToList(banknotes, Banknote.ONE_THOUSAND, 1);
        atm.putBanknotes(banknotes);
        assertEquals(Banknote.ONE_HUNDRED.getNominal() * MAX_ONE_HUNDRED + Banknote.ONE_THOUSAND.getNominal(), atm.balance());
    }

    @Test
    void uuid() {
        assertNotNull(atm.uuid());
    }

    @Test
    void restore() throws ATMException, CassetteException {
        Collection<Cassette> cassettes = new ArrayList<>();
        Cassette cassetteOneHundred = new CassetteImpl(Banknote.ONE_HUNDRED, MAX_ONE_HUNDRED);
        cassetteOneHundred.putBanknotes(MAX_ONE_HUNDRED);
        cassettes.add(cassetteOneHundred);
        Cassette cassetteOneThousand = new CassetteImpl(Banknote.ONE_THOUSAND, MAX_ONE_THOUSAND);
        cassetteOneThousand.putBanknotes(1);
        cassettes.add(cassetteOneThousand);
        atm = new ATMImpl(cassettes);

        assertEquals(Banknote.ONE_HUNDRED.getNominal() * MAX_ONE_HUNDRED + Banknote.ONE_THOUSAND.getNominal(), atm.balance());
        atm.getMoney(1100);
        assertEquals(Banknote.ONE_HUNDRED.getNominal() * (MAX_ONE_HUNDRED - 1), atm.balance());
        atm.restore();
        assertEquals(Banknote.ONE_HUNDRED.getNominal() * MAX_ONE_HUNDRED + Banknote.ONE_THOUSAND.getNominal(), atm.balance());
    }

    private static void addBanknotesToList(List<Banknote> banknotes, Banknote banknote, int count) {
        for (int i = 0; i < count; i++) {
            banknotes.add(banknote);
        }
    }
}
