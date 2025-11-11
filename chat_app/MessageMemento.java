package chat_app;

public class MessageMemento {
    private User sender;
    private String messageContent;
    private long timestamp;

    public MessageMemento(User sender, String messageContent, long timestamp) {
        setSender(sender);
        setMessageContent(messageContent);
        setTimestamp(timestamp);
    }

    public Object[] getSavedContent() {
        return new Object[]{sender, messageContent, timestamp};
    }

    public void setSender(User sender) {
        if (sender == null) {
            throw new NullPointerException("Sender cannot be null.");
        }
        this.sender = sender;
    }

    public void setMessageContent(String messageContent) {
        if (messageContent == null) {
            throw new NullPointerException("Message content cannot be null.");
        } else if (messageContent.isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be empty.");
        }
        this.messageContent = messageContent;
    }

    public void setTimestamp(long timestamp) {
        if (timestamp < 0) {
            throw new IllegalArgumentException("Timestamp cannot be negative.");
        }
        this.timestamp = timestamp;
    }
}
