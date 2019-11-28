package ru.otus.hw06Bankomat.cassette;

import ru.otus.hw06Bankomat.Banknote;

import java.util.List;

public interface Cassette {

    /**
     * @return Return nominal({@link Banknote}) of the current cassette
     * @see Banknote
     */
    Banknote nominal();

    /**
     * @param count Count of banknotes for put in the current cassette
     * @throws MaxSizeCassetteException If the current cassette haven't size for new count banknotes
     */
    void putBanknotes(int count) throws MaxSizeCassetteException;

    /**
     * @param count Count of banknotes for return
     * @return List of banknotes
     * @throws InsufficientAmountCassetteException If the current cassette have not count banknotes
     */
    List<Banknote> getBanknotes(int count) throws InsufficientAmountCassetteException;

    /**
     * @return Return the count of banknotes on the current Cassette
     */
    int count();

    /**
     * Check whether it is possible to transfer the transmitted number of notes
     * @param addCount Number of bills transferred
     * @return {@code true} if no the place for bills transferred
     */
    boolean noPlace(int addCount);

}
