package de.davidbilge.kata.vvm.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Coins {

    public static final Comparator<CoinValue> COIN_VALUE_COMPARATOR = Comparator.comparing(CoinValue::getValueInCents);

    public static BigDecimal calculateCoinValue(Collection<CoinValue> coins) {
        int sum = coins.stream().mapToInt(CoinValue::getValueInCents).sum();
        return centsToEur(sum);
    }

    public static Collection<CoinValue> selectCoins(Collection<CoinValue> from, BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Value must not be negative");
        }

        Collection<CoinValue> result = new ArrayList<>();
        if (value.compareTo(BigDecimal.ZERO) == 0) {
            return result;
        }

        List<CoinValue> coinValues = from.stream().distinct().sorted(COIN_VALUE_COMPARATOR.reversed()).toList();
        for (CoinValue coinValue : coinValues) {
            BigDecimal coinValueInEur = centsToEur(coinValue.getValueInCents());
            if (coinValueInEur.compareTo(value) <= 0) {
                List<CoinValue> newRemainingCoins = new ArrayList<>(from);
                newRemainingCoins.remove(coinValue);
                BigDecimal newRemainingValue = value.subtract(coinValueInEur);

                try {
                    result.addAll(selectCoins(newRemainingCoins, newRemainingValue));
                    result.add(coinValue);
                    return result;
                } catch (InsufficientReturnCoinsException e) {
                    // Try recursing again with next lowest coin value
                }
            }
        }

        throw new InsufficientReturnCoinsException();
    }

    private static BigDecimal centsToEur(int cents) {
        return BigDecimal.valueOf(cents).divide(new BigDecimal(100), 2, RoundingMode.HALF_EVEN);
    }

    public static class InsufficientReturnCoinsException extends RuntimeException {
    }

}
