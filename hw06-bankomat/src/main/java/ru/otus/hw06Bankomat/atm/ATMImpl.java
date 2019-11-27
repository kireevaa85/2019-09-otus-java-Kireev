package ru.otus.hw06Bankomat.atm;

import ru.otus.hw06Bankomat.Banknote;
import ru.otus.hw06Bankomat.cassette.Cassette;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ATMImpl implements ATM {
    private final List<Cassette> cassettes;

    public ATMImpl(List<Cassette> cassettes) {
        if (cassettes == null || cassettes.isEmpty()) {
            throw new IllegalArgumentException("cassettes can't be null or empty");
        }
        this.cassettes = cassettes;
    }

    @Override
    public void putBanknotes(List<Banknote> banknotes) throws NominalATMException {
        if (!cassettes.stream()
                .map(Cassette::nominal)
                .collect(Collectors.toList())
                .containsAll(banknotes)) {
            throw new NominalATMException("putBanknotes error: Please put suitable banknotes (look information on bankomat header)");
        }
        //// TODO: 27.11.2019
    }

    @Override
    public List<Banknote> getMoney(int summ) throws Exception {
        // TODO: 27.11.2019
        return null;
    }

    @Override
    public int balance() {
        int summ = 0;
        for (Cassette cassette : cassettes) {
            summ += cassette.count() * cassette.nominal().getNominal();
        }
        return summ;
    }
}
