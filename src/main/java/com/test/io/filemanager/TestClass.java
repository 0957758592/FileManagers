package com.test.io.filemanager;

import java.io.IOException;

public class TestClass {

    public static void main(String[] args) throws IOException {

        System.out.println("files "+ FileManager.countFiles("E:/JAVA_KATYA_EDUCATION/IO/filemanager/myTest"));
//        System.out.println("=======crashFilePath=======");
//        System.out.println(FileManager.countFiles("E:/JAVA_KATYA_EDUCATION/IO/filemanager/myTes"));
        System.out.println("==============");
        System.out.println("Dirs "+FileManager.countDirs("E:/JAVA_KATYA_EDUCATION/IO/filemanager/myTest"));
//        System.out.println("=======crashDirPath=======");
//        System.out.println(FileManager.countDirs("E:/JAVA_KATYA_EDUCATION/IO/filemanager/myTes"));
        System.out.println("==============");
//        FileManager.move("myTest3", "E:/JAVA_KATYA_EDUCATION/IO/filemanager/myTest4");
//        FileManager.move("myTest4", "E:/JAVA_KATYA_EDUCATION/IO/filemanager/myTest2");
        FileManager.copy("E:/JAVA_KATYA_EDUCATION/IO/filemanager/myTest/test3/test3.txt", "E:/JAVA_KATYA_EDUCATION/IO/filemanager/myTest4");
//        FileManager.copy("E:/JAVA_KATYA_EDUCATION/IO/filemanager/myTest3/test4.txt", "E:/JAVA_KATYA_EDUCATION/IO/filemanager/myTest5");
//        FileManager.copy("E:/JAVA_KATYA_EDUCATION/IO/filemanager/myTest3/test4.txt", "E:/JAVA_KATYA_EDUCATION/IO/filemanager/myTest5");
//        FileManager.copy("E:/JAVA_KATYA_EDUCATION/IO/filemanager/myTest3/test4.txt", "E:/JAVA_KATYA_EDUCATION/IO/filemanager/myTest5");
//        FileManager.copy("myTest", "E:/JAVA_KATYA_EDUCATION/IO/filemanager/myTest2");
//        FileManager.copy("myTest3", "E:/JAVA_KATYA_EDUCATION/IO/filemanager/myTest4");

    }

}
