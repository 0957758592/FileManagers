package com.test.io.filemanager;

import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;

import static org.junit.Assert.*;

public class FileManagerTest {
    private FileManager fm = new FileManager();
    private static final int INITIAL_CAPACITY = 5;
    private File exceptionPath = new File("Test_X");
    private static File file;
    private static File dir;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Rule
    public TemporaryFolder folderToCopy = new TemporaryFolder();

    @Rule
    public TemporaryFolder folderToMove = new TemporaryFolder();

//    @BeforeClass
//    public static void beforeClass() throws IOException {
//        for (int i = 0; i < INITIAL_CAPACITY; i++) {
//            dir = new File("myTest//test" + i + "//test//");
//            file = new File("myTest//test" + i + "//test" + i + ".txt");
//
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//        }
//
//    }

    @Before
    public void before() throws IOException {

        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            dir = tempFolder.newFolder("myTest2", "test" + i, "test");
            file = tempFolder.newFile("myTest2//test" + i + "//test" + i + ".txt");

            if (!dir.exists()) {
                dir.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }
        }

    }

    @Test(expected = InvalidPathException.class)
    public void InvalidPathExceptionTest() {
        fm.countFiles(exceptionPath.getPath());
    }

    @Test
    public void countFilesTest() {
        assertEquals(5, fm.countFiles(tempFolder.getRoot().getAbsolutePath()));
    }

    @Test
    public void countDirsTest() {
        assertEquals(11, fm.countDirs(tempFolder.getRoot().getAbsolutePath()));
    }

    @Test
    public void copyTest() throws IOException {
        fm.copy(tempFolder.getRoot().getAbsolutePath(), folderToCopy.getRoot().getAbsolutePath());
        assertEquals(5, fm.countFiles(folderToCopy.getRoot().getAbsolutePath()));
        assertEquals(11, fm.countDirs(folderToCopy.getRoot().getAbsolutePath()));
    }

    @Test
    public void moveTest() throws IOException {
        assertTrue(folderToCopy.getRoot().exists());
        assertTrue(tempFolder.getRoot().exists());
        assertTrue(folderToMove.getRoot().exists());
        fm.copy(tempFolder.getRoot().getAbsolutePath(), folderToCopy.getRoot().getAbsolutePath());
        fm.move(folderToCopy.getRoot().getPath(), folderToMove.getRoot().getPath());
        assertFalse(folderToCopy.getRoot().exists());
        assertEquals(11, fm.countDirs(folderToMove.getRoot().getAbsolutePath()));
        assertEquals(5, fm.countFiles(folderToMove.getRoot().getAbsolutePath()));
    }

}