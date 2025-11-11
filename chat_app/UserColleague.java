package chat_app;

public interface UserColleague {
    void sendMessage(String content, User[] recipients);
    void receiveMessage(Message message);
    void undoLastMessage();
}