///////////////////////////////////////////////////////////////////////////////////////////////
// Open Eggbert: Free recreation of the computer game Speedy Eggbert.
// Copyright (C) 2024 the original author or authors.
//
// This program is free software: you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation, either version 3
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see
// <https://www.gnu.org/licenses/> or write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
///////////////////////////////////////////////////////////////////////////////////////////////
package com.openeggbert.utils;

import com.openeggbert.core.utils.OpenEggbertUtils;
import com.openeggbert.core.entity.common.GameFileType;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author robertvokac
 */
public class OpenEggbertUtilsTest {

    public OpenEggbertUtilsTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

//    /**
//     * Test of lines method, of class OpenEggbertUtils.
//     */
//    @Test
//    public void testLines() {
//        System.out.println("lines");
//        String string = "";
//        Stream<String> expResult = null;
//        Stream<String> result = OpenEggbertUtils.lines(string);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of streamToList method, of class OpenEggbertUtils.
//     */
//    @Test
//    public void testStreamToList() {
//        System.out.println("streamToList");
//        List expResult = null;
//        List result = OpenEggbertUtils.streamToList(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of createPossibleFileNames method, of class OpenEggbertUtils.
     */
    @Test
    public void testCreatePossibleFileNamesForImage() {
        System.out.println("createPossibleFileNames");
        GameFileType gameFileType = GameFileType.IMAGE8;
        String fileName = "BLUPI";
        String expResult = "BLUPI.BLP\n"
                + "blupi.blp\n"
                + "Blupi.blp\n"
                + "BLUPI.BMP\n"
                + "blupi.bmp\n"
                + "Blupi.bmp\n"
                + "BLUPI.PNG\n"
                + "blupi.png\n"
                + "Blupi.png\n"
                + "BLUPI.JPEG\n"
                + "blupi.jpeg\n"
                + "Blupi.jpeg";
        String result = OpenEggbertUtils.createPossibleFileNames(gameFileType, fileName).stream().collect(Collectors.joining("\n"));

        assertEquals(expResult, result);
    }

    /**
     * Test of createPossibleFileNames method, of class OpenEggbertUtils.
     */
    @Test
    public void testCreatePossibleFileNamesForMusic() {
        System.out.println("createPossibleFileNames");
        GameFileType gameFileType = GameFileType.MUSIC;
        String fileName = "BLUPI";
        String expResult = "BLUPI.BLP\n"
                + "blupi.blp\n"
                + "Blupi.blp\n"
                + "BLUPI.MID\n"
                + "blupi.mid\n"
                + "Blupi.mid\n"
                + "BLUPI.WAV\n"
                + "blupi.wav\n"
                + "Blupi.wav\n"
                + "BLUPI.MP3\n"
                + "blupi.mp3\n"
                + "Blupi.mp3\n"
                + "BLUPI.OGG\n"
                + "blupi.ogg\n"
                + "Blupi.ogg";
        String result = OpenEggbertUtils.createPossibleFileNames(gameFileType, fileName).stream().collect(Collectors.joining("\n"));

        assertEquals(expResult, result);
    }

    /**
     * Test of createPossibleFileNames method, of class OpenEggbertUtils.
     */
    @Test
    public void testCreatePossibleFileNamesForSound() {
        System.out.println("createPossibleFileNames");
        GameFileType gameFileType = GameFileType.SOUND;
        String fileName = "BLUPI";
        String expResult = "BLUPI.BLP\n"
                + "blupi.blp\n"
                + "Blupi.blp\n"
                + "BLUPI.WAV\n"
                + "blupi.wav\n"
                + "Blupi.wav\n"
                + "BLUPI.MP3\n"
                + "blupi.mp3\n"
                + "Blupi.mp3\n"
                + "BLUPI.OGG\n"
                + "blupi.ogg\n"
                + "Blupi.ogg";
        String result = OpenEggbertUtils.createPossibleFileNames(gameFileType, fileName).stream().collect(Collectors.joining("\n"));

        assertEquals(expResult, result);
    }

    /**
     * Test of createPossibleFileNames method, of class OpenEggbertUtils.
     */
    @Test
    public void testCreatePossibleFileNamesForConfig() {
        System.out.println("createPossibleFileNames");
        GameFileType gameFileType = GameFileType.CONFIG;
        String fileName = "CONFIG.DEF";
        String expResult = "CONFIG.DEF\n"
                + "config.def\n"
                + "Config.def";
        String result = OpenEggbertUtils.createPossibleFileNames(gameFileType, fileName).stream().collect(Collectors.joining("\n"));

        assertEquals(expResult, result);
    }

    /**
     * Test of createPossibleFileNames method, of class OpenEggbertUtils.
     */
    @Test
    public void testCreatePossibleFileNamesForWorld() {
        System.out.println("createPossibleFileNames");
        GameFileType gameFileType = GameFileType.WORLD;
        String fileName = "WORLD031.BLP";
        String expResult = "WORLD031.BLP\n"
                + "world031.blp\n"
                + "World031.blp";
        String result = OpenEggbertUtils.createPossibleFileNames(gameFileType, fileName).stream().collect(Collectors.joining("\n"));

        assertEquals(expResult, result);
    }

    /**
     * Test of getFileNameWithoutExtension method, of class OpenEggbertUtils.
     */
    @Test
    public void testGetFileNameWithoutExtension() {
        System.out.println("getFileNameWithoutExtension");
        String fileName = "BLUPI000.BLP";
        String expResult = "BLUPI000";
        String result = OpenEggbertUtils.getFileNameWithoutExtension(fileName);
        assertEquals(expResult, result);
    }

    /**
     * Test of getFileNameWithoutExtension method, of class OpenEggbertUtils.
     */
    @Test
    public void testGetFileNameWithoutExtension2() {
        System.out.println("getFileNameWithoutExtension");
        String fileName = "BLUPI000.BLP.PNG";
        String expResult = "BLUPI000.BLP";
        String result = OpenEggbertUtils.getFileNameWithoutExtension(fileName);
        assertEquals(expResult, result);
    }

    /**
     * Test of getFileNameWithoutExtension method, of class OpenEggbertUtils.
     */
    @Test
    public void testGetFileNameWithoutExtension3() {
        System.out.println("getFileNameWithoutExtension");
        String fileName = "BLUPI000";
        String expResult = "BLUPI000";
        String result = OpenEggbertUtils.getFileNameWithoutExtension(fileName);
        assertEquals(expResult, result);
    }


}
