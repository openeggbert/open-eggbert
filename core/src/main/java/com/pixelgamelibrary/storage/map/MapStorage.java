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

import com.badlogic.gdx.Gdx;
import com.pixelgamelibrary.Pixel;
import com.pixelgamelibrary.Platform;
import com.pixelgamelibrary.storage.StorageException;
import com.pixelgamelibrary.storage.Storage;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author robertvokac
 */
public class MapStorage implements Storage {

    private final SimpleMap map;

    private final MapStorageCompression mapStorageCompression;
    public MapStorage(SimpleMap mapIn) {
        this(mapIn, MapStorageCompression.NONE);
    }
    public MapStorage(SimpleMap mapIn, MapStorageCompression mapStorageCompressionIn) {
        this.map = mapIn;
        this.mapStorageCompression = mapStorageCompressionIn;
        mkdir("/");
        
    }

    private String workingDirectory = "/";

    @Override
    public Platform getPlatform() {
        return null;
    }

    private String convertToAbsolutePathIfNeeded(String path) {
        if (path.startsWith(SLASH)) {
            return path;
        }

        return workingDirectory + (workingDirectory.equals("/") ? "" : SLASH) + path;
    }
    
    private static final String TWO_DOTS = "..";
    
    @Override
    public String cd(String path) {
//        System.out.println("path="+path);
        String absolutePath = path.equals(TWO_DOTS) ? getParentPath(workingDirectory) : convertToAbsolutePathIfNeeded(path);

        if (!exists(absolutePath)) {
            final String msg = "Path does not exist: " + absolutePath;
            logError(msg);
            return msg;
        }
        if (!isdir(absolutePath)) {
            final String msg = "Path is not directory: " + absolutePath;
            logError(msg);
            return msg;
        }

        workingDirectory = absolutePath;
        return "";
    }
    private static final String SLASH = "/";    

    @Override
    public String mkdir(String path) {
        if(path.isEmpty()) {
            String msg = "Missing argument";
            logError(msg);
            return msg;
        }
        String absolutePath = convertToAbsolutePathIfNeeded(path);
        final String parentPath = getParentPath(absolutePath);
        if (!path.equals(SLASH) && !exists(parentPath)) {
            var msg = "Cannot create new directory, because parent path does not exist: " + parentPath;
            logError(msg);
            return msg;
        }
        if (!path.equals(SLASH) && !isdir(parentPath)) {
            var msg = "Cannot create new directory, because parent path is not directory: " + parentPath;
            logError(msg);
            return msg;
        }
        if (exists(absolutePath)) {
            var msg = "Cannot create new directory, because path already exists: " + absolutePath;
            logError(msg);
            return msg;
        }
        map.putString(absolutePath, MapFileType.DIRECTORY + EIGHT_COLONS);

        return "";

    }
    private static final String EIGHT_COLONS = "::::::::";

    private static String getParentPath(String path) {
//        System.out.println("getParentPath()");
        if (path == null) {
            throw new StorageException("Path is null");
        }
        if (path.trim().isEmpty()) {
            throw new StorageException("Path is empty");
        }

        if (path.equals("/")) {
            return path;
        }
        String[] array = path.split(SLASH);
        if (array.length == 2) {
            return SLASH;
        }
        return path.substring(0, path.length() - 1 - array[array.length - 1].length());
    }

    @Override
    public String pwd() {
        return workingDirectory;
    }

    @Override
    public int depth(String path) {
        String absolutePath = convertToAbsolutePathIfNeeded(path);
        if (absolutePath.equals(SLASH)) {
            return 0;
        }
        String[] array = absolutePath.split(SLASH);
        return array.length -1;
    }

    @Override
    public List<String> ls(String path) {
        int currentDepth = depth(path);
        return map
                .keyList()
                .stream()
                .filter(key -> key.startsWith(workingDirectory))
                .filter(key -> depth(key) == (currentDepth + 1))
                .collect(Collectors.toList());
    }

    @Override
    public String touch(String path) {
        return touch(path, "");
    }

    public String touch(String path, String content) {
        String absolutePath = convertToAbsolutePathIfNeeded(path);
        final String parentPath = getParentPath(absolutePath);
        if (!exists(parentPath)) {
            var msg = "Cannot create new file, because parent path does not exist: " + parentPath;
            logError(msg);
            return msg;
        }
        if (!isdir(parentPath)) {
            var msg = "Cannot create new file, because parent path is not directory: " + parentPath;
            logError(msg);
            return msg;
        }
        if (exists(absolutePath)) {
            var msg = "Cannot create new file, because path already exists: " + absolutePath;
            logError(msg);
            return msg;
        }
        map.putString(absolutePath, MapFileType.FILE + EIGHT_COLONS + content);

        return "";
    }

