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
package com.openeggbert.core.mod;

import com.openeggbert.core.fbox.api.XmlElement;
import com.openeggbert.core.fbox.core.FBox;
import com.openeggbert.core.release.Release;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author robertvokac
 */
@Data
public class Mod {

    public Mod(String xml) {
        XmlElement root = FBox.get().utils().parseXml(xml);
        XmlElement parentElement = root.getChildByName("parent");
        if (parentElement != null) {
            parent = new ModIdentification(parentElement);
        }
        identification = new ModIdentification(
                root.get("groupId"),
                root.get("modId"),
                root.get("version"));
        modPackaging = ModPackaging.valueOf(root.get("packaging"));
        modType = ModType.valueOf(root.get("type"));
        featureLevel = Release.valueOf(root.get("featureLevel"));
        name = root.get("name");
        description = root.get("description");
        XmlElement imports = root.getChildByName("imports");
        if (imports != null) {
            for (int i = 0; i < imports.getChildCount(); i++) {
                XmlElement import_ = imports.getChild(i);
                importedMods.add(new ModIdentification(import_));
            }
        }
        XmlElement files_ = root.getChildByName("files");
        if (files_ != null) {
            for (int i = 0; i < files_.getChildCount(); i++) {
                XmlElement file = files_.getChild(i);
                files.add(file.getText());
            }
        }

        XmlElement stores_ = root.getChildByName("stores");
        if (stores_ != null) {
            for (int i = 0; i < stores_.getChildCount(); i++) {
                XmlElement store = stores_.getChild(i);
                stores.add(new Store(store));
            }
        }

    }
    private ModIdentification parent;
    private ModIdentification identification;
    private ModPackaging modPackaging;
    private ModType modType;
    private Release featureLevel;
    private String name;
    private String description;
    private List<ModIdentification> importedMods = new ArrayList<>();
    private List<String> files = new ArrayList<>();
    private List<Store> stores = new ArrayList<>();
}
