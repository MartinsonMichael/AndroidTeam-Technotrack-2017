package com.technothack.michael.music;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatHistory {

    ArrayList<ChatMessage> items;
    HashMap<String, ChatMessage> itemMap;

    public ChatHistory() {
        items = new ArrayList<ChatMessage>();
        itemMap = new HashMap<String, ChatMessage>();
    }

    public void addItem(ChatMessage item) {
        this.items.add(item);
        this.itemMap.put(item.id, item);
    }

    private String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ChatMessage {
        public String id;
        public String content;
        public String details;

        public ChatMessage(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}