package ru.kkiselev;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by kv on 13.12.16.
 */
public class Main {

    public static Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) {

        login("user", "user");



/*
        System.out.println("РАБОТАЕТ 8)");
        System.out.println("РАБОТАЕТ 8)");
        System.out.println("РАБОТАЕТ 8)");
        System.out.println("РАБОТАЕТ 8)");
        System.out.println("РАБОТАЕТ 8)");
        System.out.println("РАБОТАЕТ 8)");
        System.out.println("РАБОТАЕТ 8)");
        System.out.println("РАБОТАЕТ 8)");
        System.out.println("РАБОТАЕТ 8)");
        System.out.println("РАБОТАЕТ 8)");
        System.out.println("РАБОТАЕТ 8)");

        Class1 c1 = new Class1();
        Class2 c2 = new Class2();
        Class3 c3 = new Class3();
        */

        File dir = new File("/Users/kv/Documents/Innopolis/projects/versions/src/");
        findAllFiles(dir);

    }

    static void login(String userName, String password){
        try {
            MDC.put("userName", userName);
            MDC.put("password", password);
            doSome();
        } finally {
            MDC.clear();
        }
    }

    static  void doSome(){

        logger.warn("main main main main main main main main");
    }

    private static void findAllFiles(File dir){
        File[] files = dir.listFiles();
        if(files != null){
            for(File child: files){
                if(child.isDirectory()){
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    String changeDate = df.format(child.lastModified());
                    logger.warn("directotry " + child.getName() + " last modified " + changeDate);
                    findAllFiles(new File(child.getPath()));
                } else if(child.isFile()){
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    String changeDate = df.format(child.lastModified());
                    logger.warn("file " + child.getName() + " last modified " + changeDate);
                }
            }
        }
    }
}
