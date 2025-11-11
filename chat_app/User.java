package chat_app;

import java.util.Iterator;

public class User implements UserColleague,IterableByUser {
    private String username;
    private ChatServerMediator mediator;
    private ChatHistory chatHistory;

    public User(String username, ChatServerMediator mediator) {
        setUsername(username);
        setMediator(mediator);
        this.chatHistory = new ChatHistory();
    }

    public String getUsername() {
        return username;
    }

    public ChatServerMediator getMediator() {
        return mediator;
    }

    public ChatHistory getChatHistory() {
        return chatHistory;
    }

    public void setUsername(String username) {
        if (username == null) {
            throw new NullPointerException("Username cannot be null.");
        } else if (username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        this.username = username;
    }

    public void setMediator(ChatServerMediator mediator) {
        if (mediator == null) {
            throw new NullPointerException("Mediator cannot be null.");
        }
        this.mediator = mediator;
    }

    public void setChatHistory(ChatHistory chatHistory) {
        if (chatHistory == null) {
            throw new NullPointerException("ChatHistory cannot be null.");
        }
        this.chatHistory = chatHistory;
    }

    public void blockUser(User user) {
        if (user == null) {
            throw new NullPointerException("User cannot be null.");
        }
        mediator.blockMessages(this, user);
    }

    @Override
    public void sendMessage(String content, User[] recipients) {
        if (content == null) {
            throw new NullPointerException("Content cannot be null.");
        } else if (content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty.");
        } else if (recipients == null) {
            throw new NullPointerException("Recipients cannot be null.");
        } else if (recipients.length == 0) {
            throw new IllegalArgumentException("Recipients cannot be empty.");
        }

        Message message = new Message(this, recipients, content);
        mediator.sendMessage(message);
        chatHistory.addMemento(message.createMemento());
    }

    @Override
    public void receiveMessage(Message message) {
        if (message == null) {
            throw new NullPointerException("Message cannot be null.");
        }
        System.out.println("(" + username + " received) " + message.getSender().getUsername() + ": " + message.getMessageContent());
        chatHistory.addMemento(message.createMemento());
    }

    @Override
    public void undoLastMessage() {
        MessageMemento lastMessage = chatHistory.getLastSentMessage(this);
        if (lastMessage != null) {
            System.out.println(username + " is undoing the last message: " + lastMessage.getSavedContent()[1]);
            chatHistory.mementos.remove(lastMessage);
        } else {
            System.out.println("No last messages to undo.");
        }
    }

    public void viewChatHistory() {
        System.out.println(username + "'s Chat History:");
        for (MessageMemento memento : chatHistory.mementos) {
            System.out.println(
                "\tSender: " + ((User) memento.getSavedContent()[0]).getUsername() +
                ", Message: " + memento.getSavedContent()[1] +
                ", Timestamp: " + memento.getSavedContent()[2]);
        }
    }

    @Override
    public Iterator<MessageMemento> iterator(User userToSearchWith) {
        if (userToSearchWith == null) {
            throw new NullPointerException("User cannot be null.");
        }

        return chatHistory.iterator(userToSearchWith);
    }
}
