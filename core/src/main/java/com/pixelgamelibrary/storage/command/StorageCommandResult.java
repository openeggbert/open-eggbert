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

/**
 *
 * @author robertvokac
 */
public class StorageCommandResult {

    public StorageCommandResult() {
        this("");
    }

    public StorageCommandResult(String output) {
        this(output, false);
    }

    public StorageCommandResult(String output, boolean error) {
        this.output = output;
        this.error = error;
    }

    public String getOutput() {
        return output;
    }

    public boolean isError() {
        return error;
    }

    public void setOutput(int output) {
        setOutput(String.valueOf(output));
    }

    public void setErrorOutput(String output) {
        this.output = output;
        setError(true);
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    private String output;
    private boolean error;
}
