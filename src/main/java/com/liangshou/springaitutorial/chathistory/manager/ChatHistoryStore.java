package com.liangshou.springaitutorial.chathistory.manager;

import org.springframework.ai.chat.messages.Message;

import java.util.List;

/**
 * 负责历史消息存储的接口
 * 根据 ChatHistory ID 来管理消息
 * @author X-L-S
 */
public interface ChatHistoryStore {

    List<Message> getMessages (String historyMessageId);

    void updateMessages (String historyMessageId, List<Message> messages);

    void deleteMessages (String historyMessageId);
}
