package chat_app;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();

        User john = new User("John", chatServer);
        User james = new User("James", chatServer);
        User tom = new User("Tom", chatServer);

        chatServer.registerUser(john);
        chatServer.registerUser(james);
        chatServer.registerUser(tom);

        john.sendMessage("Hello James and Tom!", new User[]{james, tom});
        james.sendMessage("Hello John!", new User[]{john, tom});
        james.blockUser(tom);
        tom.sendMessage("Hello John and James!", new User[]{john, james});
        james.sendMessage("Tom was not supposed to be added to this chat, its the other one.", new User[]{john});
        james.undoLastMessage();

        System.out.println();
        john.viewChatHistory();
        System.out.println();
        james.viewChatHistory();
        System.out.println();
        tom.viewChatHistory();

        System.out.println("\nJohn's messages from James:");
        Iterator<MessageMemento> iterator = john.iterator(james);
        while (iterator.hasNext()) {
            MessageMemento memento = iterator.next();
            System.out.println("\tSender: " + ((User) memento.getSavedContent()[0]).getUsername() +
                    ", Message: " + memento.getSavedContent()[1] +
                    ", Timestamp: " + memento.getSavedContent()[2]);
        }
    }
}
