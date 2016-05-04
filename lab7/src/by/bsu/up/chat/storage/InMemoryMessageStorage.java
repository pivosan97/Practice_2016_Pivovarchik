package by.bsu.up.chat.storage;

import by.bsu.up.chat.common.models.Message;
import by.bsu.up.chat.logging.Logger;
import by.bsu.up.chat.logging.impl.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class InMemoryMessageStorage implements MessageStorage {

    private static final String DEFAULT_PERSISTENCE_FILE = "messages.txt";

    private static final Logger logger = Log.create(InMemoryMessageStorage.class);

    private List<Message> messages = new ArrayList<>();

    public InMemoryMessageStorage(){
        loadFromFile();
    }

    private void saveToFile(){
        PrintWriter out;

        try{
            out = new PrintWriter(new FileWriter(DEFAULT_PERSISTENCE_FILE));
        }catch (IOException err){
            logger.error("Failed to create history file", err);
            return;
        }

        for(Message msg : messages){
            out.print(msg + "@");
        }

        out.close();
    }

    private void loadFromFile(){
        Scanner in;

        messages.clear();

        try{
            in = new Scanner(new File(DEFAULT_PERSISTENCE_FILE));
        }catch (IOException err){
            logger.info("Failed to open history file, empty storage initialized");
            return;
        }

        StringBuilder buf = new StringBuilder();
        while(in.hasNext()){
            buf.append(in.nextLine());
        }

        try{
            StringTokenizer tokenizer = new StringTokenizer(buf.toString(), "@", false);
            while (tokenizer.hasMoreTokens()) {
                messages.add(new Message(tokenizer.nextToken()));
            }

            logger.info("Load history from file");
        } catch (Exception err) {
            logger.error("Failed to parsehistory file", err);
        }
    }

    @Override
    public synchronized List<Message> getPortion(Portion portion) {
        int from = portion.getFromIndex();
        if (from < 0) {
            throw new IllegalArgumentException(String.format("Portion from index %d can not be less then 0", from));
        }
        int to = portion.getToIndex();
        if (to != -1 && to < portion.getFromIndex()) {
            throw new IllegalArgumentException(String.format("Porting last index %d can not be less then start index %d", to, from));
        }
        to = Math.max(to, messages.size());
        return messages.subList(from, to);
    }

    @Override
    public boolean addMessage(Message message) {
        for(Message msg : messages){
            if(msg.getId().compareTo(message.getId()) == 0){
                logger.info("Failed to add msg (Such id is already exist)");
                return false;
            }
        }
        messages.add(message);
        message.setStatus(Message.NEW);
        saveToFile();
        return true;
    }

    @Override
    public boolean updateMessage(String messageId, String messageText) {
        for(Message msg : messages){
            if(msg.getId().compareTo(messageId) == 0){
                if(msg.getStatus().compareTo(Message.REMOVED) == 0){
                    logger.info("Failed to update msg (This msg is removed)");
                    return false;
                }

                msg.setText(messageText);
                msg.setStatus(Message.EDDITED);
                saveToFile();
                return true;
            }
        }

        logger.info("Failed to update msg (Such id is not exist)");
        return false;
    }

    @Override
    public synchronized boolean removeMessage(String messageId) {
        for(Message msg : messages){
            if(msg.getId().compareTo(messageId) == 0){
                msg.setText("");
                msg.setStatus(Message.REMOVED);
                saveToFile();
                return true;
            }
        }

        logger.info("Failed to remove msg (Such id is not exist)");
        return false;
    }

    @Override
    public int size() {
        return messages.size();
    }
}
