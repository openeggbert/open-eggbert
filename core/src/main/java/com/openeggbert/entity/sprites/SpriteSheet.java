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
package com.openeggbert.entity.sprites;

import com.openeggbert.entity.common.OpenEggbertException;
import com.openeggbert.utils.OpenEggbertUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author robertvokac
 */
public class SpriteSheet {

    public static final String DELIMITER = ";";

    private final Map<String, Integer> columnIndexesForNames = new HashMap<>();
    private final Map<String, SpriteSheetRow> rowMap = new HashMap<>();

    public SpriteSheet(String csv) {
        //List<SpriteSheetRow> rows = new ArrayList<>();
        List<String> lines = Arrays.asList(csv.split("\n"));

        String[] header = lines.get(0).split(DELIMITER);
        for (int i = 0; i < header.length; i++) {
            String columnName = header[i];
            if (columnIndexesForNames.containsKey(columnName)) {
                throw new OpenEggbertException("Invalid sprite sheet. It has invalid the first row (column names). Column is more than once: " + columnName);
            }
            Optional<SpriteSheetRowColumn> optionalSpriteSheetRowColumn = Arrays.asList(com.openeggbert.entity.sprites.SpriteSheetRowColumn.values()).stream().filter(r -> r.getColumnName().equals(columnName)).findFirst();
            if (!optionalSpriteSheetRowColumn.isPresent()) {
                continue;
            }
            columnIndexesForNames.put(columnName, i);
        }

        lines.stream().skip(1)
                .forEach(line -> {
                    SpriteSheetRow row = new SpriteSheetRow(line, columnIndexesForNames);
                    //rows.add(row);
                    rowMap.put(row.createId(), row);
                });
    }
    public SpriteSheetRow findSpriteSheetRow(SpriteGroup group, int numberInGroup) {
        String id = SpriteSheetRow.createId(group.name(), numberInGroup);
        SpriteSheetRow row = rowMap.get(id);
        
        return row;
    }
}
