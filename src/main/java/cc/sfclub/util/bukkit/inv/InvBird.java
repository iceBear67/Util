package cc.sfclub.util.bukkit.inv;

import cc.sfclub.util.bukkit.inv.template.Template;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class InvBird implements Listener {
    @Getter
    private final JavaPlugin plugin;
    private final Map<String, FeatherInv> inventories = new HashMap<>();

    public InvBird(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public FeatherInv drop(Template t, String name) {
        inventories.put(name, new FeatherInv(t, null, name, this));
        return inventories.get(name);
    }

    @EventHandler
    public void onClosing(InventoryCloseEvent evt) {
        if (evt.getInventory().getHolder() != null || evt.getInventory().getHolder() instanceof InvHolder) {
            InvHolder invHolder = (InvHolder) evt.getInventory().getHolder();
            if (inventories.containsKey(invHolder.getSign())) {
                FeatherInv featherInv = inventories.get(invHolder.getSign());
                featherInv.getPage(invHolder.getPage()).getRunningRefresher().cancel();
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent evt) {
        if (evt.getInventory().getHolder() != null || evt.getInventory().getHolder() instanceof InvHolder) {
            InvHolder invHolder = (InvHolder) evt.getInventory().getHolder();
            if (inventories.containsKey(invHolder.getSign())) {
                FeatherInv featherInv = inventories.get(invHolder.getSign());
                featherInv.getPage(invHolder.getPage()).getClick().accept(
                        Context.builder()
                                .action(evt.getClick())
                                .featherInv(featherInv)
                                .index(evt.getSlot())
                                .build()
                );
            }
        }
    }
}
