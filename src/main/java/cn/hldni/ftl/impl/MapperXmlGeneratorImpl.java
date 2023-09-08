package cn.hldni.ftl.impl;
import cn.hldni.ftl.DatabaseUtils;
import cn.hldni.ftl.Generator;
import cn.hldni.ftl.util.CodeGeneratorUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Mapper XML生成器
 */
//@Component
public class MapperXmlGeneratorImpl implements Generator {
    @Override
    public void generator(String... tableNames) {
        for (String tableName : tableNames) {
            generateMapperXml(CodeGeneratorUtils.PACKAGE_NAME, tableName);
        }
    }

    public static void generateMapperXml(String packageName, String tableName) {
        String className = CodeGeneratorUtils.getClassName(tableName);
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
            configuration.setDirectoryForTemplateLoading(new File(CodeGeneratorUtils.TEMPLATE_DIR));
            configuration.setDefaultEncoding("UTF-8");

            Template template = configuration.getTemplate("mapperXml.ftl");

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("packageName", packageName);
            dataModel.put("className", className);
            dataModel.put("tableRemarks", DatabaseUtils.remarkMap.get(tableName));


            String fileUrl = CodeGeneratorUtils.PROJECT_DIR + "/src/main/resources/mapper";
            String templateDir = fileUrl;
            File outputDir = new File(templateDir);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            File outputFile = new File(templateDir + "/" + className + "Mapper.xml");
            Writer writer = new FileWriter(outputFile);

            template.process(dataModel, writer);

            writer.close();

            System.out.println("Mapper XML generated successfully!file:" + fileUrl);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
