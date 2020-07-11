package apps.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


public class AppsUtil {

    /*
     * Use reflection to reset the apps.AppsBase instance
     */
    public static void resetAppsBase() {
        try {
            Class<?> c = apps.AppsBase.class;
            Field f = c.getDeclaredField("preInit");
            f.setAccessible(true);
            f.set(null, false);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException x) {
            log.error("Failed to reset apps.AppsBase static preInit field", x);
        }
    }

    private final static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AppsUtil.class);
}
