package com.test.io.filemanager;

import java.io.*;
import java.nio.file.InvalidPathException;

public class FileManager {

    public static void main(String[] args) throws IOException {

        String path = "myTest";
        String copyTo = "myTest2";
        String moveTo = "myTest3";

        System.out.println("files: " + countFiles(path));
        System.out.println("folders: " + countDirs(path));
        copy(path, copyTo);
        move(copyTo, moveTo);
    }

    // public static int countFiles(String path) - принимает путь к папке,
    // возвращает количество файлов в папке и всех подпапках по пути
    public static int countFiles(String path) {
        int count = 0;
        File filePath = new File(path);
        isPathExist(filePath);
        checkToAccessFile(filePath);

        for (File s : filePath.listFiles()) {
            if (s.isDirectory()) {
                count += countFiles(s.getPath());
            } else if (s.isFile()) {
                count++;
            }
        }

        return count;
    }

    // public static int countDirs(String path) - принимает путь к папке,
    // возвращает количество папок в папке и всех подпапках по пути
    public static int countDirs(String path) {
        File filePath = new File(path);
        isPathExist(filePath);
        checkToAccessFile(filePath);

        File[] files = filePath.listFiles();
        int count = 0;

        if (files != null) {

            for (File file : files) {
                if (file.isDirectory()) {
                    count++;
                    count += countDirs(file.getAbsolutePath());
                }
            }
            return count;
        } else {
            throw new NullPointerException("the folder " + filePath.getPath() + "is Empty");
        }
    }

    // - метод по копированию папок и файлов.
    // Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться копирование.
    public static void copy(String from, String to) throws IOException {
        File fileFrom = new File(from);
        File fileTo = new File(to);
        isPathExist(fileFrom);

        if (fileFrom.isDirectory()) {
            if (!fileTo.exists()) {
                fileTo.mkdir();
            }

            String[] files = fileFrom.list();
            if (files != null) {
                for (String file : files) {
                    File src = new File(fileFrom, file);
                    File dest = new File(fileTo, file);
                    copy(src.getAbsolutePath(), dest.getAbsolutePath());
                }
            } else {
                throw new NullPointerException("the folder " + fileFrom.getPath() + "is Empty");
            }
        } else {
            try (InputStream in = new FileInputStream(fileFrom);
                 OutputStream out = new FileOutputStream(fileTo)) {

                byte[] buffer = new byte[2048];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }
        }
    }

    //    - метод по перемещению папок и файлов.
    //    Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться перемещение.

    public static void move(String from, String to) throws IOException {
        copy(from, to);
        removeFiles(from, to);
    }

    private static void removeFiles(String from, String to) {
        File pathFrom = new File(from);
        File pathTo = new File(to);
        isPathExist(pathFrom);

        if (pathTo.isDirectory()) {
            for (File file : pathFrom.listFiles()) {
                if (file.isDirectory()) {
                    removeFiles(file.getPath(), to);
                }
                file.delete();
            }
            pathFrom.delete();
        } else {
            throw new InvalidPathException(pathTo.getName(), "Should be a folder");
        }
    }

    private static void isPathExist(File fileFrom) {
        if (!fileFrom.exists()) {
            throw new InvalidPathException(fileFrom.getPath(), "There is no such file or folder");
        }
    }

    private static void checkToAccessFile(File filePath) {
        if (filePath.listFiles() == null) {
            throw new IllegalAccessError("Access to file " + filePath.getAbsolutePath() + "is deny");
        }
    }
}