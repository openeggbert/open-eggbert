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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author robertvokac
 */
public class SimpleJavaMap implements SimpleMap {

    private final Map<String, String> map;

    public SimpleJavaMap() {
        this(new HashMap<>());
    }
    public SimpleJavaMap(Map<String, String> mapIn) {
        this.map = mapIn;
    }

    @Override
    public void putString(String key, String val) {
        map.put(key, val);
    }

    @Override
    public void put(Map<String, String> map) {
        for (String key : map.keySet()) {
            putString(key, map.get(key));
        }
    }

    @Override
    public String getString(String key) {
        return map.get(key);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return contains(key) ? getString(key) : defaultValue;
    }

    @Override
    public Map<String, String> getReadOnlyMap() {
        return Collections.unmodifiableMap(map);
    }

    @Override
    public boolean contains(String key) {
        return map.containsKey(key);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public void remove(String key) {
        map.remove(key);
    }

    @Override
    public void flush() {
        //nothing to do
    }
    @Override
    public List<String> keyList() {
        return map.keySet().stream().collect(Collectors.toList());
    }
}
