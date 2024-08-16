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
package com.openeggbert.core.release;

import com.openeggbert.core.gamespace.GameDirectoryType;
import com.openeggbert.core.gamespace.ImageDirectories;
import com.openeggbert.core.utils.FileNameCaseType;
import lombok.Getter;

/**
 *
 * @author robertvokac
 */
public enum Release {
    SPEEDY_BLUPI_DEMO(ReleaseType.BLUPI, ReleaseVersion.DEMO, Publisher.EPSITEC_SA, "SBD", FileNameCaseType.CAPITALIZATION,  new ImageDirectories(GameDirectoryType.IMAGE, null, null, null)),
    SPEEDY_BLUPI_I(ReleaseType.BLUPI, ReleaseVersion.ONE, Publisher.EPSITEC_SA, "SBI"),
    SPEEDY_BLUPI_II(ReleaseType.BLUPI, ReleaseVersion.TWO, Publisher.EPSITEC_SA, "SBII"),
    SPEEDY_EGGBERT_DEMO(ReleaseType.EGGBERT, ReleaseVersion.DEMO, Publisher.E_GAMES, "SED"),
    SPEEDY_EGGBERT_1(ReleaseType.EGGBERT, ReleaseVersion.ONE, Publisher.E_GAMES, "SE1"),
    SPEEDY_EGGBERT_2(ReleaseType.EGGBERT, ReleaseVersion.TWO, Publisher.E_GAMES, "SE2"),
    SPEEDY_EGGBERT_VALUEWARE(ReleaseType.EGGBERT, ReleaseVersion.VALUEWARE, Publisher.E_GAMES, "SEW"),
    SPEEDY_BLUPI_FOR_WINDOWS_PHONE(ReleaseType.BLUPI, ReleaseVersion.WINDOWS_PHONE, Publisher.DADA_GAMES, "SBP"),
    OPEN_EGGBERT_3(ReleaseType.OPEN, ReleaseVersion.THREE, Publisher.OPEN_EGGBERT, "OE3", FileNameCaseType.UPPERCASE, new ImageDirectories(GameDirectoryType.IMAGE08, GameDirectoryType.IMAGE16, GameDirectoryType.IMAGE24, GameDirectoryType.IMAGE24X2));
    @Getter
    private final ReleaseType releaseType;
    @Getter
    private final ReleaseVersion releaseVersion;
    @Getter
    private final Publisher publisher;
    @Getter
    private final String abbreviation;
    @Getter
    private final ImageDirectories imageDirectories;

    @Getter
    public static final Release[] ALL_RELEASES = Release.values();
    @Getter
    private FileNameCaseType caseModeForDirectories;
    private Release(ReleaseType releaseType, ReleaseVersion releaseVersion, Publisher publisher, String abbreviation) {
        this(releaseType, releaseVersion, publisher, abbreviation, FileNameCaseType.UPPERCASE, ImageDirectories.DEFAULT);
    }

    private Release(ReleaseType releaseType, ReleaseVersion releaseVersion, Publisher publisher, String abbreviation, FileNameCaseType caseModeForDirectories, ImageDirectories imageDirectories) {
        this.releaseType = releaseType;
        this.releaseVersion = releaseVersion;
        this.publisher = publisher;
        this.abbreviation = abbreviation;
        this.caseModeForDirectories = caseModeForDirectories;
        this.imageDirectories = imageDirectories;
    }
    
    public String createLabel() {
        String[] array = this.name().split(UNDERSCORE);
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < array.length; i++) {
            sb.append(FileNameCaseType.convertToString(array[i], FileNameCaseType.CAPITALIZATION));
            if(i < (array.length - 1)) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
    private static final String UNDERSCORE = "_";

}
