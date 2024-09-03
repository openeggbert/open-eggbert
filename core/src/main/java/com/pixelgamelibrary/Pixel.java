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
package com.pixelgamelibrary;

import com.pixelgamelibrary.api.PixelBackend;

/**
 *
 * @author robertvokac
 */
public class Pixel {

    private static PixelBackend INSTANCE = null;

    private Pixel() {
        //Not meant to be instantiated.
    }

    public static PixelBackend get() {
        return getBackend();
    }

    public static void setBackend(PixelBackend pixelBackend) {
        if (isBackendSet()) {
            throw new PixelException("Pixel Backend was already set");
        }
        INSTANCE = pixelBackend;
    }

    public static PixelBackend getBackend() {
        if (!isBackendSet()) {
            throw new PixelException("Pixel Backend was not set");
        }
        return INSTANCE;
    }

    public static boolean isBackendSet() {
        return INSTANCE != null;
    }
}
