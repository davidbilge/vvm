package de.davidbilge.kata.vvm.business;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CoinReturnDrawer {
    private final List<CoinValue> coins = new ArrayList<>();

    public void addCoins(Collection<CoinValue> coins) {
        this.coins.addAll(coins);
    }

    public ImmutableList<CoinValue> withdraw() {
        ImmutableList<CoinValue> result = ImmutableList.copyOf(coins);
        coins.clear();
        return result;
    }
}
