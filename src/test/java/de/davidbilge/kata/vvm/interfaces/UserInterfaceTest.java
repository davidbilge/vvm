package de.davidbilge.kata.vvm.interfaces;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import de.davidbilge.kata.vvm.business.CoinReturnDrawer;
import de.davidbilge.kata.vvm.business.CoinValue;
import de.davidbilge.kata.vvm.business.ItemIdentifier;
import de.davidbilge.kata.vvm.business.ItemOutputDrawer;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserInterfaceTest {
    public static final ItemIdentifier COCA_COLA = new ItemIdentifier("Coca Cola");
    public static final ItemIdentifier TWIX = new ItemIdentifier("Twix");
    public static final ItemIdentifier APFELSCHORLE = new ItemIdentifier("Apfelschorle");
    @Autowired
    private OperatorInterface operatorInterface;

    @Autowired
    private UserInterface userInterface;

    @Autowired
    private CoinReturnDrawer coinReturnDrawer;

    @Autowired
    private ItemOutputDrawer itemOutputDrawer;

    @Test
    void shouldProcessCorrectOrder() {
        // given
        enoughChange();
        enoughItems();
        configuredPrices();

        // when
        userInterface.insertCoin(CoinValue.FIFTY_CENT);
        userInterface.insertCoin(CoinValue.ONE_EURO);
        userInterface.insertCoin(CoinValue.ONE_EURO);
        userInterface.insertCoin(CoinValue.TWENTY_CENT);
        userInterface.requestItem(COCA_COLA);

        // then
        ImmutableList<CoinValue> withdrawnCoins = coinReturnDrawer.withdraw();
        assertThat(withdrawnCoins).containsExactlyInAnyOrder(CoinValue.TWENTY_CENT, CoinValue.TWENTY_CENT);

        ImmutableList<ItemIdentifier> withdrawnItems = itemOutputDrawer.withdraw();
        assertThat(withdrawnItems).containsExactlyInAnyOrder(COCA_COLA);

        assertThat(operatorInterface.countItems(COCA_COLA)).isEqualTo(9);
        assertThat(operatorInterface.countCoins(CoinValue.TWO_EURO)).isEqualTo(10);
        assertThat(operatorInterface.countCoins(CoinValue.ONE_EURO)).isEqualTo(12);
        assertThat(operatorInterface.countCoins(CoinValue.FIFTY_CENT)).isEqualTo(11);
        assertThat(operatorInterface.countCoins(CoinValue.TWENTY_CENT)).isEqualTo(9);
        assertThat(operatorInterface.countCoins(CoinValue.TEN_CENT)).isEqualTo(10);

    }

    private void configuredPrices() {
        operatorInterface.setPriceFor(COCA_COLA, new BigDecimal("2.30"));
        operatorInterface.setPriceFor(TWIX, new BigDecimal("2.80"));
        operatorInterface.setPriceFor(APFELSCHORLE, new BigDecimal("2.10"));
    }

    private void enoughItems() {
        operatorInterface.addItems(COCA_COLA, 10);
        operatorInterface.addItems(TWIX, 15);
        operatorInterface.addItems(APFELSCHORLE, 2);
    }

    private void enoughChange() {
        for (CoinValue value : CoinValue.values()) {
            operatorInterface.addCoins(value, 10);
        }
    }

}
