package com.test.io.filemanager;

import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;

import static org.junit.Assert.*;

public class FileManagerTest {
    private static final int INITIAL_CAPACITY = 5;
    private File exceptionPath = new File("Test_X");

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Rule
    public TemporaryFolder folderToCopy = new TemporaryFolder();

    @Rule
    public TemporaryFolder folderToMove = new TemporaryFolder();

    @Before
    public void before() throws IOException {

        for (int i = 1; i <= INITIAL_CAPACITY; i++) {
            tempFolder.newFolder("myTest2", "test" + i);
            tempFolder.newFile("myTest2//test" + i + "//test" + i + ".txt");
        }
    }

    @Test(expected = InvalidPathException.class)
    public void InvalidPathExceptionTest() {
        FileManager.countFiles(exceptionPath.getPath());
    }

    @Test
    public void countFilesTest() {
        assertEquals(5, FileManager.countFiles(tempFolder.getRoot().getAbsolutePath()));
    }

    @Test
    public void countDirsTest() {
        assertEquals(7, FileManager.countDirs(tempFolder.getRoot().getAbsolutePath()));
    }

    @Test
    public void copyTest() throws IOException {
        FileManager.copy(tempFolder.getRoot().getAbsolutePath(), folderToCopy.getRoot().getAbsolutePath());
        assertEquals(5, FileManager.countFiles(folderToCopy.getRoot().getAbsolutePath()));
        assertEquals(8, FileManager.countDirs(folderToCopy.getRoot().getAbsolutePath()));
    }

    @Test
    public void moveTest() throws IOException {
        FileManager.move(tempFolder.getRoot().getPath(), folderToMove.getRoot().getPath());
        assertEquals(8, FileManager.countDirs(folderToMove.getRoot().getAbsolutePath()));
        assertEquals(5, FileManager.countFiles(folderToMove.getRoot().getAbsolutePath()));
    }

}