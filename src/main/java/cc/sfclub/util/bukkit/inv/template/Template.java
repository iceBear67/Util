package cc.sfclub.util.bukkit.inv.template;

import lombok.Getter;

public class Template {
    @Getter
    private final String title;
    private Layout layout;

    private Template(String title, int line) {
        this.title = title;
        this.layout = new Layout(line);
    }

    private Template(String title, Layout layout) {
        this.title = title;
        this.layout = layout;
    }

    public static Template build(String title, int line) {
        return new Template(title, line);
    }

    public static Template build(String title, Layout layout) {
        return new Template(title, layout);
    }

    public Layout layout() {
        return layout;
    }

    public Template layout(Layout layout) {
        this.layout = layout;
        return this;
    }
}