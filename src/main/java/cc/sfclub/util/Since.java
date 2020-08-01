package cc.sfclub.util;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface Since {
    String value();
}
