package carsharing.client.command.carCommand;

import carsharing.client.command.IFromInput;
import carsharing.server.dao.car.Car;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;


public enum CarCommand implements IFromInput<CarCommand> {
    CarList("1", "1. Car list"),
    CreateCar("2", "2. Create a car"),
    Back("0", "0. Back"),
    STATIC("static", "static");

    public final String value;
    private final String printValue;

    CarCommand(String value, String printValue) {
        this.value = value;
        this.printValue = printValue;
    }

    @Override
    public CarCommand fromUserInput(String input) {
        return switch (input) {
            case "1" -> CarList;
            case "2" -> CreateCar;
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
        String result = Arrays.stream(CarCommand.values()).map(CarCommand::toString).reduce("", (str, strCommand) -> str + (!Objects.equals(strCommand, CarCommand.STATIC.printValue) ? "%s\n".formatted(strCommand) : ""));
        System.out.print(result);
    }
}

