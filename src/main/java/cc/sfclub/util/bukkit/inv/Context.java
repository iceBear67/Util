package cc.sfclub.util.bukkit.inv;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@Builder
@Getter
public class Context {
    private final ClickType action;
    private final int index;
    private final FeatherInv feather;
    private final Player clicker;

    public String getClickedElement() {
        return feather.layout().fromIndex(index);
    }

    public ItemStack getElementAsItem() {
        return feather.layout().fromElement(getClickedElement());
    }
}
