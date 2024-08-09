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
public enum MusicFormats {
    MIDI("blp", "mid"),
    MP3("mp3"),
    OGG("ogg"),
    WAV("wav")
    ;
    @Getter
    private String[] fileExtensions;
    @Getter
    private boolean openEggbertOnly;
    MusicFormats(String... fileExtensionsIn) {
        this(true, fileExtensionsIn);
    }
    MusicFormats(boolean openEggbertOnlyIn, String... fileExtensionsIn) {
        this.fileExtensions = fileExtensionsIn;
        this.openEggbertOnly = openEggbertOnlyIn;
    }
    
}
