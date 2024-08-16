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
package com.openeggbert.core.entity.common;

import com.openeggbert.core.utils.OpenEggbertUtils;
import java.util.HashMap;
import java.util.Map;
import lombok.ToString;

/**
 *
 * @author robertvokac
 */
@ToString
public class ConfigDef {

    private Map<String,String> map = new HashMap<>();

    public ConfigDef(String textContentofConfigDefFile) {
        OpenEggbertUtils
                .lines(textContentofConfigDefFile)
                .filter(l -> !l.trim().isEmpty())
                .filter(l -> !l.trim().startsWith(HASH_CHARACTER))
                .filter(l -> l.contains(EQUALS_CHARACTER))
                .forEach(line -> {
                    String[] array = line.split("=");
                    String key = array[0];
                    String value = array[1];
                    map.put(key, value);
                });
    }
    private static final String HASH_CHARACTER = "#";
    private static final String EQUALS_CHARACTER = "=";

    public boolean isFullscreen() {
        return getMandatoryBooleanProperty(ConfigDefKey.FULLSCREEN);

    }

    public boolean isStrictCompatibility() {
        return getOptionalBooleanProperty(ConfigDefKey.STRICT_COMPATIBILITY, false);

    }
    private boolean getMandatoryBooleanProperty(ConfigDefKey configDefKey) {
                String key = configDefKey.getKey();
        if (!map.containsKey(key)) {
            throw new OpenEggbertException("Fatal error: CONFIG.DEF is missing key: " + key);
        }
        return convertStringToBoolean(map.get(configDefKey.getKey()));
    }

    private boolean getOptionalBooleanProperty(ConfigDefKey configDefKey, boolean defaultValue) {
        String key = configDefKey.getKey();
        if (!map.containsKey(key)) {
            return defaultValue;
        }
        return getMandatoryBooleanProperty(configDefKey);
    }

    private static boolean convertStringToBoolean(String string) {
        if (string.equals("1")) {
            return true;
        }
        if (string.equals("0")) {
            return false;
        }
        throw new OpenEggbertException("Could not convert String to boolean: " + string);
    }

}
