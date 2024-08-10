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
package com.openeggbert.storage;

import com.badlogic.gdx.Application;
import java.util.List;

/**
 *
 * @author robertvokac
 */
public interface Storage {

    Application.ApplicationType getApplicationType();

    public String cd(String path);

    default String cd() {
        mkdir("home");
        cd("home");
        mkdir("openeggbert");
        cd("openeggbert");
        return "";
    }

    public String mkdir(String name);
    default String mkdir(String... name) {
        
        for(String n:name) {
            String result = mkdir(n);
            if(!result.isEmpty()) {
                return result;
            }
        }
        return "";
    }

    public String pwd();

    public List<String> ls(String workingDirectory);

    default List<String> ls() {
        return ls(pwd());
    }

    public int depth(String path);

    default int depth() {
        return depth(pwd());
    }

    public String touch(String name);

    public boolean rm(String name);

    public String cp(String source, String target);

    public String mv(String source, String target);

    public String readtext(String name);

    public byte[] readbin(String name);

    public String savetext(String name, String text);

    public String savebin(String name, byte[] data);

    public boolean exists(String name);

    public boolean isfile(String name);

    public boolean isdir(String name);

    public String debug();
    
    public void flush();

}
