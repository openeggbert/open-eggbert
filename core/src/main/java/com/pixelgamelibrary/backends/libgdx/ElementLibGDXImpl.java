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
package com.pixelgamelibrary.backends.libgdx;

import com.badlogic.gdx.utils.XmlReader;
import com.pixelgamelibrary.api.XmlElement;

/**
 *
 * @author robertvokac
 */
public class ElementLibGDXImpl implements XmlElement {

    private final XmlReader.Element element;

    public ElementLibGDXImpl(com.badlogic.gdx.utils.XmlReader.Element element) {
        this.element = element;
    }

    @Override
    public XmlElement getChildByName(String name) {
        XmlReader.Element child = element.getChildByName(name);
        return child == null ? null : new ElementLibGDXImpl(child);
    }

    @Override
    public String get(String elementName) {
        return element.get(elementName);
    }

    @Override
    public int getChildCount() {
        return element.getChildCount();
    }

    @Override
    public XmlElement getChild(int i) {
        XmlReader.Element child = element.getChild(i);
        return child == null ? null : new ElementLibGDXImpl(child);    }

    @Override
    public String getText() {
        return element.getText();
    }
}
