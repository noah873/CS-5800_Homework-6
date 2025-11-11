package chat_app_test;

import org.junit.Test;
import static org.junit.Assert.*;

import chat_app.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServerTest {
    @Test
    public void testChatServerConstructor_WithValidConditions() {
        ChatServer chatServer = new ChatServer();
        assertNotNull(chatServer);
    }

    @Test
    public void testGetUsers_WithValidUsers() {
        ChatServer chatServer = new ChatServer();
        ChatServerMediator mediator = new ChatServer();
        User john = (new User("John", mediator));
        chatServer.registerUser(john);
        assertTrue(chatServer.getUsers().contains(john));
    }

    @Test
    public void testGetBlockedUsers_WithValidBlockedUsers() {
        ChatServer chatServer = new ChatServer();
        ChatServerMediator mediator = new ChatServer();
        User john = (new User("John", mediator));
        chatServer.registerUser(john);
        assertNotNull(chatServer.getBlockedUsers().get(john));
    }

    @Test
    public void testSetUsers_WithValidUsers() {
        ChatServer chatServer = new ChatServer();
        ChatServerMediator mediator = new ChatServer();
        User john = (new User("John", mediator));
        List<User> users = new ArrayList<>();
        users.add(john);
        chatServer.setUsers(users);
        assertTrue(chatServer.getUsers().contains(john));
    }

    @Test (expected = NullPointerException.class)
    public void testSetUsers_WithNullUsers() {
        ChatServer chatServer = new ChatServer();
        chatServer.setUsers(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetUsers_WithEmptyUsers() {
        ChatServer chatServer = new ChatServer();
        List<User> users = new ArrayList<>();
        chatServer.setUsers(users);
    }

    @Test
    public void testSetBlockedUsers_WithValidBlockedUsers() {
        ChatServer chatServer = new ChatServer();
        ChatServerMediator mediator = new ChatServer();
        User john = new User("John", mediator);

        Map<User, List<User>> blockedUsers = new HashMap<>();
        blockedUsers.put(john, new ArrayList<>());
        chatServer.setBlockedUsers(blockedUsers);
        assertEquals(blockedUsers, chatServer.getBlockedUsers());
    }

    @Test (expected = NullPointerException.class)
    public void testSetBlockedUsers_WithNullBlockedUsers() {
        ChatServer chatServer = new ChatServer();
        chatServer.setBlockedUsers(null);
    }

    @Test
    public void testRegisterUsers_WithValidUser() {
        ChatServer chatServer = new ChatServer();
        User john = new User("John", chatServer);
        chatServer.registerUser(john);
    }

    @Test (expected = NullPointerException.class)
    public void testRegisterUsers_WithNullUser() {
        ChatServer chatServer = new ChatServer();
        chatServer.registerUser(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testRegisterUsers_WithAlreadyRegisteredUser() {
        ChatServer chatServer = new ChatServer();
        User john = new User("John", chatServer);
        chatServer.registerUser(john);
        chatServer.registerUser(john);
    }

    @Test
    public void testUnregisterUsers_WithValidUser() {
        ChatServer chatServer = new ChatServer();
        User john = new User("John", chatServer);
        chatServer.registerUser(john);
        chatServer.unregisterUser(john);
    }

    @Test (expected = NullPointerException.class)
    public void testUnregisterUsers_WithNullUser() {
        ChatServer chatServer = new ChatServer();
        User john = new User("John", chatServer);
        chatServer.registerUser(john);
        chatServer.unregisterUser(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testUnregisterUsers_WithInvalidUser() {
        ChatServer chatServer = new ChatServer();
        User john = new User("John", chatServer);
        chatServer.unregisterUser(john);
    }

    @Test
    public void testSendMessage_WithValidConditions() {
        ChatServer chatServer = new ChatServer();

        User john = new User("John", chatServer);
        User james = new User("James", chatServer);
        User tom = new User("Tom", chatServer);

        chatServer.registerUser(john);
        chatServer.registerUser(james);
        chatServer.registerUser(tom);

        chatServer.sendMessage(new Message(john, new User[]{james}, "Test"));
    }

    @Test (expected = NullPointerException.class)
    public void testSendMessage_WithNullMessage() {
        ChatServer chatServer = new ChatServer();

        User john = new User("John", chatServer);
        User james = new User("James", chatServer);
        User tom = new User("Tom", chatServer);

        chatServer.registerUser(john);
        chatServer.registerUser(james);
        chatServer.registerUser(tom);

        chatServer.sendMessage(new Message(john, new User[]{james}, null));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSendMessage_WithSendingUserNotFound() {
        ChatServer chatServer = new ChatServer();

        User john = new User("John", chatServer);
        User james = new User("James", chatServer);
        User tom = new User("Tom", chatServer);

        chatServer.registerUser(john);
        chatServer.registerUser(james);

        chatServer.sendMessage(new Message(tom, new User[]{james}, "Test"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSendMessage_WithReceivingUserNotFound() {
        ChatServer chatServer = new ChatServer();

        User john = new User("John", chatServer);
        User james = new User("James", chatServer);
        User tom = new User("Tom", chatServer);

        chatServer.registerUser(john);
        chatServer.registerUser(tom);

        chatServer.sendMessage(new Message(tom, new User[]{james}, "Test"));
    }

    @Test
    public void testBlockMessages_WithValidConditions() {
        ChatServer chatServer = new ChatServer();

        User john = new User("John", chatServer);
        User james = new User("James", chatServer);
        User tom = new User("Tom", chatServer);

        chatServer.registerUser(john);
        chatServer.registerUser(james);
        chatServer.registerUser(tom);

        chatServer.blockMessages(john, james);
    }

    @Test (expected = NullPointerException.class)
    public void testBlockMessages_WithNullInitiatingUser() {
        ChatServer chatServer = new ChatServer();

        User john = new User("John", chatServer);
        User james = new User("James", chatServer);
        User tom = new User("Tom", chatServer);

        chatServer.registerUser(john);
        chatServer.registerUser(james);
        chatServer.registerUser(tom);

        chatServer.blockMessages(null, james);
    }

    @Test (expected = NullPointerException.class)
    public void testBlockMessages_WithNullBlockedUser() {
        ChatServer chatServer = new ChatServer();

        User john = new User("John", chatServer);
        User james = new User("James", chatServer);
        User tom = new User("Tom", chatServer);

        chatServer.registerUser(john);
        chatServer.registerUser(james);
        chatServer.registerUser(tom);

        chatServer.blockMessages(john, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBlockMessages_WithInvalidInitiatingUser() {
        ChatServer chatServer = new ChatServer();

        User john = new User("John", chatServer);
        User james = new User("James", chatServer);
        User tom = new User("Tom", chatServer);

        chatServer.registerUser(james);
        chatServer.registerUser(tom);

        chatServer.blockMessages(john, james);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBlockMessages_WithInvalidBlockedUser() {
        ChatServer chatServer = new ChatServer();

        User john = new User("John", chatServer);
        User james = new User("James", chatServer);
        User tom = new User("Tom", chatServer);

        chatServer.registerUser(john);
        chatServer.registerUser(tom);

        chatServer.blockMessages(john, james);
    }
}
