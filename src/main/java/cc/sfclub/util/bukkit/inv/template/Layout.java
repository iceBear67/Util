package cc.sfclub.util.bukkit.inv.template;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 声明一个布局..
 */
@RequiredArgsConstructor
public class Layout {
    private static final int[][] locations = new int[10][9];

    static {
        int i = 0;
        for (int a = 0; a < 6; a++) {
            for (int b = 0; b < 9; b++) {
                locations[b][a] = i;
                i++;
            }
        }
    }

    @Getter
    private final int height;
    private final Map<Integer, String> elements = new LinkedHashMap<>();
    private final Map<String, ItemStack> elementsToItem = new LinkedHashMap<>();

    /**
     * 把X Y转换成Bukkit物品栏里的slot
     *
     * @param x
     * @param y
     * @return slot
     */
    public static int getIndexFromXY(int x, int y) {
        return locations[x][y];
    }

    /**
     * 放置元素..
     *
     * @param elementName
     * @param x
     * @param y
     * @return
     */
    public Layout put(String elementName, int x, int y) {
        elements.put(getIndexFromXY(x, y), elementName);
        return this;
    }

    /**
     * 设置所有匹配到正则表达式的元素为item
     *
     * @param regex
     * @param item
     * @return
     */
    public Layout setAll(String regex, ItemStack item) {
        elements.forEach((e, v) -> {
            if (v.matches(regex)) elementsToItem.put(v, item);
        });
        return this;
    }

    /**
     * 从Element获取到槽位上对应的物品
     *
     * @param element
     * @return
     */
    public ItemStack fromElement(String element) {
        return elementsToItem.get(element);
    }

    /**
     * 从slot id得到对应的元素
     *
     * @param i
     * @return
     */
    public String fromIndex(int i) {
        return elements.get(i);
    }

    /**
     * 应用到物品栏
     *
     * @param inventory
     */
    public void applyTo(Inventory inventory) {
        inventory.clear();
        elements.forEach((i, e) -> inventory.setItem(i, fromElement(e)));
        for (ItemStack content : inventory.getContents()) {
            if (content != null) System.out.println(content);
        }
    }
}
