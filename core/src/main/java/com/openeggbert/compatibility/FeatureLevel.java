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
public enum FeatureLevel {
    SPEEDY_BLUPI_DEMO(ReleaseType.BLUPI, ReleaseVersion.DEMO),
    SPEEDY_BLUPI_I(ReleaseType.BLUPI, ReleaseVersion.ONE),
    SPEEDY_BLUPI_II(ReleaseType.BLUPI, ReleaseVersion.TWO),
    SPEEDY_EGGBERT_DEMO(ReleaseType.EGGBERT, ReleaseVersion.DEMO),
    SPEEDY_EGGBERT_1(ReleaseType.EGGBERT, ReleaseVersion.ONE),
    SPEEDY_EGGBERT_2(ReleaseType.EGGBERT, ReleaseVersion.TWO),
    OPEN_EGGBERT_3(ReleaseType.OPEN, ReleaseVersion.THREE);
    @Getter
    private final ReleaseType releaseType;
    @Getter
    private final ReleaseVersion releaseVersion;

    private FeatureLevel(ReleaseType releaseType, ReleaseVersion releaseVersion) {
        this.releaseType = releaseType;
        this.releaseVersion = releaseVersion;
    }

}
