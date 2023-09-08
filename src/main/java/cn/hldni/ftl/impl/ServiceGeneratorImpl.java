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
public class ServiceGeneratorImpl implements Generator {

    @Override
    public void generator(String... tableNames) {
        for (String tableName : tableNames) {
            generateService(CodeGeneratorUtils.PACKAGE_NAME, tableName);
        }
    }

    public static void generateService(String packageName, String tableName) {
        String className = CodeGeneratorUtils.getClassName(tableName);
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
            configuration.setDirectoryForTemplateLoading(new File(CodeGeneratorUtils.TEMPLATE_DIR));
            configuration.setDefaultEncoding("UTF-8");

            Template template = configuration.getTemplate("service.ftl");

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("packageName", packageName);
            dataModel.put("className", className);
            dataModel.put("tableRemarks", DatabaseUtils.remarkMap.get(tableName));

//            dataModel.put("classNameLower", CodeGeneratorUtils.toLowerCaseFirstOne(className));

            String fileUrl = CodeGeneratorUtils.OUTPUT_DIR + "/service";
            File outputDir = new File(fileUrl);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            File outputFile = new File(outputDir.getPath() + "/" + className + "Service.java");
            Writer writer = new FileWriter(outputFile);

            template.process(dataModel, writer);

            writer.close();

            System.out.println("Service implementation generated successfully!file:" + fileUrl);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
