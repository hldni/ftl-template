package cn.hldni.ftl;

import cn.hldni.ftl.impl.EntityGeneratorImpl;
import cn.hldni.ftl.util.CodeGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;


@SpringBootApplication
public class CodeGenerator {
    private static List<Generator> generatorList;

    private static String[] tables;

    @Value("#{'${generator.tables}'.split(',')}")
    public void setTables(String[] tables) {
//        System.out.println(tables.size());
        CodeGenerator.tables = tables;
    }

    @Autowired
    public CodeGenerator(List<Generator> codeGeneratorList) {
        Collections.sort(codeGeneratorList, (l1, l2) -> l1.sort() - l2.sort());
        CodeGenerator.generatorList = codeGeneratorList;
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext context = SpringApplication.run(CodeGenerator.class, args);
        System.out.println(CodeGeneratorUtils.OUTPUT_DIR);
        for (int i = 0; i < tables.length; i++) {
            tables[i] = tables[i].replace(" ", "");

        }
        for (Generator generator : generatorList) {
            generator.generator(tables);
        }
        System.exit(0);
    }


}
