package cc.sfclub.util;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
public @interface Since {
    String value();
}
