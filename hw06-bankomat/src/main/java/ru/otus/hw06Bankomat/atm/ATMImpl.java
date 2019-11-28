package ru.otus.hw06Bankomat.atm;

import ru.otus.hw06Bankomat.Banknote;
import ru.otus.hw06Bankomat.cassette.Cassette;
import ru.otus.hw06Bankomat.cassette.InsufficientAmountCassetteException;
import ru.otus.hw06Bankomat.cassette.MaxSizeCassetteException;

import java.util.*;
import java.util.stream.Collectors;

public class ATMImpl implements ATM {
    private final TreeMap<Banknote, Cassette> cassettes;

    public ATMImpl(Set<Cassette> cassettesSet) {
        if (cassettesSet == null || cassettesSet.isEmpty()) {
            throw new IllegalArgumentException("cassettes can't be null or empty");
        }
        this.cassettes = new TreeMap<>((o1, o2) -> o2.getNominal() - o1.getNominal());
        cassettesSet.forEach(cassette -> this.cassettes.put(cassette.nominal(), cassette));
    }

    @Override
    public void putBanknotes(List<Banknote> banknotes) throws NominalATMException, MaxSizeATMException {
        final Map<Banknote, Long> groupingBanknotes = banknotes.stream()
                .collect(Collectors.groupingBy(banknote -> banknote, Collectors.counting()));

        if (!cassettes.keySet().containsAll(groupingBanknotes.keySet())) {
            throw new NominalATMException("Please put suitable banknotes (look information on bankomat header)");
        }
        if (groupingBanknotes.keySet().stream()
                .anyMatch(banknote -> cassettes.get(banknote).noPlace(Math.toIntExact(groupingBanknotes.get(banknote))))) {
            throw new MaxSizeATMException("Sorry, the ATM need technical support, please try later");
        }

        groupingBanknotes.keySet().forEach(banknote -> {
            try {
                cassettes.get(banknote).putBanknotes(Math.toIntExact(groupingBanknotes.get(banknote)));
            } catch (MaxSizeCassetteException e) {
                throw new RuntimeException();
            }
        });
    }

    @Override
    public List<Banknote> getMoney(int summ) throws MoneySelectionATMException {
        if (summ <= 0) {
            throw new IllegalArgumentException("The summ must not be zero or negative");
        }
        List<GetMoneyOperation> futureOperations = new ArrayList<>();
        int otherSumm = summ;
        for (Banknote banknote : cassettes.keySet()) {
            int count = otherSumm / cassettes.get(banknote).nominal().getNominal();
            while (count != 0 && cassettes.get(banknote).noPlace(count)) {
                count--;
            }
            if (count > 0) {
                futureOperations.add(new GetMoneyOperation(banknote, count));
                otherSumm -= count * banknote.getNominal();
            }
        }

        if (otherSumm != 0) {
            throw new MoneySelectionATMException("Sorry, i cant'n return this amount: " + summ);
        }

        List<Banknote> result = new ArrayList<>();
        futureOperations.forEach(operation -> {
                    try {
                        result.addAll(cassettes.get(operation.banknote).getBanknotes(operation.count));
                    } catch (InsufficientAmountCassetteException e) {
                        throw new RuntimeException();
                    }
                });
        return result;
    }

    private static class GetMoneyOperation {
        private Banknote banknote;
        private int count;

        private GetMoneyOperation(Banknote banknote, int count) {
            this.banknote = banknote;
            this.count = count;
        }
    }

    @Override
    public int balance() {
        return cassettes.values().stream()
                .mapToInt(cassette -> cassette.count() * cassette.nominal().getNominal())
                .sum();
    }
}
