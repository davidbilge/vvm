package de.davidbilge.kata.vvm.interfaces;

import de.davidbilge.kata.vvm.business.CoinStorage;
import de.davidbilge.kata.vvm.business.CoinValue;
import de.davidbilge.kata.vvm.business.ItemIdentifier;
import de.davidbilge.kata.vvm.business.ItemStorage;
import de.davidbilge.kata.vvm.business.Prices;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperatorInterface {
    private final ItemStorage itemStorage;
    private final CoinStorage coinStorage;
    private final Prices prices;

    public void addItems(ItemIdentifier item, int times) {
        itemStorage.addItem(item, times);
    }

    public int countItems(ItemIdentifier item) {
        return itemStorage.countItems(item);
    }

    public void addCoins(CoinValue value, int times) {
        for (int i = 0; i < times; i++) {
            coinStorage.addCoins(List.of(value));
        }
    }

    public int countCoins(CoinValue value) {
        return coinStorage.countCoins(value);
    }

    public void setPriceFor(ItemIdentifier item, BigDecimal price) {
        prices.setPriceFor(item, price);
    }
}
