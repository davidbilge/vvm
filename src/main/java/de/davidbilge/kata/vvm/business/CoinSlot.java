package de.davidbilge.kata.vvm.business;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CoinSlot {
    private final List<CoinValue> insertedCoins = new ArrayList<>();
    private final CoinReturnDrawer coinReturnDrawer;

    public void insertCoin(CoinValue coinValue) {
        insertedCoins.add(coinValue);
    }

    public void returnCoins() {
        log.info("Returning coins to customer");
        coinReturnDrawer.addCoins(insertedCoins);
        insertedCoins.clear();
    }

    public ImmutableList<CoinValue> commitCoins() {
        ImmutableList<CoinValue> committedCoins = ImmutableList.copyOf(insertedCoins);
        insertedCoins.clear();
        return committedCoins;
    }

    public ImmutableList<CoinValue> getCoins() {
        return ImmutableList.copyOf(insertedCoins);
    }
}
