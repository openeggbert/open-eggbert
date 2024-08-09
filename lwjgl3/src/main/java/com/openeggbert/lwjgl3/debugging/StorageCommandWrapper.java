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
package com.openeggbert.lwjgl3.debugging;

import com.openeggbert.storage.Storage;
import com.openeggbert.storage.map.MemoryStorage;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author robertvokac
 */
public class StorageCommandWrapper {

    private StorageCommandWrapper(String userIn, String hostnameIn, Storage storageIn) {
        this.user = userIn;
        this.hostname = hostnameIn;
        this.storage = storageIn;
    }
    private String user;
    private String hostname;
    private Storage storage;

//    @AllArgsConstructor
//    @Data
//    class StorageCommandWrapperResult {
//
//        private String output;
//        private boolean error;
//    }
//
//    public StorageCommandWrapperResult execute(String commandWithArguments) {
//        return null;
//    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        MemoryStorage memoryStorage = new MemoryStorage();

        while (true) {
            System.out.print("player@openegggbert:" + memoryStorage.pwd() + "$ ");
            String argument = scanner.nextLine();
            String[] arguments = argument.split(" ");
            int argumentCount = arguments.length - 1;
            //System.out.println("argumentCount=" + argumentCount);
            Optional<String> argument1 = Optional.ofNullable(argumentCount >= 1 ? arguments[1] : null);
            Optional<String> argument2 = Optional.ofNullable(argumentCount >= 2 ? arguments[2] : null);
            String command = arguments.length == 0 ? "" : arguments[0];

            switch (command) {
                case "whoami":
                    print("player");break;
                case "hostname":
                    print("openegggbert");break;
                case "uname":
                    if (argumentCount == 0) {
                        print("LinuxBashCommandLinePartialEmulation");
                    } else {
                        if (argument1.get().equals("-a")) {
                            print("LinuxBashCommandLinePartialEmulation openeggbert 0.0.0 (" + LocalDateTime.now().toString().replace("T", " ").substring(0, 10) + ")");
                        } else {
                            print("LinuxBashCommandLinePartialEmulation");
                        }
                    }
                    break;
                case "exit":
                    return;
                case "":
                    continue;
                case "ls":
                    memoryStorage
                            .ls()
                            .stream()
                            .map(l->{
                                String[] a = l.split("/");return a[a.length-1];
                                        }
                            )
                            .forEach(l -> print(l));
                    break;
                case "pwd":
                    print(memoryStorage.pwd());
                    break;

                case "depth":
                    print(memoryStorage.depth());
                    break;
                case "mkdir":
                    if (checkArgumentCount(1, argumentCount)) {
                        String result = memoryStorage.mkdir(argument1.get());
                        if (result.isEmpty()) {
                            System.out.println("New directory was successfully created");
                        } else {
                            System.err.println("Creating new directory failed: " + result);
                        }

                    }
                    break;
                case "touch":
                    if (checkArgumentCount(1, argumentCount)) {
                        String result = memoryStorage.touch(argument1.get());
                        if (result.isEmpty()) {
                            System.out.println("New file was successfully created");
                        } else {
                            System.err.println("Creating new directory failed: " + result);
                        }

                    }
                    break;
                case "readtext":
                    if (checkArgumentCount(1, argumentCount)) {
                        String result = memoryStorage.readtext(argument1.get());
                        if (result != null) {
                            System.out.println("Text file was successfully loaded");
                            System.out.println(result);
                        } else {
                            System.err.println("Loading text file failed:");
                        }

                    }
                    break;
                    
                case "savetext":
                    if (checkArgumentCount(2, argumentCount)) {
                        String result = memoryStorage.savetext(argument1.get(), argument2.get());
                        if (result.isEmpty()) {
                            System.out.println("Text file was successfully saved");
                        } else {
                            System.err.println("Saving text file failed: " + result);
                        }

                    }
                    break;
                case "cd":
                    String result = argument1.isEmpty() ? memoryStorage.cd() : memoryStorage.cd(argument1.get());
                    if (result.isEmpty()) {
                        System.out.println("Changing working directory was successfully created");
                    } else {
                        System.err.println("Changing working directory failed: " + result);
                    }

                    break;
                case "debug":
                    print(memoryStorage.debug());

                    break;
                default: {
                    System.err.println("Unsupported command: " + command);
                }
            }

        }
    }

    private static void print(int msg) {
        print(String.valueOf(msg));
    }

    private static void print(String msg) {
        System.out.println(msg);
    }

    private static void printError(String msg) {
        System.err.println(msg);
    }

    private static boolean checkArgumentCount(int wantedCount, int currentCount) {
//        System.out.println("wantedCount=" + wantedCount);
//        System.out.println("currentCount=" + currentCount);
        boolean b = wantedCount < currentCount;
//        System.out.println("b=" + b);
        if (currentCount < wantedCount) {

            printError("Wanted argument count is: " + wantedCount + ", but the current count of arguments is: " + currentCount);
//            System.out.println("return false");
            return false;
        }
//        System.out.println("return true");
        return true;
    }
}
