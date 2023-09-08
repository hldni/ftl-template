package cn.hldni.ftl;

import lombok.Data;

import java.sql.*;
import java.util.*;

public class DatabaseUtils {
    public static final Map<String, String> remarkMap = new HashMap<>(32);

    public static List<Column> getColumns(String url, String username, String password,String tableName) {
        List<Column> columns = new ArrayList<>();
        HashSet<String> columnNames = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet tables = metadata.getTables(null, null, tableName, null);
            tables.next();
            remarkMap.put(tableName, tables.getString("REMARKS"));

            ResultSet resultSet = metadata.getColumns(null, null, tableName, null);
            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                String columnType = resultSet.getString("TYPE_NAME");
                String remarks = resultSet.getString("REMARKS");
                // 过滤非普通数据列
                // 去重逻辑，如果已经添加过该列名，则跳过
                if (columnNames.contains(columnName)) {
                    continue;
                }
                columnNames.add(columnName);
                columns.add(new Column(columnName, columnType, remarks));
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return columns;
    }

    // 列对象的定义
    @Data
    public static class Column {
        private String columnName;
        private String columnType;
        private String remarks;
        private String javaType;
        private String javaName;

        public Column(String columnName, String columnType, String remarks) {
            this.columnName = columnName;
            this.columnType = columnType;
            this.remarks = remarks;
            this.javaType = TypeMappingUtils.mapColumnTypeToJavaType(columnType);
            this.javaName = StringUtils.underscoreToCamelCase(columnName);
        }

        // Getters and setters...
    }

    public static void main(String[] args) {
        System.out.println("12312");
    }
}
