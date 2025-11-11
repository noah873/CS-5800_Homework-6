package chat_app;

public class Message {
    private User sender;
    private User[] recipients;
    private long timestamp;
    private String messageContent;

    public Message(User sender, User[] recipients, String messageContent) {
        setSender(sender);
        setRecipients(recipients);
        setMessageContent(messageContent);
        this.timestamp = System.currentTimeMillis();
    }

    public User getSender() {
        return sender;
    }

    public User[] getRecipients() {
        return recipients;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setSender(User sender) {
        if (sender == null) {
            throw new NullPointerException("Sender cannot be null.");
        }
        this.sender = sender;
    }

    public void setRecipients(User[] recipients) {
        if (recipients == null) {
            throw new NullPointerException("Recipients cannot be null.");
        } else if (recipients.length == 0) {
            throw new IllegalArgumentException("Recipients list cannot be empty.");
        }
        for (User recipient : recipients) {
            if (recipient == null) {
                throw new NullPointerException("Individual recipient cannot be null.");
            }
        }
        this.recipients = recipients;
    }

    public void setMessageContent(String messageContent) {
        if (messageContent == null) {
            throw new NullPointerException("MessageContent cannot be null.");
        } else if (messageContent.isEmpty()) {
            throw new IllegalArgumentException("MessageContent cannot be empty.");
        }
        this.messageContent = messageContent;
    }

    public void setTimestamp(long timestamp) {
        if (timestamp < 0) {
            throw new IllegalArgumentException("Timestamp cannot be negative.");
        }
        this.timestamp = timestamp;
    }

    public MessageMemento createMemento() {
        return new MessageMemento(sender, messageContent, timestamp);
    }

    public void restoreMemento(MessageMemento memento) {
        if  (memento == null) {
            throw new NullPointerException("Memento cannot be null.");
        }
        Object[] savedContent = memento.getSavedContent();
        this.sender = (User) savedContent[0];
        this.messageContent = (String) savedContent[1];
        this.timestamp = (long) savedContent[2];
    }
}