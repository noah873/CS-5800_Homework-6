package chat_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer implements ChatServerMediator {
    private List<User> users;
    private Map<User, List<User>> blockedUsers;

    public ChatServer() {
        users = new ArrayList<>();
        blockedUsers = new HashMap<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public Map<User, List<User>> getBlockedUsers() {
        return blockedUsers;
    }

    public void setUsers(List<User> users) {
        if (users == null) {
            throw new NullPointerException("Users can't be null");
        } else if (users.isEmpty()) {
            throw new IllegalArgumentException("Users can't be empty");
        }
        this.users = users;
    }

    public void setBlockedUsers(Map<User, List<User>> blockedUsers) {
        if (blockedUsers == null) {
            throw new NullPointerException("Blocked Users can't be null");
        }
        this.blockedUsers = blockedUsers;
    }

    @Override
    public void registerUser(User user) {
        if (user == null) {
            throw new NullPointerException("User cannot be null.");
        } else if (users.contains(user)) {
            throw new IllegalArgumentException("User already exists.");
        }

        users.add(user);
        blockedUsers.put(user, new ArrayList<>());
    }

    @Override
    public void unregisterUser(User user) {
        if (user == null) {
            throw new NullPointerException("User cannot be null.");
        } else if (!users.contains(user)) {
            throw new IllegalArgumentException("User does not exist.");
        }

        users.remove(user);
        blockedUsers.remove(user);
    }

    @Override
    public void sendMessage(Message message) {
        if (message == null) {
            throw new NullPointerException("Message cannot be null.");
        } else if (!users.contains(message.getSender())) {
            throw new IllegalArgumentException("Sending User: " + message.getSender() + " not found.");
        }

        for (User recipient : message.getRecipients()) {
            if (!users.contains(recipient)) {
                throw new IllegalArgumentException("Recipient User: " + recipient + " not found.");
            } else if (!blockedUsers.get(recipient).contains(message.getSender())) {
                recipient.receiveMessage(message);
            }
        }
    }

    @Override
    public void blockMessages(User initiatingUser, User blockedUser) {
        if (initiatingUser == null) {
            throw new NullPointerException("Initiating User cannot be null.");
        } else if (blockedUser == null) {
            throw new NullPointerException("Blocked User cannot be null.");
        } else if (!blockedUsers.containsKey(initiatingUser)) {
            throw new IllegalArgumentException("Initiating User: " + initiatingUser + " not found.");
        } else if (!users.contains(blockedUser)) {
            throw new IllegalArgumentException("Blocked User: " + blockedUser + " not found.");
        }

        blockedUsers.get(initiatingUser).add(blockedUser);
        System.out.println(initiatingUser.getUsername() + " has blocked messages from " + blockedUser.getUsername());
    }
}
