/*
 * SLD Editor - The Open Source Java SLD Editor
 *
 * Copyright (C) 2016, SCISYS UK Limited
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.sldeditor.test.unit.ui.detail.config.symboltype.externalgraphic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.sldeditor.ui.detail.config.symboltype.externalgraphic.RelativePath;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.Test;

/**
 * The unit test for RelativePath.
 *
 * <p>{@link com.sldeditor.ui.detail.config.symboltype.externalgraphic.RelativePath}
 *
 * @author Robert Ward (SCISYS)
 */
public class RelativePathTest {

    /**
     * Test method for {@link
     * com.sldeditor.ui.detail.config.symboltype.externalgraphic.RelativePath#isRelativePath(java.lang.String)}.
     */
    @Test
    public void testIsRelativePath() {
        assertFalse(RelativePath.isRelativePath(null));
        assertTrue(RelativePath.isRelativePath("testfile.sld"));
        assertTrue(RelativePath.isRelativePath("a/b/c/testfile.sld"));
        assertFalse(RelativePath.isRelativePath("c:\\testfile.sld"));
        assertFalse(RelativePath.isRelativePath("/tmp/testfile.sld"));
        assertFalse(RelativePath.isRelativePath(new File(".").getAbsolutePath()));
    }

    /**
     * Test method for {@link
     * com.sldeditor.ui.detail.config.symboltype.externalgraphic.RelativePath#convert(java.net.URL,
     * boolean)}.
     */
    @Test
    public void testConvert() {
        assertEquals("", RelativePath.convert(null, true));
        assertEquals("", RelativePath.convert(null, false));

        String filename = "test.txt";
        try {
            String fileAbsolutePath = new File(".", filename).getCanonicalPath();
            URL fileURL = new File(fileAbsolutePath).toURI().toURL();
            String actualResult = RelativePath.convert(fileURL, false);

            assertEquals(fileURL.toExternalForm(), actualResult);

            actualResult = RelativePath.convert(fileURL, true);

            assertEquals(filename, actualResult);

            // Try http://
            String urlString = "http://example.com/test";
            URL httpURL = new URL(urlString);
            actualResult = RelativePath.convert(httpURL, false);

            assertEquals(urlString, actualResult);

            actualResult = RelativePath.convert(httpURL, true);

            assertEquals(urlString, actualResult);

        } catch (MalformedURLException e) {
            fail(e.getStackTrace().toString());
        } catch (IOException e1) {
            fail(e1.getStackTrace().toString());
        }
    }

    /**
     * Test method for {@link
     * com.sldeditor.ui.detail.config.symboltype.externalgraphic.RelativePath#getRelativePath(java.io.File,
     * java.io.File)}.
     */
    @Test
    public void testGetRelativePath() {
        String filename = "testFile.txt";
        File file = new File(".", filename);
        File folder = file;

        assertNull(RelativePath.getRelativePath(null, folder));
        assertNull(RelativePath.getRelativePath(file, null));

        String actualResult = RelativePath.getRelativePath(file, folder);
        assertEquals("", actualResult);

        folder = file.getParentFile();
        actualResult = RelativePath.getRelativePath(file, folder);
        assertEquals(filename, actualResult);
    }

    @Test
    public void testIsLocalFile() {
        assertFalse(RelativePath.isLocalFile(null));

        String urlString = "http://example.com/test";
        try {
            URL httpURL = new URL(urlString);
            assertFalse(RelativePath.isLocalFile(httpURL));

            File f = File.createTempFile("tst", ".txt");
            URL fileURL = f.toURI().toURL();
            assertTrue(RelativePath.isLocalFile(fileURL));
            f.delete();
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testHasHost() {
        assertFalse(RelativePath.hasHost(null));

        try {
            String urlString = "http://example.com/test";
            URL httpURL = new URL(urlString);
            assertTrue(RelativePath.hasHost(httpURL));

        } catch (MalformedURLException e) {
            fail(e.getMessage());
        }
    }
}
