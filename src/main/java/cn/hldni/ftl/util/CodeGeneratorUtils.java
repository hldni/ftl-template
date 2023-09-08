package cn.hldni.ftl.util;


import cn.hldni.ftl.StringUtils;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class CodeGeneratorUtils {
    public static String PACKAGE_NAME = "";
    public static String PROJECT_DIR = System.getProperty("user.dir");
    public static String OUTPUT_DIR = PROJECT_DIR + "/src/main/java/" + PACKAGE_NAME.replace(".", "/");
    public static String TEMPLATE_DIR = PROJECT_DIR + "/src/main/resources/ftl";

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
    }

    @Value("${package}")
    @Order(0)
    public void setPackageName(String packageName){
        if (org.springframework.util.StringUtils.hasText(packageName)) {
            CodeGeneratorUtils.PACKAGE_NAME = packageName;
        }
    }


    //    @Value("${projectDir}")
//    public void setProjectDir(String projectDir){
//
//        if (org.springframework.util.StringUtils.hasText(projectDir)) {
//            return;
//        }
//        CodeGeneratorUtils.PROJECT_DIR = projectDir;
//    }
    @Value("${outputDir}")
    @Order(20)
    public void setOutputDir(String outputDir){
        if ("".equals(PACKAGE_NAME)) {
            System.out.println("程序配置【PACKAGE_NAME、OUTPUT_DIR】读取顺序错误,重新启动");
            System.exit(0);
        }
        if (org.springframework.util.StringUtils.hasText(outputDir)) {
            CodeGeneratorUtils.OUTPUT_DIR = outputDir + PACKAGE_NAME.replace(".", "/");
        }
    }
//    @Value("${templateDir}")
//    public void setTemplateDir(String templateDir){
//        if (org.springframework.util.StringUtils.hasText(templateDir)) {
//            return;
//        }
//        CodeGeneratorUtils.TEMPLATE_DIR = templateDir;
//    }


    // 根据表名获取类名
    public static String getClassName(String tableName) {
        tableName = tableName.replaceFirst("t_", "");
        // 根据表名转换规则生成类名
        tableName = StringUtils.underscoreToCamelCase(tableName);
        return tableName.substring(0, 1).toUpperCase() + tableName.substring(1);
    }

    public static String toLowerCaseFirstOne(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        if (Character.isLowerCase(str.charAt(0))) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

}
