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
package com.openeggbert.lwjgl3.debugging.storage;

import com.openeggbert.storage.Storage;
import com.openeggbert.storage.map.MemoryStorage;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author robertvokac
 */
public class StorageCommandLine {

    private long startNanoTime = System.nanoTime();

    private String extractArgument(String arguments, int argumentIndex) {
        String[] array = arguments.split(" ");
        if(argumentIndex > (array.length - 1)) {
            return "";
        }
        return array[argumentIndex];
    }
    private StorageCommandLine(String userIn, String hostnameIn, Storage storageIn) {

        this.user = userIn;
        this.hostname = hostnameIn;
        this.storage = storageIn;

        addCommand("whoami", arguments -> modifyResult(result -> result.setOutput(user)));
        addCommand("uptime", arguments -> modifyResult(result
                -> result.setOutput(
                        LocalDateTime.now().toString().replace("T", " ").substring(10, 19) + " up "
                        + (System.nanoTime() - startNanoTime) / 1000000000l / 60l
                        + " minutes"
                        + ", 1 user"
                )));

        addCommand("hostname", arguments -> modifyResult(result -> result.setOutput(hostname)));
        addCommand("uname", arguments -> modifyResult(result -> result.setOutput(
        "LinuxBashCommandLinePartialEmulation"
                +
                ((extractArgument(arguments, 0).equals("-a")) ? 
                        (hostname + " 0.0.0 ("
                                +
                                LocalDateTime.now().toString().replace("T", " ").substring(0, 10) + ")"
                        )
                        : "")
        
        
        
        )));

        
                    
        
        
        
//         12:31:18 up  2:10,  1 user
        commands.keySet().stream().map(k -> commands.get(k)).forEach(c -> c.setStorageCommandLine(this));
    }

    private StorageCommandResult modifyResult(Consumer<StorageCommandResult> consumer) {

        StorageCommandResult result = StorageCommand.emptyNewResult();
        consumer.accept(result);
        return result;
    }

    private void addCommand(String nameIn, Function<String, StorageCommandResult> functionIn) {
        StorageCommand storageCommand = new BaseCommandLine(this, nameIn, functionIn);
        commands.put(storageCommand.getName(), storageCommand);
    }

    private String user;
    private String hostname;
    private Storage storage;

    private boolean exited = false;
    private final Map<String, StorageCommand> commands = new HashMap<>();

    public String getUser() {
        return user;
    }

    public String getHostname() {
        return hostname;
    }

    public Storage getStorage() {
        return storage;
    }

    public boolean isExited() {
        return exited;
    }

    public StorageCommandResult execute(String commandWithArguments) {

        String[] arguments = commandWithArguments.split(" ");
        String command = arguments.length == 0 ? "" : arguments[0];

        StorageCommand storageCommand = commands.get(command);
        if (storageCommand != null) {
            return storageCommand.execute(commandWithArguments.substring(command.length()));
        }

        int argumentCount = arguments.length - 1;
        //System.out.println("argumentCount=" + argumentCount);
        Optional<String> argument1 = Optional.ofNullable(argumentCount >= 1 ? arguments[1] : null);
        Optional<String> argument2 = Optional.ofNullable(argumentCount >= 2 ? arguments[2] : null);

        StorageCommandResult finalResult = new StorageCommandResult();
        switch (command) {
            case "exit":
                exited = true;
                finalResult.setOutput("Exited");
                break;
            case "":
                break;
            case "ls":
                String output = storage
                        .ls()
                        .stream()
                        .map(l -> {
                            String[] a = l.split("/");
                            return a[a.length - 1];
                        }
                        ).collect(Collectors.joining("\n"));
                finalResult.setOutput(output);
                break;
            case "pwd":
                finalResult.setOutput(storage.pwd());
                break;

            case "depth":
                finalResult.setOutput(storage.depth());
                break;
            case "mkdir":
                if (checkArgumentCount(1, argumentCount)) {
                    String r = storage.mkdir(argument1.get());
                    if (r.isEmpty()) {
                        finalResult.setOutput("New directory was successfully created");
                    } else {
                        finalResult.setErrorOutput("Creating new directory failed: " + r);
                    }

                }
                break;
            case "touch":
                if (checkArgumentCount(1, argumentCount)) {
                    String result = storage.touch(argument1.get());
                    if (result.isEmpty()) {
                        finalResult.setOutput("New file was successfully created");
                    } else {
                        finalResult.setErrorOutput("Creating new directory failed: " + result);
                    }

                }
                break;
            case "readtext":
                if (checkArgumentCount(1, argumentCount)) {
                    String result = storage.readtext(argument1.get());
                    if (result != null) {
                        finalResult.setOutput("Text file was successfully loaded" + "\n\n" + result);
                    } else {
                        finalResult.setErrorOutput("Loading text file failed:");
                    }

                }
                break;

            case "savetext":
                if (checkArgumentCount(2, argumentCount)) {
                    String result = storage.savetext(argument1.get(), argument2.get());
                    if (result.isEmpty()) {
                        finalResult.setOutput("Text file was successfully saved");
                    } else {
                        finalResult.setErrorOutput("Saving text file failed: " + result);
                    }

                }
                break;
            case "cd":
                String r = argument1.isEmpty() ? storage.cd() : storage.cd(argument1.get());
                if (r.isEmpty()) {
                    finalResult.setOutput("Changing working directory was successfully created");
                } else {
                    finalResult.setErrorOutput("Changing working directory failed: " + r);
                }

                break;
            case "debug":
                finalResult.setOutput(storage.debug());

                break;
            default: {
                finalResult.setErrorOutput("Unsupported command: " + command);
            }
        }
        return finalResult;
    }

    public static void main(String[] args) {
        MemoryStorage memoryStorage = new MemoryStorage();

        StorageCommandLine storageCommandWrapper = new StorageCommandLine("player", "openegggbert", memoryStorage);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("player@openegggbert:" + memoryStorage.pwd() + "$ ");
            String argument = scanner.nextLine();

            StorageCommandResult result = storageCommandWrapper.execute(argument);
            if (result.isError()) {
                printError(result.getOutput());
            } else {
                print(result.getOutput());

            }
            if (storageCommandWrapper.isExited()) {
                break;
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
