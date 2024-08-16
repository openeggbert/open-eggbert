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
package com.openeggbert.core.utils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.openeggbert.core.main.OpenEggbertException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author robertvokac
 */
public class AssetsTxt {


    private final List<List<String>> filesLists = new ArrayList<>();
    private final List<List<String>> directoriesLists = new ArrayList<>();

    private final Set<String> directoriesSet = new HashSet<>();

    public AssetsTxt(String readString) {
        OpenEggbertUtils.lines(readString).forEach(line -> {
            var lineArray = Arrays.asList(line.split("/"));
            filesLists.add(lineArray);
            if (lineArray.size() > 1) {
                String fileName = lineArray.get(lineArray.size() - 1);
                String directory = line.substring(0, line.length() - 1 - fileName.length());
                if (!directoriesSet.contains(directory)) {
                    directoriesSet.add(directory);
                    directoriesLists.add(Arrays.asList(directory.split("/")));
                }
            }
        });
        //directories: without files, with only directories
        Set<String> subDirectoriesTmpSet = new HashSet<>();
        for (String dir : directoriesSet) {
            List<String> list = Arrays.asList(dir.split("/"));
            int depth = list.size();

            while (depth > 1) {
                depth = depth - 1;
                String aSubdirectory = list.stream().limit(depth).collect(Collectors.joining("/"));
                if (!directoriesSet.contains(aSubdirectory)) {
                    subDirectoriesTmpSet.add(aSubdirectory);
                    directoriesLists.add(Arrays.asList(aSubdirectory.split("/")));
                }

            }
        }
        directoriesSet.addAll(subDirectoriesTmpSet);

    }

    public void listDirectories() {
        directoriesLists.forEach(l -> System.out.println(convertListStringToStringPath(l)));
    }

    public void listFiles() {
        filesLists.forEach(l -> System.out.println(convertListStringToStringPath(l)));
    }

    public List<String> listRoot(boolean directoryType, boolean fileType) {
        return AssetsTxt.this.list(".", directoryType, fileType);
    }

    public List<String> listRoot() {
        return listRoot(true, true);
    }

    public List<String> list(String pathToDirectory) {
        return AssetsTxt.this.list(pathToDirectory, true, true);
    }

    public List<String> listDirectories(String pathToDirectory) {
        return AssetsTxt.this.list(pathToDirectory, true, false);
    }

    public List<String> listFiles(String pathToDirectory) {
        return AssetsTxt.this.list(pathToDirectory, false, true);
    }

    public List<String> list(String pathToDirectory, boolean directoryType, boolean fileType) {
//        System.out.println("Calling: AssetsTxt.list( " + pathToDirectory + " ...)");
        if (!directoryType && !fileType) {
            throw new OpenEggbertException("Invalid arguments, both arguments are false: directoryType, fileType");
        }
        
        if (pathToDirectory.equals(".")) {
            List<String> files = fileType ? filesLists
                    .stream()
                    .filter(l -> l.size() == 1)
                    .map(l -> l.get(0))
                    .collect(Collectors.toList()) : new ArrayList<>();
            List<String> directories = directoryType ? directoriesLists
                    .stream()
                    .filter(l -> l.size() == 1)
                    .map(l -> l.get(0))
                    .collect(Collectors.toList()) : new ArrayList<>();
            List<String> result = new ArrayList<>();
            result.addAll(files);
            result.addAll(directories);
            return result;
        }
        if (!directoriesSet.contains(pathToDirectory)) {
            throw new OpenEggbertException("There is no such directory in assets: " + pathToDirectory);
        }

        var directoryArray = pathToDirectory.split("/");
        int depth = directoryArray.length;

        List<String> files = fileType ? filesLists
                .stream()
                .filter(l -> l.size() == depth + 1)
                .filter(l -> convertListStringToStringPath(l).startsWith(pathToDirectory))
                .map(l -> l.get(depth))
                .collect(Collectors.toList()) : new ArrayList<>();
        List<String> directories = directoryType ? directoriesLists
                .stream()
                .filter(l -> l.size() == depth + 1)
                .filter(l -> convertListStringToStringPath(l).startsWith(pathToDirectory))
                .map(l -> l.get(depth))
                .distinct()
                .collect(Collectors.toList()) : new ArrayList<>();
        List<String> result = new ArrayList<>();
        result.addAll(files);
        result.addAll(directories);
        return result;

    }
    
    public List<FileHandle> list(FileHandle fileHandle) {
        String pathToDirectory = fileHandle.path();//((fileHandle.path().isEmpty() ? "" : (fileHandle.path() + "/"))) + fileHandle.name();
        Function<String, FileHandle> createFileHandle = s -> 
            Gdx.app.getType() == Application.ApplicationType.Desktop ?
                Gdx.files.classpath(s):Gdx.files.internal(s)
        ;
        return AssetsTxt.this.list(pathToDirectory)
                .stream()
                .map(p-> createFileHandle.apply((pathToDirectory.equals(".") ? "" : (pathToDirectory + "/")) + p))
                .collect(Collectors.toList());
    }

    private static String convertListStringToStringPath(List<String> list) {
        return list.stream().collect(Collectors.joining("/"));
    }

}
