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

@Component
public class ControllerGeneratorImpl implements Generator {

    @Override
    public void generator(String... tableNames) {
        for (String tableName : tableNames) {
            generateController(CodeGeneratorUtils.PACKAGE_NAME, tableName);
        }
    }

    public static void generateController(String packageName, String tableName) {
        String className = CodeGeneratorUtils.getClassName(tableName);
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
            configuration.setDirectoryForTemplateLoading(new File(CodeGeneratorUtils.TEMPLATE_DIR));
            configuration.setDefaultEncoding("UTF-8");

            Template template = configuration.getTemplate("controller.ftl");

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("packageName", packageName);
            dataModel.put("className", className);
            dataModel.put("classNameLower", CodeGeneratorUtils.toLowerCaseFirstOne(className));
            dataModel.put("tableRemarks", DatabaseUtils.remarkMap.get(tableName));

            File outputDir = new File(CodeGeneratorUtils.OUTPUT_DIR + "/controller");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            File outputFile = new File(CodeGeneratorUtils.OUTPUT_DIR + "/controller/" +  className + "Controller.java");
            Writer writer = new FileWriter(outputFile);

            template.process(dataModel, writer);

            writer.close();

            System.out.println("Controller generated successfully!");
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}