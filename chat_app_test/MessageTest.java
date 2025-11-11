package chat_app_test;

import org.junit.Test;
import static org.junit.Assert.*;

import chat_app.*;

public class MessageTest {
    private ChatServer chatServer = new ChatServer();
    private User john = new User("John", chatServer);
    private User james = new User("James", chatServer);
    private User tom = new User("Tom", chatServer);

    @Test
    public void testMessageConstructor_WithValidInputs() {
        Message message = new Message(john, new User[]{james, tom}, "Test Message Content");
        assertEquals(john, message.getSender());
        assertEquals(new User[]{james, tom}, message.getRecipients());
        assertEquals("Test Message Content", message.getMessageContent());
    }

    @Test
    public void testGetSender_WithValidSender() {
        Message message = new Message(john, new User[]{james, tom}, "Test Message Content");
        assertEquals(john, message.getSender());
    }

    @Test
    public void testGetRecipients_WithValidRecipients() {
        Message message = new Message(john, new User[]{james, tom}, "Test Message Content");
        assertEquals(new User[]{james, tom}, message.getRecipients());
    }

    @Test
    public void testGetTimestamp_WithValidTimestamp() {
        Message message = new Message(john, new User[]{james, tom}, "Test Message Content");
        message.setTimestamp(0);
        assertEquals(0, message.getTimestamp());
    }

    @Test
    public void testGetMessageContent_WithValidMessageContent() {
        Message message = new Message(john, new User[]{james, tom}, "Test Message Content");
        assertEquals("Test Message Content", message.getMessageContent());
    }

    @Test
    public void testSetSender_WithValidSender() {
        Message message = new Message(john, new User[]{james, tom}, "Test Message Content");
        message.setSender(james);
        assertEquals(james, message.getSender());
    }

    @Test (expected = NullPointerException.class)
    public void testSetSender_WithNullSender() {
        Message message = new Message(john, new User[]{james, tom}, "Test Message Content");
        message.setSender(null);
    }

    @Test
    public void testSetRecipients_WithValidRecipients() {
        Message message = new Message(john, new User[]{james, tom}, "Test Message Content");
        message.setRecipients(new User[]{tom});
        assertEquals(new User[]{tom}, message.getRecipients());
    }

    @Test (expected = NullPointerException.class)
    public void testSetRecipients_WithNullRecipients() {
        Message message = new Message(john, new User[]{james, tom}, "Test Message Content");
        message.setRecipients(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetRecipients_WithEmptyRecipients() {
        Message message = new Message(john, new User[]{james}, "Test Message Content");
        message.setRecipients(new User[]{});
    }

    @Test (expected = NullPointerException.class)
    public void testSetRecipients_WithNullIndividualRecipient() {
        Message message = new Message(john, new User[]{james, tom}, "Test Message Content");
        message.setRecipients(new User[]{james, null});
    }

    @Test
    public void testSetMessageContent_WithValidMessageContent() {
        Message message = new Message(john, new User[]{james}, "Test Message Content");
        message.setMessageContent("New Test Message Content");
        assertEquals("New Test Message Content", message.getMessageContent());
    }

    @Test (expected = NullPointerException.class)
    public void testSetMessageContent_WithNullMessageContent() {
        Message message = new Message(john, new User[]{james}, "Test Message Content");
        message.setMessageContent(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetMessageContent_WithEmptyMessageContent() {
        Message message = new Message(john, new User[]{james}, "Test Message Content");
        message.setMessageContent("");
    }

    @Test
    public void testSetTimestamp_WithValidTimestamp() {
        Message message = new Message(john, new User[]{james, tom}, "Test Message Content");
        message.setTimestamp(10000);
        assertEquals(10000, message.getTimestamp());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetTimestamp_WithNegativeTimestamp() {
        Message message = new Message(john, new User[]{james, tom}, "Test Message Content");
        message.setTimestamp(-1);
    }

    @Test
    public void testRestoreMemento_WithValidMemento() {
        Message message = new Message(john, new User[]{james, tom}, "Test Message Content");
        message.setTimestamp(100000000);
        MessageMemento memento = message.createMemento();
        message.setSender(james);
        message.setMessageContent("Altered Message Content");
        message.setTimestamp(0);
        message.restoreMemento(memento);
        assertEquals(john, message.getSender());
        assertEquals(new User[]{james, tom}, message.getRecipients());
        assertEquals("Test Message Content", message.getMessageContent());
    }

    @Test (expected = NullPointerException.class)
    public void testRestoreMemento_WithNullMemento() {
        Message message = new Message(john, new User[]{james, tom}, "Test Message Content");
        message.setTimestamp(100000000);
        MessageMemento memento = message.createMemento();
        message.setSender(james);
        message.setMessageContent("Altered Message Content");
        message.setTimestamp(0);
        message.restoreMemento(null);
    }
}
