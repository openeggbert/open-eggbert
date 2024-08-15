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
import java.util.Map;
import java.util.function.Function;
import lombok.Getter;

/**
 *
 * @author robertvokac
 */
@Getter
public class SpriteSheetRow {

    private static final String COLONCOLON = ";;";

    private String file;
    private String group;
    private int numberInGroup;
    private int row;
    private int column;
    private int x;
    private int y;
    private int width;
    private int height;
    private String notes;
    private String tags;
    private int numberPerSheet;
    
    public static String createId(SpriteSheetRow row) {
        return createId(row.getGroup(), row.getNumberInGroup());
    }
    
    public static String createId(String group, int numberInGroup) {
        return group + COLONCOLON + numberInGroup;
    }

    public String createId() {
        return createId(this);
    }

    public SpriteSheetRow(String line, Map<String, Integer> columnIndexesForNames) {
        String[] columns = line.split(SpriteSheet.DELIMITER);
        Function<SpriteSheetRowColumn, String> findString = c -> columnIndexesForNames.containsKey(c.getColumnName()) ? columns[columnIndexesForNames.get(c.getColumnName())] : "";
        Function<SpriteSheetRowColumn, Integer> findInt = c -> {
            String s = findString.apply(c);
            if (s.isEmpty()) {
                throw new OpenEggbertException("Missing value for column: " + c.getColumnName());
            };
            return Integer.valueOf(s);
        };

        file = findString.apply(SpriteSheetRowColumn.FILE);
        group = findString.apply(SpriteSheetRowColumn.GROUP);
        numberInGroup = findInt.apply(SpriteSheetRowColumn.NUMBER_IN_GROUP);
        row = findInt.apply(SpriteSheetRowColumn.ROW);
        column = findInt.apply(SpriteSheetRowColumn.COLUMN);
        x = findInt.apply(SpriteSheetRowColumn.X);
        y = findInt.apply(SpriteSheetRowColumn.Y);
        width = findInt.apply(SpriteSheetRowColumn.WIDTH);
        height = findInt.apply(SpriteSheetRowColumn.HEIGHT);
        notes = findString.apply(SpriteSheetRowColumn.NOTES);
        tags = findString.apply(SpriteSheetRowColumn.TAGS);
        int numberPerSheet = findInt.apply(SpriteSheetRowColumn.FILE);
        if (file.isEmpty()) {
            throw new OpenEggbertException("Missing mandatory value for column: " + SpriteSheetRowColumn.FILE);
        }
        if (group.isEmpty()) {
            throw new OpenEggbertException("Missing mandatory value for column: " + SpriteSheetRowColumn.GROUP);
        }
    }
}
