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

import com.pixelgamelibrary.storage.Storage;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author robertvokac
 */
public class StorageCommandLine {

    private String user;
    private String hostname;
    private Storage storage;
    //
    private boolean exited = false;
    //
    private long startNanoTime = System.nanoTime();

    public String getCommandLineStart() {
        return user + "@" + hostname + ":" + storage.pwd() + "$ ";
    }

    private String extractArgument(String arguments, int argumentIndex) {
        if(arguments.isEmpty()) {
            return arguments;
        }
        String[] array = arguments.split(" ");
        if (argumentIndex > (array.length)) {
            return "";
        }
        return array[argumentIndex + 1];
    }

    public StorageCommandLine(String userIn, String hostnameIn, Storage storageIn) {

        this.user = userIn;
        this.hostname = hostnameIn;
        this.storage = storageIn;

        addCommand("date", arguments -> provideOutput(result -> result.setOutput(new Date().toString())));
        addCommand("whoami", arguments -> provideOutput(result -> result.setOutput(user)));
        addCommand("uptime", arguments -> provideOutput(result
                -> result.setOutput(
                        new Date().toString().substring(11, 19) + " up "
                        + (System.nanoTime() - startNanoTime) / 1000000000l / 60l
                        + " minutes"
                        + ", 1 user"
                )));

        addCommand("hostname", arguments -> provideOutput(result -> result.setOutput(hostname)));
        addCommand("test", arguments-> provideOutput(result-> result.setOutput((extractArgument(arguments, 0)))));
        addCommand("uname", arguments -> provideOutput(result -> result.setOutput(
                "LinuxBashCommandLinePartialEmulation"
                + ((extractArgument(arguments, 0).equals("-a"))
                ? (hostname + " 0.0.0 ("
                + new Date().toString() + ")")
                : "")
        )));

        addCommand("ls", arguments -> provideOutput(result -> result.setOutput(storage
                .ls()
                .stream()
                .map(l -> {
                    String[] a = l.split("/");
                    return a[a.length - 1];
                }
                ).collect(Collectors.joining("\n")))));

        addCommand("pwd", arguments -> provideOutput(result -> result.setOutput(storage.pwd())));
        addCommand("depth", arguments -> provideOutput(result -> result.setOutput(storage.depth())));

        addCommand("mkdir", arguments -> provideOutput(result
                -> {
            String string = storage.mkdirmore(extractArguments(arguments));
            if (string.isEmpty()) {
                result.setOutput("New directory was successfully created");

            } else {
                result.setErrorOutput("Creating new directory failed: " + string);

            }
        }
        ));

        commands.keySet().stream().map(k -> commands.get(k)).forEach(c -> c.setStorageCommandLine(this));
    }

    private String[] extractArguments(String arguments) {
        return Arrays.asList(arguments.split(" ")).stream()
                .filter(a->!a.isEmpty())
                .toArray(String[]::new);
    }

    private StorageCommandResult provideOutput(Consumer<StorageCommandResult> consumer) {

        StorageCommandResult result = StorageCommand.emptyNewResult();
        consumer.accept(result);
        return result;
    }

    private void addCommand(String nameIn, Function<String, StorageCommandResult> functionIn) {
        StorageCommand storageCommand = new BaseCommand(this, nameIn, functionIn);
        commands.put(storageCommand.getName(), storageCommand);
    }

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

            case "touch":

                String r = storage.touch(argument1.get());
                if (r.isEmpty()) {
                    finalResult.setOutput("New file was successfully created");
                } else {
                    finalResult.setErrorOutput("Creating new directory failed: " + r);
                }

                break;
            case "readtext":

                String rr = storage.readtext(argument1.get());
                if (rr != null) {
                    finalResult.setOutput("Text file was successfully loaded" + "\n\n" + rr);
                } else {
                    finalResult.setErrorOutput("Loading text file failed:");
                }

                break;

            case "savetext":

                String result = storage.savetext(argument1.get(), argument2.get());
                if (result.isEmpty()) {
                    finalResult.setOutput("Text file was successfully saved");
                } else {
                    finalResult.setErrorOutput("Saving text file failed: " + result);
                }

                break;
            case "cd":
                String rrr = argument1.isEmpty() ? storage.cd() : storage.cd(argument1.get());
                if (rrr.isEmpty()) {
                    finalResult.setOutput("Changing working directory was successfully created");
                } else {
                    finalResult.setErrorOutput("Changing working directory failed: " + rrr);
                }

                break;
            case "debug":
                finalResult.setOutput(storage.debug());

                break;

            case "exit":
                exited = true;
                finalResult.setOutput("Exited");
                break;
            default: {
                finalResult.setErrorOutput("Unsupported command: " + command);
            }
        }
        return finalResult;
    }

}
