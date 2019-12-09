package ru.otus.hw06Bankomat.atm;

import ru.otus.hw06Bankomat.Banknote;
import ru.otus.hw06Bankomat.cassette.Cassette;
import ru.otus.hw06Bankomat.cassette.CassetteException;
import ru.otus.hw06Bankomat.cassette.CassetteImpl;

import java.util.*;
import java.util.stream.Collectors;

public class ATMImpl implements ATM {
    private final String uuid;
    private TreeMap<Banknote, Cassette> cassettes;
    private Memento memento;

    public ATMImpl(Collection<Cassette> cassettesList) {
        if (cassettesList == null || cassettesList.isEmpty()) {
            throw new IllegalArgumentException("cassettes can't be null or empty");
        }
        this.uuid = UUID.randomUUID().toString();
        final Set<Banknote> banknotes = new HashSet<>();
        cassettesList.forEach(cassette -> {
            if (!banknotes.add(cassette.nominal())) {
                throw new IllegalArgumentException("cassettes can't have duplicates by banknote");
            }
        });
        this.cassettes = new TreeMap<>((o1, o2) -> o2.getNominal() - o1.getNominal());
        cassettesList.forEach(cassette -> this.cassettes.put(cassette.nominal(), cassette));
        this.memento = new Memento();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATMImpl depATM = (ATMImpl) o;
        return uuid.equals(depATM.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public void putBanknotes(List<Banknote> banknotes) throws ATMException {
        final Map<Banknote, Long> groupingBanknotes = banknotes.stream()
                .collect(Collectors.groupingBy(banknote -> banknote, Collectors.counting()));

        if (!cassettes.keySet().containsAll(groupingBanknotes.keySet())) {
            throw new ATMException("Please put suitable banknotes (look information on ATM header)");
        }
        if (groupingBanknotes.keySet().stream()
                .anyMatch(banknote -> cassettes.get(banknote).noPlace(Math.toIntExact(groupingBanknotes.get(banknote))))) {
            throw new ATMException("Sorry, the ATM need technical support, please try later");
        }

        groupingBanknotes.keySet().forEach(banknote -> {
            try {
                cassettes.get(banknote).putBanknotes(Math.toIntExact(groupingBanknotes.get(banknote)));
            } catch (CassetteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<Banknote> getMoney(int sum) throws ATMException {
        if (sum <= 0) {
            throw new IllegalArgumentException("The sum must not be zero or negative");
        }
        List<GetMoneyOperation> futureOperations = new ArrayList<>();
        int otherSum = sum;
        for (Banknote banknote : cassettes.keySet()) {
            int count = otherSum / cassettes.get(banknote).nominal().getNominal();
            while (count != 0 && cassettes.get(banknote).count() < count) {
                count--;
            }
            if (count > 0) {
                futureOperations.add(new GetMoneyOperation(banknote, count));
                otherSum -= count * banknote.getNominal();
            }
        }

        if (otherSum != 0) {
            throw new ATMException("Sorry, i cant'n return this amount: " + sum);
        }

        List<Banknote> result = new ArrayList<>();
        futureOperations.forEach(operation -> {
                    try {
                        result.addAll(cassettes.get(operation.banknote).getBanknotes(operation.count));
                    } catch (CassetteException e) {
                        throw new RuntimeException(e);
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

    @Override
    public String uuid() {
        return uuid;
    }

    @Override
    public void restore() {
        cassettes = cloneCassettes(memento.getCassettesStamp());
    }

    private class Memento {
        private final TreeMap<Banknote, Cassette> cassettesStamp;

        private Memento() {
            this.cassettesStamp = cloneCassettes(cassettes);
        }

        private TreeMap<Banknote, Cassette> getCassettesStamp() {
            return cassettesStamp;
        }
    }

    private TreeMap<Banknote, Cassette> cloneCassettes(TreeMap<Banknote, Cassette> cassettes) {
        TreeMap<Banknote, Cassette> result = new TreeMap<>((o1, o2) -> o2.getNominal() - o1.getNominal());
        cassettes.values().forEach(cassette -> result
                .put(cassette.nominal(), new CassetteImpl(cassette.nominal(), cassette.maxCount(), cassette.count())));
        return result;
    }

}
