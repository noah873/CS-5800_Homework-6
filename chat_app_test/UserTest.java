package chat_app_test;

import org.junit.Test;
import static org.junit.Assert.*;

import chat_app.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserTest {
    @Test
    public void testUserConstructor_WithValidInputs() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        assertNotNull(john);
    }

    @Test
    public void testGetUsername_WithValidUsername() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        assertEquals("John", john.getUsername());
    }

    @Test
    public void testGetMediator_WithValidMediator() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        assertEquals(mediator, john.getMediator());
    }

    @Test
    public void testGetChatHistory_WithValidChatHistory() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);

        ChatHistory chatHistory = new ChatHistory();
        List<MessageMemento> mementos = new ArrayList<>(){{
            add(new MessageMemento(john, "Test Message Content", 10000));
            add(new MessageMemento(john, "2nd Test Message Content", 20000));
        }};
        chatHistory.setMementos(mementos);

        john.setChatHistory(chatHistory);
        assertEquals(chatHistory, john.getChatHistory());
    }

    @Test
    public void testSetUsername_WithValidUsername() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        john.setUsername("Johnny");
        assertEquals("Johnny", john.getUsername());
    }

    @Test (expected = NullPointerException.class)
    public void testSetUsername_WithNullUsername() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        john.setUsername(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetUsername_WithEmptyUsername() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        john.setUsername("");
    }

    @Test
    public void testSetMediator_WithValidMediator() {
        ChatServerMediator mediator1 = new ChatServer();
        ChatServerMediator mediator2 = new ChatServer();
        User john = new User("John", mediator1);
        john.setMediator(mediator2);
        assertEquals(mediator2, john.getMediator());
    }

    @Test (expected = NullPointerException.class)
    public void testSetMediator_WithNullMediator() {
        ChatServerMediator mediator = new ChatServer();
        User user = new User("John", mediator);
        user.setMediator(null);
    }

    @Test
    public void testSetChatHistory_WithValidChatHistory() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);

        ChatHistory chatHistory = new ChatHistory();
        List<MessageMemento> mementos = new ArrayList<>(){{
            add(new MessageMemento(john, "Test Message Content", 10000));
            add(new MessageMemento(john, "2nd Test Message Content", 20000));
        }};
        chatHistory.setMementos(mementos);

        john.setChatHistory(chatHistory);
        assertEquals(chatHistory, john.getChatHistory());
    }

    @Test (expected = NullPointerException.class)
    public void testSetChatHistory_WithNullChatHistory() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        john.setChatHistory(null);
    }

    @Test
    public void testBlockUser_WithValidUser() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        User james = new User("James", mediator);
        mediator.registerUser(john);
        mediator.registerUser(james);
        john.blockUser(james);
    }

    @Test (expected = NullPointerException.class)
    public void testBlockUser_WithNullUser() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        john.blockUser(null);
    }

    @Test
    public void testSendMessage_WithValidContentAndRecipients() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        User james = new User("James", mediator);
        mediator.registerUser(john);
        mediator.registerUser(james);
        john.sendMessage("Test", new User[]{john, james});
        assertEquals("Test", james.getChatHistory().getMementos().get(0).getSavedContent()[1]);
    }

    @Test (expected = NullPointerException.class)
    public void testSendMessage_WithNullContentAndRecipients() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        User james = new User("James", mediator);
        mediator.registerUser(john);
        mediator.registerUser(james);
        john.sendMessage(null, new User[]{john, james});
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSendMessage_WithEmptyContentAndRecipients() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        User james = new User("James", mediator);
        mediator.registerUser(john);
        mediator.registerUser(james);
        john.sendMessage("", new User[]{john, james});
    }

    @Test (expected = NullPointerException.class)
    public void testSendMessage_WithValidContentAndNullRecipients() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        User james = new User("James", mediator);
        mediator.registerUser(john);
        mediator.registerUser(james);
        john.sendMessage("Test", null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSendMessage_WithValidContentAndEmptyRecipients() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        User james = new User("James", mediator);
        mediator.registerUser(john);
        mediator.registerUser(james);
        john.sendMessage("Test", new User[]{});
    }

    @Test
    public void testReceiveMessage_WithValidMessage() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        User james = new User("James", mediator);
        mediator.registerUser(john);
        mediator.registerUser(james);
        Message message = new Message(james, new User[]{james}, "Test");
        john.receiveMessage(message);
        MessageMemento memento = john.getChatHistory().getMementos().get(0);
        Object[] savedContent = memento.getSavedContent();

        assertEquals(message.getSender(), savedContent[0]);
        assertEquals(message.getMessageContent(), savedContent[1]);
        assertEquals(message.getTimestamp(), savedContent[2]);
    }

    @Test (expected = NullPointerException.class)
    public void testReceiveMessage_WithNullMessage() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        User james = new User("James", mediator);
        mediator.registerUser(john);
        mediator.registerUser(james);
        Message message = new Message(james, new User[]{james}, "Test");
        john.receiveMessage(null);
    }

    @Test
    public void testUndoLastMessage_WithValidMessage() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        User james = new User("James", mediator);
        mediator.registerUser(john);
        mediator.registerUser(james);
        james.sendMessage("Test", new User[]{john, james});

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        james.undoLastMessage();

        System.setOut(originalOut);
        assertEquals(
                "James is undoing the last message: Test",
                outputStream.toString().trim()
        );
    }

    @Test
    public void testUndoLastMessage_WithNoMessages() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        User james = new User("James", mediator);
        mediator.registerUser(john);
        mediator.registerUser(james);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        james.undoLastMessage();

        System.setOut(originalOut);
        assertEquals(
                "No last messages to undo.",
                outputStream.toString().trim()
        );
    }

    @Test
    public void testViewChatHistory_WithValidChatHistory() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        User james = new User("James", mediator);
        mediator.registerUser(john);
        mediator.registerUser(james);
        james.sendMessage("Test", new User[]{john});
        james.getChatHistory().getMementos().get(0).setTimestamp(10000);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        james.viewChatHistory();

        System.setOut(originalOut);
        assertEquals(
                "James's Chat History:\n" +
                        "\tSender: James, Message: Test, Timestamp: 10000",
                outputStream.toString().trim()
        );
    }

    @Test
    public void testIterator_WithValidUser() {
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);
        User james = new User("James", mediator);
        mediator.registerUser(john);
        mediator.registerUser(james);

        Iterator<MessageMemento> expectedIterator = john.getChatHistory().iterator(james);
        Iterator<MessageMemento> actualIterator = john.iterator(james);

        while (expectedIterator.hasNext() && actualIterator.hasNext()) {
            MessageMemento expectedMemento = expectedIterator.next();
            MessageMemento actualMemento = actualIterator.next();

            assertEquals(expectedMemento.getSavedContent()[1], actualMemento.getSavedContent()[1]);
            assertEquals(expectedMemento.getSavedContent()[0], actualMemento.getSavedContent()[0]);
        }
    }
}
