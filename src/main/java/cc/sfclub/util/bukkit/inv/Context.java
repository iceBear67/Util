package cc.sfclub.util.bukkit.inv;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.event.inventory.ClickType;

@Builder
@Getter
public class Context {
    private final ClickType action;
    private final int index;
    private final FeatherInv featherInv;

    public String getClickedElement() {
        return featherInv.layout().fromIndex(index);
    }

    public FeatherInv getFeather() {
        return featherInv;
    }
}
