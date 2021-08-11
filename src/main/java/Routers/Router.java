package Routers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

public abstract class Router {
    public void run() throws IOException {
        BufferedReader keyReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print(getName() + ": ");
            String command = keyReader.readLine();
            String commandName;
            if (command.contains(" "))
                commandName = command.substring(0, command.indexOf(" "));
            else
                commandName = command;

            if (commandName.equals(CommandsHelper.HELP_COMMAND))
                callHelpList();
            else if (commandName.equals(CommandsHelper.EXIT_COMMAND))
                break;
            else
                callCommand(commandName, command.substring(command.indexOf(" ") + 1).split(" "));
        }
    }

    public abstract String getName();

    protected abstract void callHelpList();

    protected abstract void callCommand(String commandName, String[] split);
}
