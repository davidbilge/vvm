package de.davidbilge.kata.vvm.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoinStorage {
    private final CoinReturnDrawer coinReturnDrawer;

    private final List<CoinValue> storedCoins = new ArrayList<>();

    public void addCoins(Collection<CoinValue> coins) {
        this.storedCoins.addAll(coins);
    }

    public void outputCoins(BigDecimal value) {
        Collection<CoinValue> outputCoins = Coins.selectCoins(storedCoins, value);
        for (CoinValue outputCoin : outputCoins) {
            storedCoins.remove(outputCoin);
        }
        coinReturnDrawer.addCoins(outputCoins);
    }

    public int countCoins(CoinValue value) {
        return (int) storedCoins.stream().filter(storedCoin -> storedCoin == value).count();
    }
}
