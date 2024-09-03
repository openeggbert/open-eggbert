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

import java.util.List;
import java.util.Map;

/**
 *
 * @author robertvokac
 */
public interface SimpleMap {

    public void putString(String key, String val);

    public void put(Map<String, String> map);

    public String getString(String key);

    public String getString(String key, String defaultValue);

    public Map<String, String> getReadOnlyMap();

    public boolean contains(String key);

    public void clear();

    public void remove(String key);

    public void flush();
    
    public List<String> keyList();
}
