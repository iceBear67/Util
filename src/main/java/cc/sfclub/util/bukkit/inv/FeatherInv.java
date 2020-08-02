package cc.sfclub.util.bukkit.inv;

import cc.sfclub.util.bukkit.inv.template.Layout;
import cc.sfclub.util.bukkit.inv.template.Template;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class FeatherInv {
    private final Template template;
    private final Layout layout;
    private final FeatherInv last;
    private final String sign;
    private final InvBird invBird;
    @Getter
    private int pageNum;
    @Getter
    private FeatherInv next;
    private Consumer<FeatherInv> refresh;
    @Getter
    private BukkitTask runningRefresher;
    private InvHolder inv;
    private int refreshInterval;
    @Getter(AccessLevel.PROTECTED)
    private final Map<Integer, Consumer<Context>> clickHandlers = new HashMap<>();
    private String title;

    protected FeatherInv(Template template, FeatherInv previous, String sign, InvBird invBird) {
        this.template = template;
        this.layout = template.layout();
        this.pageNum = 0;
        this.sign = sign;
        this.invBird = invBird;
        last = previous;
    }

    public FeatherInv clickHandler(Consumer<Context> handler) {
        clickHandlers.put(-1, handler);
        return this;
    }

    public FeatherInv clickHandler(int itemIndex, Consumer<Context> handler) {
        clickHandlers.put(itemIndex, handler);
        return this;
    }

    public FeatherInv clickHandler(int x, int y, Consumer<Context> handler) {
        clickHandler(Layout.getIndexFromXY(x, y), handler);
        return this;
    }

    public FeatherInv refreshHandler(int interval, Consumer<FeatherInv> handler) {
        refreshInterval = interval;
        refresh = handler;
        return this;
    }

    public FeatherInv nextPage() {
        if (next != null) return next;
        next = new FeatherInv(template, this, sign, invBird);
        next.pageNum = pageNum++;
        return next;
    }

    public FeatherInv nextPage(Template newTemplate) {
        if (next != null) return next;
        next = new FeatherInv(newTemplate, this, sign, invBird);
        next.pageNum = pageNum++;
        return next;
    }

    public FeatherInv title(String title) {
        this.title = title;
        return this;
    }

    public FeatherInv getPage(int pageNum) {
        if (pageNum > this.pageNum) {
            //look for down
            if (next == null) {
                return null;
            }
            return next.getPage(pageNum);
        } else {
            if (pageNum == this.pageNum) return this;
            if (last == null) {
                return null;
            }
            return last.getPage(pageNum);
        }
    }

    public void show(Player player, int page) {
        if (inv == null) {
            create();
        }
        if (page == this.pageNum) {
            player.openInventory(inv.getInventory());
            if (runningRefresher == null || runningRefresher.isCancelled()) {
                runningRefresher = Bukkit.getScheduler().runTaskTimer(invBird.getPlugin(), () -> refresh.accept(this), 0L, refreshInterval * 20L);
            }
            return;
        }
        if (page > this.pageNum) {
            //look for down
            if (next == null) {
                show(player, 0);//fallback
                return;
            }
            next.show(player, page);
        } else {
            if (last == null) {
                show(player, 0);
                return;
            }
            last.show(player, 0);
        }
    }

    public void showNext(Player player) {
        show(player, pageNum++);

    }

    private void create() {
        inv = new InvHolder(sign, layout.getHeight(), title, this.pageNum);
        layout.applyTo(inv.getInventory());
        refresh.accept(this);
    }

    public Layout layout() {
        return layout;
    }

    public void onClick(InventoryClickEvent event) {
        Context context = Context.builder()
                .action(event.getClick())
                .feather(this)
                .index(event.getSlot())
                .clicker((Player) event.getWhoClicked())
                .event(event)
                .build();
        if (clickHandlers.containsKey(event.getSlot())) {
            clickHandlers.get(event.getSlot()).accept(context);
        } else {
            clickHandlers.get(-1).accept(context);
        }
    }
}
