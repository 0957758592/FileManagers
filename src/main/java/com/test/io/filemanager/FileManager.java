package com.test.io.filemanager;

import java.io.*;
import java.nio.file.InvalidPathException;

public class FileManager {

    static int countFiles(String path) {
        File directory = new File(path);
        isPathExist(directory);
        pathIsDirectory(directory);

        File[] files = directory.listFiles();
        pathIsFile(files, directory);

        int count = 0;

        for (File file : files) {
            if (file.isDirectory()) {
                count += countFiles(file.getPath());
            } else {
                count++;
            }
        }
        return count;
    }

    static int countDirs(String path) {
        File directory = new File(path);
        isPathExist(directory);
        pathIsDirectory(directory);

        File[] files = directory.listFiles();
        pathIsFile(files, directory);

        int count = 0;

        for (File file : files) {
            if (file.isDirectory()) {
                count++;
                count += countDirs(file.getAbsolutePath());
            }
        }
        return count;
    }

    static void copy(String from, String to) throws IOException {
        File fileFrom = new File(from);
        File fileTo = new File(to);
        isPathExist(fileFrom);

        if (fileFrom.isDirectory()) {

            if (!fileTo.exists()) {
                createDirectory(fileTo);
            }

            File[] files = fileFrom.listFiles();

            for (File file : files) {
                copy(from + "/" + file.getName(), to + "/" + file.getName());
            }

        } else {
            writeFile(fileFrom, fileTo);
        }
    }

    static void move(String from, String to) throws IOException {
        File pathFrom = new File(from);
        File pathTo = new File(to);
        isPathExist(pathFrom);

        if (pathFrom.isDirectory()) {

            if (!pathTo.exists()) {
                createDirectory(pathTo);
            }

            File[] listFiles = pathFrom.listFiles();

            for (File file : listFiles) {
                move(pathFrom.getPath() + "/" + file.getName(), pathTo.getPath() + "/" + file.getName());
                deleteFile(file);
            }

        } else {
            writeFile(pathFrom, pathTo);
        }

    }

    private static void writeFile(File fileFrom, File fileTo) throws IOException {

        try (InputStream in = new FileInputStream(fileFrom);
             OutputStream out = new FileOutputStream(fileTo)) {
            byte[] buffer = new byte[2048];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }

    private static void isPathExist(File fileFrom) {
        if (!fileFrom.exists()) {
            throw new InvalidPathException(fileFrom.getName(), "The path '" + fileFrom.getAbsolutePath() + "' is not defined ");
        }
    }

    private static void pathIsDirectory(File filePath) {
        if (!filePath.isDirectory()) {
            throw new InvalidPathException(filePath.getName(), "The path '" + filePath.getAbsolutePath() + "' is not a Directory ");
        }
    }

    private static void pathIsFile(File[] files, File filePath) {
        if (files == null) {
            throw new NullPointerException("The path to " + filePath.getAbsolutePath() + " should be a Directory");
        }
    }

    private static void createDirectory(File filePath) throws IOException {
        if (!filePath.mkdirs()) {
            throw new IOException("Can't create directories in " + filePath.getAbsolutePath());
        }
    }

    private static void deleteFile(File file) throws IOException {
        if (!file.delete()) {
            throw new IOException("Can't create directories");
        }
    }
}