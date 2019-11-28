package ru.otus.hw06Bankomat.cassette;

import ru.otus.hw06Bankomat.Banknote;

import java.util.ArrayList;
import java.util.List;

public class CassetteImpl implements Cassette {
    private final Banknote nominal;
    private final int maxCount;
    private int count;

    public CassetteImpl(Banknote nominal, int maxCount) {
        this(nominal, maxCount, 0);
    }

    public CassetteImpl(Banknote nominal, int maxCount, int count) {
        if (nominal == null) {
            throw new IllegalArgumentException("nominal can't be null");
        }
        if (maxCount <= 0) {
            throw new IllegalArgumentException("maxCount can't be negative or zero");
        }
        this.nominal = nominal;
        this.maxCount = maxCount;
        this.count = count;
    }

    @Override
    public Banknote nominal() {
        return nominal;
    }

    @Override
    public void putBanknotes(int count) throws MaxSizeCassetteException {
        if (noPlace(count)) {
            throw new MaxSizeCassetteException("Can't put all banknotes, the cassette for nominal " + nominal + " is fully");
        }
    }

    @Override
    public List<Banknote> getBanknotes(int count) throws InsufficientAmountCassetteException {
        if (count > this.count) {
            throw new InsufficientAmountCassetteException("The cassette with nominal " + nominal + " have not " + count + " banknotes");
        }
        List<Banknote> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(nominal);
        }
        this.count -= count;
        return result;
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public boolean noPlace(int addCount) {
        return addCount + count > maxCount;
    }
}
