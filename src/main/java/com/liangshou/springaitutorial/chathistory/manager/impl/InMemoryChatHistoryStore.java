package com.liangshou.springaitutorial.chathistory.manager.impl;

import com.liangshou.springaitutorial.chathistory.manager.ChatHistoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 短期历史消息存储，将消息存储在内存中
 * @author X-L-S
 */
public class InMemoryChatHistoryStore implements ChatHistoryStore {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    // 保存历史消息的 ConcurrentHashMap, 查询、更新、删除操作都通过这个对象来完成
    private final ConcurrentHashMap<String, List<Message>> messagesByHistoryId = new ConcurrentHashMap<>();

    @Override
    public List<Message> getMessages(String historyMessageId) {
        return messagesByHistoryId.computeIfAbsent(historyMessageId, id -> new ArrayList<>());
    }

    @Override
    public void updateMessages(String historyMessageId, List<Message> messages) {
        messagesByHistoryId.put(historyMessageId, messages);
    }

    @Override
    public void deleteMessages(String historyMessageId) {
        try {
            messagesByHistoryId.remove(historyMessageId);
        } catch (Exception e) {
            logger.warn("Delete message {} failed.", historyMessageId);
        }
    }
}
