package de.davidbilge.kata.vvm.business;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ItemOutputDrawer {
    private final List<ItemIdentifier> items = new ArrayList<>();

    public void addItem(ItemIdentifier item) {
        this.items.add(item);
    }

    public ImmutableList<ItemIdentifier> withdraw() {
        ImmutableList<ItemIdentifier> result = ImmutableList.copyOf(items);
        items.clear();
        return result;
    }
}
