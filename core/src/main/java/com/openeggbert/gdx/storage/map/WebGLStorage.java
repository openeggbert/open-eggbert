///////////////////////////////////////////////////////////////////////////////////////////////
// Gdx Storage: Multiplatform persistent storage.
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
package com.openeggbert.gdx.storage.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.openeggbert.core.fbox.entity.Platform;

/**
 *
 * @author robertvokac
 */
public class WebGLStorage extends MapStorage {
    public Platform getPlatform() {
        return Platform.WEB;
    }
    public WebGLStorage() {
        this("open-eggbert.webGL.Local-Storage");
    }
    public WebGLStorage(String preferencesName) {
        this(Gdx.app.getPreferences(preferencesName));
    }
        public WebGLStorage(Preferences preferences) {
        super(new SimpleLocalStorageMap(preferences));
    }

}
