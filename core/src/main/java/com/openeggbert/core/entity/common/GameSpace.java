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

import com.openeggbert.core.compatibility.Release;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author robertvokac
 */
@Data
@AllArgsConstructor
public class GameSpace {

    private boolean embeddedAssets;
    private Release featureLevel;
    private String dataDirectory;
    private String image08Directory;
    private String image16Directory;
    private String image24Directory;
    private String image24x2Directory;
    private String soundDirectory;
    private String currentDirectory;

    public GameSpace() {

    }


    public boolean isValid() {
        if (featureLevel == null) {
            return false;
        }
        if (dataDirectory == null) {
            return false;
        }
        if (image08Directory == null && image16Directory == null && image24Directory == null && image24x2Directory == null) {
            return false;
        }
        if (soundDirectory == null) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("featureLevel=" + featureLevel).append(" ");
        sb.append("data=" + dataDirectory).append(" ");
        sb.append("image08=" + image08Directory).append(" ");
        sb.append("image16=" + image16Directory).append(" ");
        sb.append("image24=" + image24Directory).append(" ");
        sb.append("image24x2=" + image24x2Directory).append(" ");
        sb.append("image24x2=" + image24x2Directory).append(" ");
        sb.append("sound=" + soundDirectory);
        return sb.toString();
    }
}
