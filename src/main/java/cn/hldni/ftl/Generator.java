package cn.hldni.ftl;

public interface Generator {

    void generator(String... tableNames);

    default int sort(){
        return 1;
    }
}
