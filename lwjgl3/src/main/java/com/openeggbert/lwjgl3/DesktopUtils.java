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
package com.openeggbert.lwjgl3;

import com.openeggbert.compatibility.FeatureLevel;
import com.openeggbert.entity.common.GameSpace;
import com.openeggbert.entity.common.OpenEggbertException;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 *
 * @author robertvokac
 */
public class DesktopUtils {

    private DesktopUtils() {
        //Not meant to be instantiated.
    }

    public static Optional<GameSpace> tryToLoadGameSpace() {
        String gameSpaceDirectoryFromEnvironmentVariable = System.getenv().getOrDefault("GAME_SPACE_DIRECTORY", "");
        String gameSpaceDirectoryFromSystemProperty = System.getProperty("GAME_SPACE_DIRECTORY", "");
        
        if (!gameSpaceDirectoryFromEnvironmentVariable.isBlank()) {
            return tryToLoadGameSpaceFromEnvironmentVariable(gameSpaceDirectoryFromEnvironmentVariable);
        }        
        if (!gameSpaceDirectoryFromSystemProperty.isBlank()) {
            return tryToLoadGameSpaceFromEnvironmentVariable(gameSpaceDirectoryFromSystemProperty);
        }
        Optional<GameSpace> gameOptional = tryToLoadGameSpaceFromCurrentDirectory();
        return gameOptional;
    }

    private static Optional<GameSpace> tryToLoadGameSpaceFromEnvironmentVariable(String gameSpaceDirectoryFromEnvironmentVariable) {

        File gameSpaceDirectory = new File(gameSpaceDirectoryFromEnvironmentVariable);

        Optional<GameSpace> gameSpace = tryToLoadGameSpaceFromDirectory(gameSpaceDirectory);
        return gameSpace;

    }

