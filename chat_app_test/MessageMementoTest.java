package chat_app_test;

import org.junit.Test;
import static org.junit.Assert.*;

import chat_app.*;

public class MessageMementoTest {
    private ChatServer chatServer = new ChatServer();
    private User john = new User("John", chatServer);
    private User james = new User("James", chatServer);

    @Test
    public void testMessageMementoConstructor_WithValidInputs() {
        MessageMemento memento = new MessageMemento(john, "Test Message Content", 10000);
        Object[] savedContent = memento.getSavedContent();
        assertEquals(john, savedContent[0]);
        assertEquals("Test Message Content", savedContent[1]);
        assertEquals(10000, (long) savedContent[2]);
    }

    @Test
    public void testGetSavedContent_WithValidInputs() {
        MessageMemento memento = new MessageMemento(john, "Test Message Content", 10000);
        Object[] savedContent = memento.getSavedContent();
        assertEquals(john, savedContent[0]);
        assertEquals("Test Message Content", savedContent[1]);
        assertEquals(10000, (long) savedContent[2]);
    }

    @Test
    public void testSetSender_WithValidSender() {
        MessageMemento memento = new MessageMemento(john, "Test Message Content", 10000);
        memento.setSender(james);
        Object[] savedContent = memento.getSavedContent();
        assertEquals(james, savedContent[0]);
    }

    @Test (expected = NullPointerException.class)
    public void testSetSender_WithNullSender() {
        MessageMemento memento = new MessageMemento(john, "Test Message Content", 10000);
        memento.setSender(null);
    }

    @Test
    public void testSetMessageContent_WithValidMessageContent() {
        MessageMemento memento = new MessageMemento(john, "Test Message Content", 10000);
        memento.setMessageContent("New Message Content");
        Object[] savedContent = memento.getSavedContent();
        assertEquals("New Message Content", savedContent[1]);
    }

    @Test (expected = NullPointerException.class)
    public void testSetMessageContent_WithNullMessageContent() {
        MessageMemento memento = new MessageMemento(john, "Test Message Content", 10000);
        memento.setMessageContent(null);
    }

    @Test  (expected = IllegalArgumentException.class)
    public void testSetMessageContent_WithEmptyMessageContent() {
        MessageMemento memento = new MessageMemento(john, "Test Message Content", 10000);
        memento.setMessageContent("");
    }

    @Test
    public void testSetTimestamp_WithValidTimestamp() {
        MessageMemento memento = new MessageMemento(john, "Test Message Content", 10000);
        memento.setTimestamp(0);
        Object[] savedContent = memento.getSavedContent();
        assertEquals(0, (long) savedContent[2]);
    }

    @Test  (expected = IllegalArgumentException.class)
    public void testSetTimestamp_WithNegativeTimestamp() {
        MessageMemento memento = new MessageMemento(john, "Test Message Content", 10000);
        memento.setTimestamp(-1);
    }
}
