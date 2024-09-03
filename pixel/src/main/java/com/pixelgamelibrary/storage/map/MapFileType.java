///////////////////////////////////////////////////////////////////////////////////////////////
// Pixel: Game library.
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
package com.pixelgamelibrary.storage.map;

import com.pixelgamelibrary.storage.StorageException;


/**
 *
 * @author robertvokac
 */
public enum MapFileType {
    FILE, DIRECTORY;

    public static MapFileType ofKey(String key, SimpleMap map) {
        if (!map.contains(key)) {
            throw new StorageException("Map does not contain key: " + key);
        }
        String value = map.getString(key);
        if (value.startsWith(FILE.name())) {
            return FILE;
        }
        if (value.startsWith(DIRECTORY.name())) {
            return DIRECTORY;
        }
        throw new StorageException("Unsupported MapFileType for key in the map: " + key);

    }

}
