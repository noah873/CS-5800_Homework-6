package chat_app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatHistory implements IterableByUser {
    List<MessageMemento> mementos;

    public ChatHistory() {
        mementos = new ArrayList<>();
    }

    public List<MessageMemento> getMementos() {
        return mementos;
    }

    public void setMementos(List<MessageMemento> mementos) {
        if (mementos == null) {
            throw new NullPointerException("Mementos cannot be null.");
        }
        this.mementos = mementos;
    }

    public void addMemento(MessageMemento memento) {
        if(memento == null) {
            throw new NullPointerException("Memento cannot be null.");
        }
        mementos.add(memento);
    }

    public MessageMemento getLastSentMessage(User user) {
        if (user == null) {
            throw new NullPointerException("User cannot be null.");
        }
        if (mementos.isEmpty()) {
            return null;
        }
        MessageMemento lastSentMessage = null;
        for (int i = this.mementos.size() - 1; i >= 0; i--) {
            MessageMemento memento = this.mementos.get(i);
            if (memento.getSavedContent()[0].equals(user)) {
                lastSentMessage = memento;
                break;
            }
        }
        return lastSentMessage;
    }

    @Override
    public Iterator<MessageMemento> iterator(User userToSearchWith) {
        if (userToSearchWith == null) {
            throw new NullPointerException("User cannot be null.");
        }
        return new SearchMessagesByUser(userToSearchWith);
    }

    private class SearchMessagesByUser implements Iterator<MessageMemento> {
        private final User user;
        private int index;

        SearchMessagesByUser(User user) {
            this.user = user;
            this.index = -1;
        }

        @Override
        public boolean hasNext() {
            while (index + 1 < mementos.size()) {
                MessageMemento memento = mementos.get(index + 1);
                if (memento.getSavedContent()[0].equals(user) || ((User) memento.getSavedContent()[0]).getUsername().equals(user.getUsername())) {
                    return true;
                }
                index++;
            }
            return false;
        }

        @Override
        public MessageMemento next() {
            index++;
            return mementos.get(index);
        }
    }
}
