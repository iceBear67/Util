package cc.sfclub.util.bukkit.inv.template;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public class Layout {
    private static final int[][] locations = new int[10][9];
    private static boolean populated = false;

    static {
        if (!populated) {
            populated = !populated;
            int i = 0;
            for (int a = 0; a < 6; a++) {
                for (int b = 0; b < 9; b++) {
                    locations[b][a] = i;
                    i++;
                }
            }
        }
    }

    @Getter
    private final int height;
    private final Map<Integer, String> elements = new LinkedHashMap<>();
    private final Map<String, ItemStack> elementsToItem = new LinkedHashMap<>();

    public static int getIndexFromXY(int x, int y) {
        return locations[x][y];
    }

    public Layout put(String elementName, int x, int y) {
        String location = (y - 1) + String.valueOf(x - 1); //3 3: 22
        elements.put(Integer.parseInt(location), elementName);
        return this;
    }

    public Layout put(String elementName, ItemStack item) {
        elementsToItem.put(elementName, item);
        return this;
    }

    public ItemStack fromElement(String element) {
        return elementsToItem.get(element);
    }

    public String fromIndex(int i) {
        return elements.get(i);
    }

    public void applyTo(Inventory inventory) {
        inventory.clear();
        elements.forEach((i, e) -> inventory.setItem(i, fromElement(e)));
    }
}
