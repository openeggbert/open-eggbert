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
package com.openeggbert.compatibility;

import com.openeggbert.core.utils.FileNameCaseType;
import com.openeggbert.core.gamespace.GameFileType;
import com.openeggbert.core.main.OpenEggbertException;
import com.openeggbert.core.utils.OpenEggbertUtils;
import java.util.List;
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
public class FileNameCaseTypeTest {

    public FileNameCaseTypeTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }

//    /**
//     * Test of values method, of class FileNameCaseType.
//     */
//    @org.junit.jupiter.api.Test
//    public void testValues() {
//        System.out.println("values");
//        FileNameCaseType[] expResult = null;
//        FileNameCaseType[] result = FileNameCaseType.values();
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of valueOf method, of class FileNameCaseType.
//     */
//    @org.junit.jupiter.api.Test
//    public void testValueOf() {
//        System.out.println("valueOf");
//        String name = "";
//        FileNameCaseType expResult = null;
//        FileNameCaseType result = FileNameCaseType.valueOf(name);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of ofString method, of class FileNameCaseType.
     */
    @org.junit.jupiter.api.Test
    public void ofStringExpectedReturnedValueIsUPPERCASE() {
        System.out.println("ofString");
        String string = "BLUPI000.BLP";

        FileNameCaseType expResult = FileNameCaseType.UPPERCASE;
        FileNameCaseType result = FileNameCaseType.ofString(string);
        assertEquals(expResult, result);
    }

    /**
     * Test of ofString method, of class FileNameCaseType.
     */
    @org.junit.jupiter.api.Test
    public void ofStringExpectedReturnedValueIsLOWERCASE() {
        System.out.println("ofString");
        String string = "blupi000.blp";

        FileNameCaseType expResult = FileNameCaseType.LOWERCASE;
        FileNameCaseType result = FileNameCaseType.ofString(string);
        assertEquals(expResult, result);
    }

    /**
     * Test of ofString method, of class FileNameCaseType.
     */
    @org.junit.jupiter.api.Test
    public void ofStringExpectedReturnedValueIsCAPITALIZATION() {
        System.out.println("ofString");
        String string = "Blupi000.blp";

        FileNameCaseType expResult = FileNameCaseType.CAPITALIZATION;
        FileNameCaseType result = FileNameCaseType.ofString(string);
        assertEquals(expResult, result);
    }

    /**
     * Test of ofString method, of class FileNameCaseType.
     */
    @org.junit.jupiter.api.Test
    public void ofStringExpectedThrowingException() {
        String string = "BlupI000.blp";
        assertThrows(OpenEggbertException.class, () -> {
            FileNameCaseType.ofString(string);
        });
    }

    /**
     * Test of ofString method, of class FileNameCaseType.
     */
    @org.junit.jupiter.api.Test
    public void ofStringExpectedThrowingException2() {
        String string = "Blupi000.Blp";
        assertThrows(OpenEggbertException.class, () -> {
            FileNameCaseType.ofString(string);
        });
    }

    /**
     * Test of ofString method, of class FileNameCaseType.
     */
    @org.junit.jupiter.api.Test
    public void ofStringExpectedThrowingException3() {
        String string = "Blupi000.BLP";
        assertThrows(OpenEggbertException.class, () -> {
            FileNameCaseType.ofString(string);
        });
    }

    /**
     * Test of convertToString method, of class FileNameCaseType.
     */
    @Test
    public void testConvertToString() {
        System.out.println("convertToString");
        String string = "BLUPI000.BLP";
        FileNameCaseType fileNameCaseType = FileNameCaseType.LOWERCASE;
        String expResult = "blupi000.blp";
        String result = FileNameCaseType.convertToString(string, fileNameCaseType);
        assertEquals(expResult, result);
    }

    /**
     * Test of convertToString method, of class FileNameCaseType.
     */
    @Test
    public void testConvertToString2() {
        System.out.println("convertToString");
        String string = "blupi000.blp";
        FileNameCaseType fileNameCaseType = FileNameCaseType.UPPERCASE;
        String expResult = "BLUPI000.BLP";
        String result = FileNameCaseType.convertToString(string, fileNameCaseType);
        assertEquals(expResult, result);
    }

    /**
     * Test of convertToString method, of class FileNameCaseType.
     */
    @Test
    public void testConvertToString3() {
        System.out.println("convertToString");
        String string = "blupi000.blp";
        FileNameCaseType fileNameCaseType = FileNameCaseType.CAPITALIZATION;
        String expResult = "Blupi000.blp";
        String result = FileNameCaseType.convertToString(string, fileNameCaseType);
        assertEquals(expResult, result);
    }

    /**
     * Test of sortStringsByFileNameCaseType method, of class FileNameCaseType.
     */
    @Test
    public void testSortStringsByFileNameCaseType() {
        System.out.println("testSortStringsByFileNameCaseType");
        GameFileType gameFileType = GameFileType.SOUND;
        String fileName = "BLUPI";
        String string = "BLUPI.BLP\n"
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
        String expResult = "BLUPI.BLP\n"
                + "BLUPI.WAV\n"
                + "BLUPI.MP3\n"
                + "BLUPI.OGG\n"
                + "blupi.blp\n"
                + "blupi.wav\n"
                + "blupi.mp3\n"
                + "blupi.ogg\n"
                + "Blupi.blp\n"
                + "Blupi.wav\n"
                + "Blupi.mp3\n"
                + "Blupi.ogg";

        List<String> list = OpenEggbertUtils.createPossibleFileNames(gameFileType, fileName);
        FileNameCaseType.sortStringsByFileNameCaseType(list);
        String result = list.stream().collect(Collectors.joining("\n"));

        assertEquals(expResult, result);
    }

}
