package ru.otus.hw06Bankomat.cassette;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw06Bankomat.Banknote;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CassetteImplTest {

    private final static Banknote BANKNOTE = Banknote.ONE_HUNDRED;
    private static final int MAX_COUNT = 10;
    private Cassette cassette;

    @BeforeEach
    void setUp() {
        cassette = new CassetteImpl(BANKNOTE, MAX_COUNT);
    }

    @Test
    void nominal() {
        assertEquals(BANKNOTE, cassette.nominal());
    }

    @Test
    void putBanknotes() throws CassetteException {
        cassette.putBanknotes(MAX_COUNT);
        assertEquals(MAX_COUNT, cassette.count());
    }

    @Test
    void putBanknotesCassetteException() {
        assertThrows(CassetteException.class, () -> cassette.putBanknotes(MAX_COUNT + 1));
    }

    @Test
    void getBanknotes() throws CassetteException {
        cassette.putBanknotes(MAX_COUNT);
        List<Banknote> banknotes = cassette.getBanknotes(MAX_COUNT);
        assertEquals(MAX_COUNT, banknotes.size());
        assertTrue(banknotes.stream().allMatch(b -> b.equals(BANKNOTE)));
    }

    @Test
    void getBanknotesCassetteException() {
        assertThrows(CassetteException.class, () -> {
            cassette.putBanknotes(MAX_COUNT);
            cassette.getBanknotes(MAX_COUNT + 1);
        });
    }

    @Test
    void count() throws CassetteException {
        assertEquals(0, cassette.count());
        cassette.putBanknotes(MAX_COUNT);
        assertEquals(MAX_COUNT, cassette.count());
        cassette.getBanknotes(1);
        assertEquals(MAX_COUNT - 1, cassette.count());
    }

    @Test
    void noPlace() {
        assertFalse(cassette.noPlace(MAX_COUNT - 1));
        assertFalse(cassette.noPlace(MAX_COUNT));
        assertTrue(cassette.noPlace(MAX_COUNT + 1));
    }
}