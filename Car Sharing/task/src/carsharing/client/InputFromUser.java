package carsharing.client;

import carsharing.client.command.MenuCommand;
import carsharing.client.command.carCommand.CarCommand;
import carsharing.client.command.companyCommand.CompanyCommand;
import carsharing.client.command.custumerCommand.CustomerCommand;

import java.util.Scanner;

public class InputFromUser {
    Scanner scanner = new Scanner(System.in);
    public MenuCommand askForMenuCommand() {
        MenuCommand.STATIC.printCommands();
        String input = scanner.nextLine();
        System.out.println();
        return MenuCommand.STATIC.fromUserInput(input);
    }

    public CompanyCommand askForCompanyCommand() {
        CompanyCommand.STATIC.printCommands();

        String input = scanner.nextLine();
        System.out.println();
        return CompanyCommand.STATIC.fromUserInput(input);
    }

    public CarCommand askForCarCommand() {
        CarCommand.STATIC.printCommands();

        String input = scanner.nextLine();
        System.out.println();
        return CarCommand.STATIC.fromUserInput(input);
    }

    public CustomerCommand askForCustomerCommand() {
        CustomerCommand.STATIC.printCommands();

        String input = scanner.nextLine();
        System.out.println();
        return CustomerCommand.STATIC.fromUserInput(input);
    }

    public int askForCompanyId() {
        int result = Integer.parseInt(scanner.nextLine());
        System.out.println();
        return result;
    }

    public int askForCompanyIndex() {
        int result = Integer.parseInt(scanner.nextLine());
        System.out.println();
        return result;
    }

    public String getUserStringInput() {
        String input = scanner.nextLine();
        return input;
    }
}
