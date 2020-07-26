import cc.sfclub.util.bukkit.inv.template.Layout;
import org.junit.Test;

public class Utilies {
    @Test
    public void XYConvertToChestIndex() {
        assert Layout.getIndexFromXY(1, 1) == 10;
        assert Layout.getIndexFromXY(8, 5) == 53;
    }
}
