package chat_app_test;

import org.junit.Test;
import static org.junit.Assert.*;

import chat_app.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatHistoryTest {
    private ChatServer chatServer = new ChatServer();
    private User john = new User("John", chatServer);

    @Test
    public void testChatHistoryConstructor() {
        ChatHistory chatHistory = new ChatHistory();
    }

    @Test
    public void testGetMementos_WithValidMementos() {
        ChatHistory chatHistory = new ChatHistory();
        List<MessageMemento> mementos = new ArrayList<>(){{
            add(new MessageMemento(john, "Test Message Content", 10000));
            add(new MessageMemento(john, "2nd Test Message Content", 20000));
        }};
        chatHistory.setMementos(mementos);
        assertEquals(mementos, chatHistory.getMementos());
    }

    @Test
    public void testSetMementos_WithValidMementos() {
        ChatHistory chatHistory = new ChatHistory();
        List<MessageMemento> mementos = new ArrayList<>(){{
            add(new MessageMemento(john, "Test Message Content", 10000));
            add(new MessageMemento(john, "2nd Test Message Content", 20000));
        }};
        chatHistory.setMementos(mementos);
        assertEquals(mementos, chatHistory.getMementos());
    }

    @Test (expected = NullPointerException.class)
    public void testSetMementos_WithNullMementos() {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setMementos(null);
    }

    @Test
    public void testAddMemento_WithValidMessageMemento() {
        ChatHistory chatHistory = new ChatHistory();
        MessageMemento memento = new MessageMemento(john, "Test Message Content", 10000);
        chatHistory.addMemento(memento);
        MessageMemento actualMemento = chatHistory.getMementos().get(0);
        assertEquals(memento, actualMemento);
    }

    @Test (expected = NullPointerException.class)
    public void testAddMemento_WithNullMessageMemento() {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.addMemento(null);
    }

    @Test
    public void testGetLastSentMessage_WithValidConditions() {
        ChatHistory chatHistory = new ChatHistory();
        MessageMemento memento1 = new MessageMemento(john, "Test Message Content", 10000);
        MessageMemento memento2 = new MessageMemento(john, "2nd Test Message Content", 10000);
        chatHistory.addMemento(memento1);
        chatHistory.addMemento(memento2);
        assertEquals(memento2, chatHistory.getLastSentMessage(john));
    }

    @Test (expected = NullPointerException.class)
    public void testGetLastSentMessage_WithNullUser() {
        ChatHistory chatHistory = new ChatHistory();
        MessageMemento memento1 = new MessageMemento(john, "Test Message Content", 10000);
        MessageMemento memento2 = new MessageMemento(john, "2nd Test Message Content", 10000);
        chatHistory.addMemento(memento1);
        chatHistory.addMemento(memento2);
        chatHistory.getLastSentMessage(null);
    }

    @Test
    public void testGetLastSentMessage_WithEmptyMementos() {
        ChatHistory chatHistory = new ChatHistory();
        assertEquals(null, chatHistory.getLastSentMessage(john));
    }

    @Test
    public void testIterator_WithValidConditions() {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.addMemento(new MessageMemento(john, "Test Message Content", 10000));
        chatHistory.addMemento(new MessageMemento(john, "2nd Test Message Content", 20000));
        Iterator<MessageMemento> iterator = chatHistory.iterator(john);
    }

    @Test (expected = NullPointerException.class)
    public void testIterator_WithNullConditions() {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.addMemento(new MessageMemento(john, "Test Message Content", 10000));
        chatHistory.addMemento(new MessageMemento(john, "2nd Test Message Content", 20000));
        Iterator<MessageMemento> iterator = chatHistory.iterator(null);
    }
}
