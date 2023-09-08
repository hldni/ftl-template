package cn.hldni.ftl;
import java.util.HashMap;
import java.util.Map;

public class TypeMappingUtils {
    private static final Map<String, String> COLUMN_TYPE_TO_JAVA_TYPE = new HashMap<>();

    static {
        // 添加常见的数据库列类型与Java类型的映射关系
        COLUMN_TYPE_TO_JAVA_TYPE.put("VARCHAR", "String");
        COLUMN_TYPE_TO_JAVA_TYPE.put("CHAR", "String");
        COLUMN_TYPE_TO_JAVA_TYPE.put("TEXT", "String");
        COLUMN_TYPE_TO_JAVA_TYPE.put("INT", "Integer");
        COLUMN_TYPE_TO_JAVA_TYPE.put("INTEGER", "Integer");
        COLUMN_TYPE_TO_JAVA_TYPE.put("BIGINT", "Long");
        COLUMN_TYPE_TO_JAVA_TYPE.put("SMALLINT", "Short");
        COLUMN_TYPE_TO_JAVA_TYPE.put("TINYINT", "Byte");
        COLUMN_TYPE_TO_JAVA_TYPE.put("FLOAT", "Float");
        COLUMN_TYPE_TO_JAVA_TYPE.put("DOUBLE", "Double");
        COLUMN_TYPE_TO_JAVA_TYPE.put("DECIMAL", "BigDecimal");
        COLUMN_TYPE_TO_JAVA_TYPE.put("DATE", "java.util.Date");
        COLUMN_TYPE_TO_JAVA_TYPE.put("TIME", "java.util.Date");
        COLUMN_TYPE_TO_JAVA_TYPE.put("TIMESTAMP", "java.util.Date");
        COLUMN_TYPE_TO_JAVA_TYPE.put("BIT", "String");
    }

    public static String mapColumnTypeToJavaType(String columnType) {
        return COLUMN_TYPE_TO_JAVA_TYPE.getOrDefault(columnType, "Object");
    }
}
