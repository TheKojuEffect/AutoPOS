package io.koju.autopos.shared.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ClassUtil {

    public static List<Field> getAllFields(Class<?> type, Class<?> fieldType) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            final Field[] declaredFields = c.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (fieldType.isAssignableFrom(declaredField.getType())) {
                    fields.add(declaredField);
                }
            }
        }
        return fields;
    }

}
