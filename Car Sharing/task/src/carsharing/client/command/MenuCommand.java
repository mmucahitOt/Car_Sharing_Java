package carsharing.client.command;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;

public enum MenuCommand implements IFromInput<MenuCommand> {
    LOGIN_MANAGER("1", "1. Log in as a manager"),
    LOGIN_CUSTOMER("2", "2. Log in as a customer"),
    CREATE_CUSTOMER("3", "3. Create a customer"),
    EXIT("0", "0. Exit"),
    STATIC("static", "static");

    public final String value;
    private final String printValue;
    MenuCommand(String value, String printValue) {
        this.value = value;
        this.printValue = printValue;
    }

    @Override
    public MenuCommand fromUserInput(String input) {
        return switch (input) {
            case "1" -> LOGIN_MANAGER;
            case "2" -> LOGIN_CUSTOMER;
            case "3" -> CREATE_CUSTOMER;
            case "0" -> EXIT;
            default -> throw new InputMismatchException();
        };
    }

    @Override
    public void printCommands() {
        String result = Arrays.stream(MenuCommand.values()).map(MenuCommand::toString).reduce("", (str, strCommand) -> str + (!Objects.equals(strCommand, MenuCommand.STATIC.printValue) ? "%s\n".formatted(strCommand) : ""));
        System.out.print(result);
    }


    @Override
    public String toString() {
        return printValue;
    }
}
