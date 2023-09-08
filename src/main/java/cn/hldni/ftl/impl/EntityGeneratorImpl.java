package cn.hldni.ftl.impl;

import cn.hldni.ftl.DatabaseUtils;
import cn.hldni.ftl.Generator;
import cn.hldni.ftl.util.CodeGeneratorUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EntityGeneratorImpl implements Generator {

    @Value("${spring.datasource.url:}")
    private String url;
    @Value("${spring.datasource.username:}")
    private String username;
    @Value("${spring.datasource.password:}")
    private String password;

    private void excute(String... tableNames) throws IOException {


        // 创建 FreeMarker 配置
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        configuration.setDirectoryForTemplateLoading(new File(CodeGeneratorUtils.TEMPLATE_DIR));
        configuration.setDefaultEncoding("UTF-8");

        try {
            // 获取模板文件
            Template template = configuration.getTemplate("entity.ftl");

            // 处理每个表
            for (String tableName : tableNames) {
                // 查询表结构，获取列信息等
                List<DatabaseUtils.Column> columns = getColumns(tableName);

                // 设置模板参数
                Map<String, Object> dataModel = new HashMap<>();
                dataModel.put("packageName", CodeGeneratorUtils.PACKAGE_NAME);
                dataModel.put("tableName", tableName);
                dataModel.put("className", CodeGeneratorUtils.getClassName(tableName));
                dataModel.put("columns", columns);


                File outputDir = new File(CodeGeneratorUtils.OUTPUT_DIR + "/domain");
                if (!outputDir.exists()) {
                    outputDir.mkdirs();
                }
                // 生成代码文件路径
                String filePath = CodeGeneratorUtils.OUTPUT_DIR + "/domain/" + CodeGeneratorUtils.getClassName(tableName) + ".java";
                File outputFile = new File(filePath);
                outputFile.getParentFile().mkdirs(); // 创建目录

                /**
                 * 设置表备注
                 */
                dataModel.put("tableRemarks", DatabaseUtils.remarkMap.get(tableName));
                // 生成代码文件
                FileWriter fileWriter = new FileWriter(outputFile);
                template.process(dataModel, fileWriter);
                fileWriter.close();
            }

            System.out.println("Code generated successfully.");
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    // 模拟获取表的列信息
    private List<DatabaseUtils.Column> getColumns(String tableName) {
        // 这里根据表名查询数据库或其他方式获取表的列信息，返回列对象的列表
        // 简化示例，这里直接返回一个空列表
        return DatabaseUtils.getColumns(url, username, password, tableName);
    }

    @Override
    public void generator(String... tableNames) {
        try {
            this.excute(tableNames);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int sort() {
        return 0;
    }
}
