package de.davidbilge.kata.vvm.interfaces;

import com.google.common.collect.ImmutableList;
import de.davidbilge.kata.vvm.business.CoinSlot;
import de.davidbilge.kata.vvm.business.CoinStorage;
import de.davidbilge.kata.vvm.business.CoinValue;
import de.davidbilge.kata.vvm.business.Coins;
import de.davidbilge.kata.vvm.business.Display;
import de.davidbilge.kata.vvm.business.ItemIdentifier;
import de.davidbilge.kata.vvm.business.ItemStorage;
import de.davidbilge.kata.vvm.business.Prices;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInterface {
    private final CoinSlot coinSlot;
    private final ItemStorage itemStorage;
    private final CoinStorage coinStorage;
    private final Prices prices;
    private final Display display;

    public void insertCoin(CoinValue coinValue) {
        coinSlot.insertCoin(coinValue);
    }

    public void requestItem(ItemIdentifier identifier) {
        // Check availability of item
        if (!itemStorage.containsItem(identifier)) {
            display.showMessage("Item " + identifier + " not available");
            return;
        }

        // Check coins in coin slot
        BigDecimal itemPrice = prices.getPriceFor(identifier);
        BigDecimal depositedMoney = Coins.calculateCoinValue(coinSlot.getCoins());
        if (depositedMoney.compareTo(itemPrice) < 0) {
            display.showMessage("Please insert additional " + itemPrice.subtract(depositedMoney) + "€");
            return;
        }

        // Commit coins
        List<CoinValue> committedCoins = coinSlot.commitCoins();
        coinStorage.addCoins(committedCoins);

        // Return change
        coinSlot.returnCoins();
        BigDecimal paidPrice = Coins.calculateCoinValue(committedCoins);
        coinStorage.outputCoins(paidPrice.subtract(itemPrice));

        // Output requested item
        itemStorage.outputItem(identifier);
    }

}
