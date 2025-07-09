package org.yaojiu.supermarket.utils;
// ... existing code ...
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DynamicQueryUtils {
    public static QueryWrapper<?> getQueryWrapper(Object queryObj) {
        QueryWrapper<?> wrapper = new QueryWrapper<>();
        if (queryObj == null) return wrapper;

        Map<String, Comparable<?>> minFields = new HashMap<>();
        Map<String, Comparable<?>> maxFields = new HashMap<>();

        Field[] fields = queryObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            try {
                Object value = field.get(queryObj);
                if (value == null) continue;

                if (fieldName.endsWith("Min")) {
                    if (value instanceof Comparable<?>) {
                        String baseName = fieldName.substring(0, fieldName.length() - 3);
                        minFields.put(baseName, (Comparable<?>) value);
                    } else {
                        throw new IllegalArgumentException(
                                queryObj.getClass() + ": " + fieldName + " 不是一个有效的比较类型，但却使用了Min后缀"
                        );
                    }
                } else if (fieldName.endsWith("Max")) {
                    if (value instanceof Comparable<?>) {
                        String baseName = fieldName.substring(0, fieldName.length() - 3);
                        maxFields.put(baseName, (Comparable<?>) value);
                    } else {
                        throw new IllegalArgumentException(
                                queryObj.getClass() + ": " + fieldName + " 不是一个有效的比较类型，但却使用了Max后缀"
                        );
                    }
                } else {
                    addCondition(wrapper, fieldName, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("动态查询构建失败 - 字段: " + fieldName, e);
            }
        }
        for (String baseName : minFields.keySet()) {
            Comparable<?> minVal = minFields.get(baseName);
            Comparable<?> maxVal = maxFields.get(baseName);
            if (maxVal != null) {
                wrapper.between(baseName, minVal, maxVal);
            } else {
                wrapper.ge(baseName, minVal);
            }
        }
        for (String baseName : maxFields.keySet()) {
            if (!minFields.containsKey(baseName)) {
                wrapper.le(baseName, maxFields.get(baseName));
            }
        }

        return wrapper;
    }

    private static void addCondition(QueryWrapper<?> wrapper, String fieldName, Object value) {
        if (value instanceof String) {
            String strVal = (String) value;
            if (!strVal.isEmpty()) {
                wrapper.like(fieldName, strVal);
            }
        } else {
            wrapper.eq(fieldName, value);
        }
    }
}