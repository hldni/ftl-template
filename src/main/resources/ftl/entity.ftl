package ${packageName}.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
* ${tableRemarks}
*/
@Data
@TableName("${tableName}")
public class ${className} {

<#list columns as column>
    /**
    * ${column.remarks}
    */
    <#if column.javaType == "java.util.Date">
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    </#if>
    <#if column.javaName == "createTime">
    @TableField(value = "${column.columnName}", fill = FieldFill.INSERT)
    </#if>
    <#if column.javaName == "updateTime">
    @TableField(value = "${column.columnName}", fill = FieldFill.INSERT_UPDATE)
    </#if>
    <#if column.javaName != "createTime" && column.javaName != "updateTime">
    @TableField(value = "${column.columnName}")
    </#if>
    private ${column.javaType} ${column.javaName};
</#list>

}
