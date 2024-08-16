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


package com.openeggbert.core.compatibility;

import lombok.Getter;

/**
 *
 * @author robertvokac
 */
public enum ScreenResolution implements StrictMode {
    VGA(640,480,true),
    QUAD_VGA(1280, 960, false),
    CURRENT(0, 0, false);
    @Getter
    private boolean enabledInCaseOfStrictMode;
    @Getter
    private int width;
    @Getter
    private int height;
    ScreenResolution(int width, int height, boolean enabledInCaseOfStrictMode) {
        this.width = width;
        this.height = height;
        this.enabledInCaseOfStrictMode = enabledInCaseOfStrictMode;
    }

    @Override
    public boolean isEnabledInCaseOfStrictMode() {
        return enabledInCaseOfStrictMode;
    }
}
