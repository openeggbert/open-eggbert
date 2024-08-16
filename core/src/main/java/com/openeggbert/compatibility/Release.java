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

import lombok.Getter;

/**
 *
 * @author robertvokac
 */
public enum Release {
    SPEEDY_BLUPI_DEMO(ReleaseType.BLUPI, ReleaseVersion.DEMO, Publisher.EPSITEC_SA),
    SPEEDY_BLUPI_I(ReleaseType.BLUPI, ReleaseVersion.ONE, Publisher.EPSITEC_SA),
    SPEEDY_BLUPI_II(ReleaseType.BLUPI, ReleaseVersion.TWO, Publisher.EPSITEC_SA),
    SPEEDY_EGGBERT_DEMO(ReleaseType.EGGBERT, ReleaseVersion.DEMO, Publisher.E_GAMES),
    SPEEDY_EGGBERT_1(ReleaseType.EGGBERT, ReleaseVersion.ONE, Publisher.E_GAMES),
    SPEEDY_EGGBERT_2(ReleaseType.EGGBERT, ReleaseVersion.TWO, Publisher.E_GAMES),
    SPEEDY_EGGBERT_VALUEWARE(ReleaseType.EGGBERT, ReleaseVersion.VALUEWARE, Publisher.E_GAMES),
    SPEEDY_BLUPI_FOR_WINDOWS_PHONE(ReleaseType.BLUPI, ReleaseVersion.WINDOWS_PHONE, Publisher.DADA_GAMES),
    OPEN_EGGBERT_3(ReleaseType.OPEN, ReleaseVersion.THREE, Publisher.OPEN_EGGBERT);
    @Getter
    private final ReleaseType releaseType;
    @Getter
    private final ReleaseVersion releaseVersion;

    private Release(ReleaseType releaseType, ReleaseVersion releaseVersion, Publisher publisher) {
        this.releaseType = releaseType;
        this.releaseVersion = releaseVersion;
    }
    
    public String createLabel() {
        String[] array = this.name().split("_");
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < array.length; i++) {
            sb.append(FileNameCaseType.convertToString(array[i], FileNameCaseType.CAPITALIZATION));
            if(i < (array.length - 1)) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

}
