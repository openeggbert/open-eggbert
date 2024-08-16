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
package com.openeggbert.core.utils;

import com.openeggbert.core.main.OpenEggbertException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author robertvokac
 */
public enum FileNameCaseType {
    UPPERCASE,
    LOWERCASE,
    CAPITALIZATION;

    public static String convertToString(String string, FileNameCaseType fileNameCaseType) {
        if (fileNameCaseType == null) {
            throw new OpenEggbertException("Argument fileNameCaseType is null, which is forbidden");
        }
        if (string == null) {
            return string;
        }
        if (string.isEmpty()) {
            return string;
        }
        switch (fileNameCaseType) {
            case UPPERCASE:
                return string.toUpperCase();
            case LOWERCASE:
                return string.toLowerCase();
            case CAPITALIZATION:
                return Character.toUpperCase(string.charAt(0)) + (string.length() == 1 ? "" : string.substring(1).toLowerCase());
            default:
                throw new OpenEggbertException("Unsupported FileNameCaseType: " + fileNameCaseType);
        }

    }

    public static FileNameCaseType ofString(String string) {

        boolean uppercaseOnly = true;
        boolean lowercaseOnly = true;

        for (char ch : string.toCharArray()) {

            if (Character.isLetter(ch)) {
                boolean uppercase = Character.isUpperCase(ch);
                boolean lowercase = Character.isLowerCase(ch);

                if (uppercase) {
                    lowercaseOnly = false;
                }
                if (lowercase) {
                    uppercaseOnly = false;
                }
            }
        }
        if (uppercaseOnly) {
            return UPPERCASE;
        }
        if (lowercaseOnly) {
            return LOWERCASE;
        }
        if (!uppercaseOnly && !lowercaseOnly) {
            char firstChar = string.charAt(0);
            char[] charArray = string.toCharArray();
            List<Character> charList = new ArrayList<>();
            for (Character ch : charArray) {
                charList.add(ch);
            }
            Stream<Character> charStream = charList.stream();

            boolean thereIsNoUppercaseCharacterExcludingTheFirstCharacter
                    = charStream
                            .skip(1)
                            .filter(c -> Character.isLetter(c))
                            .filter(c -> Character.isUpperCase(c))
                            .count() == 0;
            if ((Character.isLetter(firstChar) ? Character.isUpperCase(firstChar) : true)
                    && thereIsNoUppercaseCharacterExcludingTheFirstCharacter) {
                return CAPITALIZATION;
            } else {
                throw new OpenEggbertException("Could not find FileNameCaseType from String: " + string);
            }

        }
        throw new OpenEggbertException("Could not find FileNameCaseType from String: " + string);

    }

    public static void sortStringsByFileNameCaseType(List<String> list) {
        Collections.sort(list,
                new FileNameCaseTypeStringComparator());
    }

    private static class FileNameCaseTypeStringComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            FileNameCaseType t1 = FileNameCaseType.ofString(o1);
            FileNameCaseType t2 = FileNameCaseType.ofString(o2);
            int i1 = t1 == UPPERCASE ? 1 : (t1 == LOWERCASE ? 2 : 3);
            int i2 = t2 == UPPERCASE ? 1 : (t2 == LOWERCASE ? 2 : 3);
            return Integer.valueOf(i1).compareTo(i2);
        }

    }

}
