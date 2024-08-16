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
package com.openeggbert.gdx.storage;

import com.openeggbert.gdx.storage.map.WebGLStorage;
import com.openeggbert.gdx.storage.map.MemoryStorage;
import com.openeggbert.gdx.storage.filesystem.AndroidStorage;
import com.openeggbert.gdx.storage.filesystem.DesktopStorage;
import com.openeggbert.core.fbox.core.FBox;
import com.openeggbert.core.fbox.entity.Platform;

/**
 *
 * @author robertvokac
 */
public class StorageImplementationLoader {

    private StorageImplementationLoader() {
        //Not meant to be instantiated.
    }
    private static Storage storage = null;

    public static Storage getStorage() {
        final Platform platform = FBox.get().getPlatform();
        if (storage == null) {
            storage = new MemoryStorage();
        }
        if (storage == null) {

            if (platform.isDesktop()) {
                storage = new DesktopStorage();
            }
            if (platform.isAndroid()) {
                storage = new AndroidStorage();
            }
            if (platform.isWeb()) {
                storage = new WebGLStorage();
            }
        }
        if (storage == null) {
            throw new GdxStorageException("Platform is not supported: " + platform);
        }
        return storage;
    }

}
