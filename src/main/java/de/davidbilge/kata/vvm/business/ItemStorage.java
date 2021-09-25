package de.davidbilge.kata.vvm.business;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemStorage {
    private final ItemOutputDrawer itemOutputDrawer;

    private final List<ItemIdentifier> storedItems = new ArrayList<>();

    public void addItem(ItemIdentifier identifier, int times) {
        for (int i = 0; i < times; i++) {
            storedItems.add(identifier);
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean containsItem(ItemIdentifier identifier) {
        return storedItems.contains(identifier);
    }

    public void outputItem(ItemIdentifier itemIdentifier) {
        if (!containsItem(itemIdentifier)) {
            throw new IllegalArgumentException("Item '" + itemIdentifier + "' is not available");
        }

        itemOutputDrawer.addItem(itemIdentifier);
        storedItems.remove(itemIdentifier);
    }

    public int countItems(ItemIdentifier item) {
        return (int) storedItems.stream().filter(storedItem -> storedItem.equals(item)).count();
    }
}
