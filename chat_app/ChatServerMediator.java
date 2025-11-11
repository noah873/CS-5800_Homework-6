package chat_app;

public interface ChatServerMediator {
    void registerUser(User user);
    void unregisterUser(User user);
    void sendMessage(Message message);
    void blockMessages(User initiatingUser, User blockedUser);
}
