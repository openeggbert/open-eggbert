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
package com.pixelgamelibrary.storage.command;

import java.util.function.Function;

/**
 *
 * @author robertvokac
 */
public class BaseCommand implements StorageCommand {

    private StorageCommandLine storageCommandLine = null;
    private String name;
    private final Function<String, StorageCommandResult> function;

    public BaseCommand(
            StorageCommandLine storageCommandLineIn, String nameIn, Function<String, StorageCommandResult> functionIn
    ) {
        setStorageCommandLine(storageCommandLineIn);
        this.name = nameIn;
        this.function = functionIn;

    }

    @Override
    public final void setStorageCommandLine(StorageCommandLine storageCommandLineIn) {
        storageCommandLine = storageCommandLineIn;
    }

    @Override
    public final StorageCommandLine getStorageCommandLine() {
        return storageCommandLine;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public StorageCommandResult execute(String commandWithArguments) {
        return function.apply(commandWithArguments);
    }

}
