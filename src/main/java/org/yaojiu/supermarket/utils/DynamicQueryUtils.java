package org.yaojiu.supermarket.utils;
// ... existing code ...
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DynamicQueryUtils {
    /**
     * 根据查询对象动态生成查询条件包装器
     *
     * @param queryObj 查询条件封装对象，包含过滤条件字段（可为null）
     * @return QueryWrapper<?> 构建完成的查询条件包装器实例
     * 该方法通过反射解析查询对象的字段，支持以下条件构建：
     * 1. 普通字段：直接添加等值查询条件
     * 2. Min后缀字段：添加大于等于条件（需实现Comparable接口）
     * 3. Max后缀字段：添加小于等于条件（需实现Comparable接口）
     * 4. 同时存在Min和Max后缀字段时，合并为between条件
     */
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
            String dbFieldName = toUnderlineName(baseName);
            Comparable<?> minVal = minFields.get(baseName);
            Comparable<?> maxVal = maxFields.get(baseName);
            if (maxVal != null) {
                wrapper.between(dbFieldName, minVal, maxVal);
            } else {
                wrapper.ge(dbFieldName, minVal);
            }
        }
        for (String baseName : maxFields.keySet()) {
            String dbFieldName = toUnderlineName(baseName);
            if (!minFields.containsKey(baseName)) {
                wrapper.le(dbFieldName, maxFields.get(baseName));
            }
        }

        return wrapper;
    }

    private static void addCondition(QueryWrapper<?> wrapper, String fieldName, Object value) {
        String dbFieldName = toUnderlineName(fieldName);
        if (value instanceof String) {
            String strVal = (String) value;
            if (!strVal.isEmpty()) {
                wrapper.like(dbFieldName, strVal);
            }
        } else {
            wrapper.eq(dbFieldName, value);
        }
    }
    private static String toUnderlineName(String camelName) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < camelName.length(); i++) {
            char ch = camelName.charAt(i);
            if (Character.isUpperCase(ch)) {
                if (i > 0) result.append("_");
                result.append(Character.toLowerCase(ch));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }
}