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


package com.openeggbert.core.screen;

import lombok.Getter;

/**
 *
 * @author robertvokac
 */
public enum ScreenType {
    MAIN,
    GAME_SPACE_LIST("BASIC"),
    GAME_SPACE_CREATE("BASIC"),
    GAME_SPACE_RENAME("BASIC"),
    GAME_SPACE_DELETE("BASIC"),
    GAME_SPACE_RESET("BASIC"),
    GAME_SPACE_EDIT("BASIC"),
    GAME_SPACE_CONFIGURE("BASIC"),
    GAME_SPACE_CONFIGURE_ENTRY("BASIC"),
    GAME_SPACE_SELECT_MODE("BASIC"),
    //
    MOD_LIST,
    MOD_VIEW,
    //
    INIT("INIT"),
    GAMER("GAMER"),
    MAIN_HUB(""),
    SUB_HUB(""),
    GAME(""),
    EDITOR(""),
    EDITOR_DEMO(""),
    DEMO("DECOR016"),//todo fix me
    ;
    
    @Getter
    private String fileNameWithoutExtension;
    
    ScreenType() {
        this.fileNameWithoutExtension = "";
    }
    ScreenType(String fileName) {
        this.fileNameWithoutExtension = fileName;
    }
    public boolean isBasic() {
        return name().startsWith("GAME_SPACE") || name().startsWith("MOD");
    }
    
}
