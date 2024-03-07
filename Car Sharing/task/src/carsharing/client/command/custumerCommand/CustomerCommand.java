package carsharing.client.command.custumerCommand;


import carsharing.client.command.IFromInput;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;


public enum CustomerCommand implements IFromInput<CustomerCommand> {
    RentCar("1", "1. Rent a car"),
    ReturnCar("2", "2. Return a rented car"),
    RentedCar("3", "3. My rented car"),
    Back("0", "0. Back"),
    STATIC("static", "static");

    public final String value;
    private final String printValue;
    CustomerCommand(String value, String printValue) {
        this.value = value;
        this.printValue = printValue;
    }

    @Override
    public CustomerCommand fromUserInput(String input) {
        return switch (input) {
            case "1" -> RentCar;
            case "2" -> ReturnCar;
            case "3" -> RentedCar;
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
        String result = Arrays.stream(CustomerCommand.values()).map(CustomerCommand::toString).reduce("", (str, strCommand) -> str + (!Objects.equals(strCommand, CustomerCommand.STATIC.printValue) ? "%s\n".formatted(strCommand) : ""));
        System.out.print(result);
    }
}

