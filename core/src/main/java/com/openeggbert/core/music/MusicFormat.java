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
package com.openeggbert.core.music;

import com.openeggbert.core.configuration.StrictMode;
import lombok.Getter;

/**
 *
 * @author robertvokac
 */
public enum MusicFormat implements StrictMode {
    BLP("blp", true),
    MIDI("mid", false),
    WAV("wav", false),
    MP3("mp3", false),
    OGG("ogg", false),
    ;
    @Getter
    private String fileExtension;
    
    @Getter
    private boolean enabledInCaseOfStrictMode;
    MusicFormat(String fileExtension, boolean enabledInCaseOfStrictMode) {
        this.fileExtension = fileExtension;
        this.enabledInCaseOfStrictMode = enabledInCaseOfStrictMode;
    }

    @Override
    public boolean isEnabledInCaseOfStrictMode() {
        return enabledInCaseOfStrictMode;
    }
    
    public MusicFormat getTargetFormat() {
        return this == BLP ? MIDI : this;
    }
}
