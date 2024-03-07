package carsharing.client.command.companyCommand;

import carsharing.client.command.IFromInput;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;


public enum CompanyCommand implements IFromInput<CompanyCommand> {
    CompanyList("1", "1. Company list"),
    CreateCompany("2", "2. Create a company"),
    Back("0", "0. Back"),
    STATIC("static", "static");

    public final String value;
    private final String printValue;
    CompanyCommand(String value, String printValue) {
        this.value = value;
        this.printValue = printValue;
    }

    @Override
    public CompanyCommand fromUserInput(String input) {
        return switch (input) {
            case "1" -> CompanyList;
            case "2" -> CreateCompany;
            case "0" -> Back;
            default -> throw new InputMismatchException();
        };
    }


    @Override
    public String toString() {
        return printValue;
    }

    @Override
    public void printCommands() {
        String result = Arrays.stream(CompanyCommand.values()).map(CompanyCommand::toString).reduce("", (str, strCommand) -> str + (!Objects.equals(strCommand, CompanyCommand.STATIC.printValue) ? "%s\n".formatted(strCommand) : ""));
        System.out.print(result);
    }
}

