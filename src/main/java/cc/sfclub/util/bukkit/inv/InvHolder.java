package cc.sfclub.util.bukkit.inv;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InvHolder implements InventoryHolder {
    private final Inventory inventory;
    @Getter
    private final String sign;
    @Getter
    private final int page;

    public InvHolder(String sign, int height, String title, int page) {
        inventory = Bukkit.createInventory(this, height * 9, title);
        this.page = page;
        this.sign = sign;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
