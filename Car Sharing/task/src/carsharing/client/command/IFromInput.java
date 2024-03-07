package carsharing.client.command;

public interface IFromInput<T> {
    T fromUserInput(String input);

    void printCommands();
}