    private static Optional<GameSpace> tryToLoadGameSpaceFromDirectory(File gameSpaceDirectory) throws OpenEggbertException {
        if (!gameSpaceDirectory.exists()) {
            throw new OpenEggbertException("Directory does not exist: " + gameSpaceDirectory.getAbsolutePath());
        }
        FeatureLevel featureLevel = null;
        try {
            featureLevel = findFeatureLevelFromDirectory(gameSpaceDirectory);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        if (featureLevel == null) {
            return Optional.empty();
        }
        GameSpace gameSpace = new GameSpace();
        gameSpace.setCurrenteDirectory(new File(".").getAbsolutePath());
        gameSpace.setFeatureLevel(featureLevel);
        if (featureLevel == FeatureLevel.SPEEDY_BLUPI_DEMO) {
            gameSpace.setDataDirectory(gameSpaceDirectory.getAbsolutePath() + "/" + "Data");
            gameSpace.setImage08Directory(gameSpaceDirectory.getAbsolutePath() + "/" + "Image");
            gameSpace.setSoundDirectory(gameSpaceDirectory.getAbsolutePath() + "/" + "Sound");

            throwExceptionIfDirectoryDoesNotExist(gameSpace.getDataDirectory());
            throwExceptionIfDirectoryDoesNotExist(gameSpace.getImage08Directory());
            throwExceptionIfDirectoryDoesNotExist(gameSpace.getSoundDirectory());
            return Optional.of(gameSpace);
        }
        gameSpace.setDataDirectory(gameSpaceDirectory.getAbsolutePath() + "/" + "DATA");
        throwExceptionIfDirectoryDoesNotExist(gameSpace.getDataDirectory());
        File image08Directory = new File(gameSpaceDirectory, "IMAGE08");
        File image16Directory = new File(gameSpaceDirectory, "IMAGE16");
        File soundDirectory = new File(gameSpaceDirectory, "SOUND");
        gameSpace.setImage08Directory(image08Directory.getAbsolutePath());
        gameSpace.setSoundDirectory(soundDirectory.getAbsolutePath());

        if (featureLevel != FeatureLevel.SPEEDY_EGGBERT_DEMO) {
            gameSpace.setImage16Directory(image16Directory.getAbsolutePath());
        }

        if (featureLevel != FeatureLevel.OPEN_EGGBERT_3) {
            throwExceptionIfDirectoryDoesNotExist(image08Directory);
            if (featureLevel != FeatureLevel.SPEEDY_EGGBERT_DEMO) {
                throwExceptionIfDirectoryDoesNotExist(image16Directory);
            }
            throwExceptionIfDirectoryDoesNotExist(soundDirectory);
            return Optional.of(gameSpace);
        }
        if (featureLevel == FeatureLevel.OPEN_EGGBERT_3) {

            if (!image08Directory.exists()) {
                image08Directory = null;
            }
            if (!image16Directory.exists()) {
                image16Directory = null;
            }
            File image24Directory = new File(gameSpaceDirectory, "IMAGE24");
            if (image24Directory.exists()) {
                gameSpace.setImage24Directory(image24Directory.getAbsolutePath());
            }

            File image24x2Directory = new File(gameSpaceDirectory, "IMAGE24X2");
            if (image24x2Directory.exists()) {
                gameSpace.setImage24x2Directory(image24x2Directory.getAbsolutePath());
            }
            if (gameSpace.getImage08Directory() == null
                    && gameSpace.getImage16Directory() == null
                    && gameSpace.getImage24Directory() == null
                    && gameSpace.getImage24x2Directory() == null) {
                throw new OpenEggbertException("Fatal error. At least one IMAGE* directory must exist in directory: " + gameSpaceDirectory.getAbsolutePath());
            }
            return Optional.of(gameSpace);
        }
        return Optional.empty();
    }

    private static Optional<GameSpace> tryToLoadGameSpaceFromCurrentDirectory() {
        try {
            return tryToLoadGameSpaceFromDirectory(new File(DesktopUtils.getPathToDirectoryWhereJarIsRunning()));

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }

    private static void throwExceptionIfDirectoryDoesNotExist(File directory) {

        if (!directory.exists()) {
            throw new OpenEggbertException("Directory does not exist: " + directory.getAbsolutePath());
        }
    }

    private static void throwExceptionIfDirectoryDoesNotExist(String directoryString) {
        throwExceptionIfDirectoryDoesNotExist(new File(directoryString));
    }

    public static FeatureLevel findFeatureLevelFromDirectory(File dir) {
        final File image24Directory = new File(dir, "IMAGE24");
        final File image24x2Directory = new File(dir, "IMAGE24x2");
        if (image24Directory.exists() && image24x2Directory.exists()) {
            return FeatureLevel.OPEN_EGGBERT_3;
        }
        if (new File(dir, "Data").exists() && new File(dir, "Image").exists() && new File(dir, "Sound").exists()) {
            return FeatureLevel.SPEEDY_BLUPI_DEMO;
        }
        if (!new File(dir, "DATA").exists()) {
            throw new OpenEggbertException("Directory does not exist: " + new File(dir, "DATA").getAbsolutePath());
        }
        final File image08Directory = new File(dir, "IMAGE08");
        if (image08Directory.exists()) {
            if (new File(image08Directory, "INSERT.BLP").exists()) {
                //blupi
                if (new File(image08Directory, "DECOR024.BLP").exists()) {
                    return FeatureLevel.SPEEDY_BLUPI_II;
                } else {
                    return FeatureLevel.SPEEDY_BLUPI_I;
                }
            } else {
                //eggbert
                final File image16Directory = new File(dir, "IMAGE16");
                if (!image16Directory.exists()) {
                    return FeatureLevel.SPEEDY_EGGBERT_DEMO;
                }
                if (new File(image08Directory, "DECOR024.BLP").exists() || new File(image08Directory, "decor024.blp").exists()) {
                    return FeatureLevel.SPEEDY_EGGBERT_2;
                } else {
                    return FeatureLevel.SPEEDY_EGGBERT_1;
                }
            }
        }
        throw new OpenEggbertException("Directory is not compatible with any supported version: " + dir.getAbsolutePath());
    }

    public static String getPathToDirectoryWhereJarIsRunning() {
        try {
            return new File(DesktopUtils.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getParentFile().getAbsolutePath();
        } catch (URISyntaxException ex) {
            throw new OpenEggbertException(ex.getMessage());
        }

    }
}
