package com.test.io.filemanager;

import java.io.*;
import java.nio.file.InvalidPathException;

class FileManager {

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
                count += countDirs(file.getAbsolutePath());
            }
        }
        count++;
        return count;
    }

    static void copy(String from, String to) throws IOException {
        File fileFrom = new File(from);
        moveOrCopy(to, fileFrom, true);
    }

    static void move(String from, String to) throws IOException {
        File pathFrom = new File(from);
        moveOrCopy(to, pathFrom, false);
        deleteFile(pathFrom);
    }

    private static void moveOrCopy(String to, File pathFrom, boolean copy) throws IOException {
        isPathExist(new File(to));

        if (pathFrom.isDirectory()) {

            File[] listFiles = pathFrom.listFiles();
            pathIsFile(listFiles, pathFrom);

            for (File file : listFiles) {
                recursiveMoveOrCopy(to, file, copy);
            }
        } else {
            writeFile(pathFrom, to);
        }
    }

    private static void recursiveMoveOrCopy(String to, File pathFrom, boolean copy) throws IOException {
        String substr = pathFrom.getParent().substring(pathFrom.getParent().lastIndexOf('\\'));
        File pathTo = new File(to + "\\" + substr);

        if (!pathTo.exists()) {
            createDirectory(pathTo);
        }

        if (copy) {
            copy(pathFrom.getAbsolutePath(), pathTo.getAbsolutePath());
        } else {
            move(pathFrom.getAbsolutePath(), pathTo.getAbsolutePath());
        }
    }

    private static void writeFile(File fileFrom, String to) throws IOException {
        File fileTo = new File(to);

        if (fileTo.isDirectory()) {
            fileTo = new File(to + "/" + fileFrom.getName());
        }

        try (InputStream in = new FileInputStream(fileFrom.getAbsolutePath());
             OutputStream out = new FileOutputStream(fileTo)) {

            byte[] buffer = new byte[2048];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }

    private static void isPathExist(File path) {
        if (!path.exists()) {
            throw new InvalidPathException(path.getName(), "The path '" + path.getAbsolutePath() + "' is not defined ");
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
            throw new IOException("Can't delete directories");
        }
    }

}