    @Override
    public boolean rm(String path) {
        String absolutePath = convertToAbsolutePathIfNeeded(path);

        if (!map.contains(absolutePath)) {
            logError("Cannot remove file, because it does not exist: " + absolutePath);
            return false;
        }
        map.remove(absolutePath);
        return true;
    }

    @Override
    public String cp(String source, String target) {
        return moveOrCp(source, target, false, true);
    }

    @Override
    public String mv(String source, String target) {
        return moveOrCp(source, target, true, false);
    }

    private String moveOrCp(String source, String target, boolean move, boolean cp) {
        if (move && cp) {
            throw new StorageException("move == true && cp == true");
        }
        if (!move && !cp) {
            throw new StorageException("move != true && cp != true");
        }
        String absolutePathSource = convertToAbsolutePathIfNeeded(source);
        String absolutePathTarget = convertToAbsolutePathIfNeeded(target);
        String targetParentPath = getParentPath(absolutePathTarget);

        if (!exists(absolutePathSource)) {
            final String msg = "absolutePathSource does not exist: " + absolutePathSource;
            logError(msg);
            return msg;
        }
        if (isdir(absolutePathSource)) {
            final String msg = "absolutePathSource is directory: " + absolutePathSource;
            logError(msg);
            return msg;
        }
        if (!exists(targetParentPath)) {
            final String msg = "targetParentPath does not exist: " + absolutePathSource;
            logError(msg);
            return msg;
        }
        if (!isdir(targetParentPath)) {
            final String msg = "targetParentPath is not directory: " + absolutePathSource;
            logError(msg);
            return msg;
        }
        String contentOfSourceFile = map.getString(absolutePathSource);
        String result = touch(absolutePathTarget);
        if (!result.isEmpty()) {
            var msg = "Creating new file failed: " + absolutePathTarget;
            logError(msg);
            return msg;
        }
        map.remove(absolutePathTarget);
        map.putString(absolutePathTarget, contentOfSourceFile);
        if (move) {
            map.remove(absolutePathSource);
        }
        return "";
    }

    @Override
    public String readtext(String path) {
        String absolutePath = convertToAbsolutePathIfNeeded(path);
        if (!exists(absolutePath)) {
            logError("absolutePathSource does not exist: " + absolutePath);
            return null;
        }
        if (isdir(absolutePath)) {
            logError("absolutePathSource is directory: " + absolutePath);
            return null;
        }
        String value = map.getString(absolutePath);
        return value.split(EIGHT_COLONS)[1];
    }

    @Override
    public byte[] readbin(String path) {
        String absolutePath = convertToAbsolutePathIfNeeded(path);

        String text = readtext(absolutePath);
        if (!text.startsWith(BINARYFILE)) {
            logError("File is not binary:" + absolutePath);
            return null;
        }
        text = text.substring(BINARYFILE.length());
        return Pixel.utils().decodeBase64AsByteArray(text);
    }
    private static final String BINARYFILE = "BINARYFILE";

    @Override
    public String savetext(String name, String text) {
        return touch(name, text);
    }

    @Override
    public String savebin(String name, byte[] data) {
        return savetext(name, BINARYFILE + Pixel.utils().encodeToBase64(data));
    }

    @Override
    public boolean exists(String name) {
        return map.contains(convertToAbsolutePathIfNeeded(name));
    }

    @Override
    public boolean isfile(String name) {
        return filetype(name) == MapFileType.FILE;
    }

    @Override
    public boolean isdir(String name) {
        return filetype(name) == MapFileType.DIRECTORY;
    }

    public MapFileType filetype(String name) {
        return MapFileType.ofKey(convertToAbsolutePathIfNeeded(name), map);
    }

    @Override
    public String debug() {
        StringBuilder sb = new StringBuilder();
        for(String key: map.keyList()) {
            sb
                    .append(key)
                    .append("=")
                    .append(map.getString(key))
                    .append("\n");
        }
        return sb.toString();
    }

    @Override
    public void flush() {
        map.flush();
    }

    @Override
    public boolean rmdir(String dirname) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void logError(String msg) {
        Pixel.app().error(msg);
    }

}
