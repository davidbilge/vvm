package de.davidbilge.kata.vvm.business;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CoinValue {
    // Make sure that the coin values are sorted (descending)

    TWO_EURO(200),

    ONE_EURO(100),

    FIFTY_CENT(50),

    TWENTY_CENT(20),

    TEN_CENT(10);

    private final int valueInCents;
}
