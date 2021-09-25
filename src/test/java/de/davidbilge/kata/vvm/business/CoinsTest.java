package de.davidbilge.kata.vvm.business;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.davidbilge.kata.vvm.business.Coins.InsufficientReturnCoinsException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CoinsTest {

    public static Stream<Arguments> provideChangeArguments() {
        return Stream.of(//
            Arguments.of(List.of(), BigDecimal.ZERO, List.of()),//
            Arguments.of(List.of(CoinValue.TWO_EURO, CoinValue.ONE_EURO), new BigDecimal("3"),
                List.of(CoinValue.TWO_EURO, CoinValue.ONE_EURO)),//
            Arguments.of(List.of(CoinValue.TWO_EURO, CoinValue.ONE_EURO), new BigDecimal("1"),
                List.of(CoinValue.ONE_EURO)),//
            Arguments.of(List.of(CoinValue.TWO_EURO, CoinValue.ONE_EURO, CoinValue.TWO_EURO), new BigDecimal("3"),
                List.of(CoinValue.TWO_EURO, CoinValue.ONE_EURO)),//
            Arguments.of(
                List.of(CoinValue.FIFTY_CENT, CoinValue.TWENTY_CENT, CoinValue.TWENTY_CENT, CoinValue.TWENTY_CENT),
                new BigDecimal("0.60"), List.of(CoinValue.TWENTY_CENT, CoinValue.TWENTY_CENT, CoinValue.TWENTY_CENT))//
        );
    }

    @ParameterizedTest
    @MethodSource("provideChangeArguments")
    void shouldReturnCorrectChange(List<CoinValue> givenCoinValues, BigDecimal givenAmount,
        Collection<CoinValue> expectedResult) {

        // when
        Collection<CoinValue> result = Coins.selectCoins(givenCoinValues, givenAmount);

        // then
        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedResult);
    }

    @Test
    void shouldThrowExceptionForMissingChange() {
        // given
        Collection<CoinValue> givenCoinValues = List.of(CoinValue.ONE_EURO);
        BigDecimal givenAmount = new BigDecimal("1.10");

        // when
        Executable when = () -> Coins.selectCoins(givenCoinValues, givenAmount);

        // then
        assertThrows(InsufficientReturnCoinsException.class, when);
    }


}
