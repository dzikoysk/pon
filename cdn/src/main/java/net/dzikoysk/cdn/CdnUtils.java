package net.dzikoysk.cdn;

import net.dzikoysk.cdn.entity.Exclude;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public final class CdnUtils {

    private CdnUtils() {}

    static boolean isIgnored(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
            return true;
        }

        // ignore Groovy properties
        if (field.getName().startsWith("__$") || field.getName().startsWith("$")) {
            return true;
        }

        return field.isAnnotationPresent(Exclude.class);
    }

    public static String destringify(String value) {
        for (String operator : CdnConstants.STRING_OPERATORS) {
            if (value.startsWith(operator) && value.endsWith(operator)) {
                return value.substring(1, value.length() - 1);
            }
        }

        return value;
    }

    public static String stringify(String value) {
        if (!value.startsWith("\"") && !value.endsWith("\"")) {
            if (value.isEmpty() || value.trim().length() != value.length() || value.endsWith(",")) {
                return "\"" + value + "\"";
            }
        }

        return value;
    }

}
