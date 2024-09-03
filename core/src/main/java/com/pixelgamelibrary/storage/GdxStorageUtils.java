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
package com.pixelgamelibrary.storage;

import com.badlogic.gdx.utils.Base64Coder;

/**
 *
 * @author robertvokac
 */
public class GdxStorageUtils {
    
    private GdxStorageUtils() {
        //Not meant to be instantiated.
    }

        public static String decodeBase64AsString(String string) {
        return new String(decodeBase64AsByteArray(string));
    }

    public static byte[] decodeBase64AsByteArray(String string) {
        return Base64Coder.decode(string);
    }

    public static String encodeToBase64(String string) {
        return encodeToBase64(string.getBytes());
    }

    public static String encodeToBase64(byte[] data) {
        return String.valueOf(Base64Coder.encode(data));
    }

}
