package de.davidbilge.kata.vvm.business;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class Prices {
    private final Map<ItemIdentifier, BigDecimal> prices = new HashMap<>();

    public void setPriceFor(ItemIdentifier item, BigDecimal price) {
        prices.put(item, price);
    }

    public BigDecimal getPriceFor(ItemIdentifier item) {
        return Optional.ofNullable(prices.get(item))
            .orElseThrow(() -> new NoSuchElementException("No price found for item " + item));
    }
}
