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

import com.openeggbert.core.image.ImageFormat;
import com.openeggbert.core.music.MusicFormat;
import com.openeggbert.core.sound.SoundFormat;
import com.openeggbert.core.gamespace.GameFileType;
import com.openeggbert.core.main.OpenEggbertException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author robertvokac
 */
public class OpenEggbertUtils {

    private OpenEggbertUtils() {
        //Not meant to be instantiated.
    }

    public static <T> List<T> streamToList(Stream<T> stream) {
        return stream.collect(Collectors.toList());
    }

    public static List<String> createPossibleFileNames(GameFileType gameFileType, String fileName) {
        List<String> list = new ArrayList<>();
        if (gameFileType.name().startsWith(IMAGE)) {
            String fileNameWithoutExtension = getFileNameWithoutExtension(fileName);

            for (ImageFormat imageFormat : ImageFormat.values()) {
                fillListWithPossibleFileNamesForGivenFileExtension(imageFormat.getFileExtension(), fileNameWithoutExtension, list);
            }
            return list;
        }

        if (gameFileType == GameFileType.MUSIC) {
            String fileNameWithoutExtension = getFileNameWithoutExtension(fileName);

            for (MusicFormat musicFormat : MusicFormat.values()) {
                fillListWithPossibleFileNamesForGivenFileExtension(musicFormat.getFileExtension(), fileNameWithoutExtension, list);
            }
            return list;
        }

        if (gameFileType == GameFileType.SOUND) {
            String fileNameWithoutExtension = getFileNameWithoutExtension(fileName);

            for (SoundFormat soundFormat : SoundFormat.values()) {
                fillListWithPossibleFileNamesForGivenFileExtension(soundFormat.getFileExtension(), fileNameWithoutExtension, list);
            }
            return list;
        }
        if (gameFileType == GameFileType.CONFIG
                || gameFileType == GameFileType.WORLD
                || gameFileType == GameFileType.DEMO
                || gameFileType == GameFileType.SAVE
                || gameFileType == GameFileType.USER_INFO) {
            for (FileNameCaseType fileNameCaseType : FileNameCaseType.values()) {
                list.add(FileNameCaseType.convertToString(fileName, fileNameCaseType));
            }
            return list;
        }
        throw new OpenEggbertException("Unsupported GameFileType: " + gameFileType);
    }

    private static void fillListWithPossibleFileNamesForGivenFileExtension(String fileExtension, String fileNameWithoutExtension, List<String> list) {

        String fileNameWithExtension = fileNameWithoutExtension + "." + fileExtension;
        for (FileNameCaseType fileNameCaseType : FileNameCaseType.values()) {
            list.add(FileNameCaseType.convertToString(fileNameWithExtension, fileNameCaseType));
        }

    }
    private static final String IMAGE = "IMAGE";

    public static String getFileNameWithoutExtension(String fileName) {
        if(!fileName.contains(".")) {
            return fileName;
        }
        int dotIndex = -1;
        for (int i = fileName.length() - 1; i >= 0; i--) {
            char ch = fileName.charAt(i);
            if (ch == '.') {
                dotIndex = i;
                break;
            }
        }
        return dotIndex == -1 ? fileName : fileName.substring(0, dotIndex);

    }

}
