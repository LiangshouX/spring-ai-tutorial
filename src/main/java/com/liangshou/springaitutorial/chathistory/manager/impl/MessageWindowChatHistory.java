package com.liangshou.springaitutorial.chathistory.manager.impl;

import com.liangshou.springaitutorial.chathistory.manager.ChatHistory;
import com.liangshou.springaitutorial.chathistory.manager.ChatHistoryStore;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ChatHistory 接口的实现，一个固定大小的滑动窗口的实现。
 * 固定大小的窗口 --> 限制包含的信息的数量，以免超过大模型的上下文窗口的限制，同时减少输入 Token 的数量
 * 滑动窗口     --> 选择最近的历史消息
 *
 * @author X-L-S
 */
public class MessageWindowChatHistory implements ChatHistory {

    private final String historyMessageId;

    private final ChatHistoryStore store;

    private final int maxMessage;

    public MessageWindowChatHistory(String historyMessageId, ChatHistoryStore store, int maxMessage) {
        this.historyMessageId = historyMessageId;
        this.store = store;
        this.maxMessage = maxMessage;
    }


    @Override
    public String getId() {
        return this.historyMessageId;
    }

    @Override
    public void add(Message message) {
        var messages = store.getMessages(historyMessageId);
        List<Message> updated = new ArrayList<>();

        if (message instanceof SystemMessage) {
            updated.add(message);
            updated.addAll(
                    messages.stream().filter(msg -> !(msg instanceof SystemMessage)).toList()
            );
        } else {
            updated.addAll(messages);
            updated.add(message);
        }

        updated = messagesWithLimit(updated);
        store.updateMessages(historyMessageId, updated);
    }

    @Override
    public List<Message> getMessages() {
        var messages = store.getMessages(historyMessageId);
        return messagesWithLimit(messages);
    }

    @Override
    public void clear() {
        store.deleteMessages(historyMessageId);
    }

    /**
     * 自定义的工具方法，用于将历史消息限定在窗口大小内
     *
     * @param messages 历史消息列表
     * @return 限制 窗口大小 的历史消息列表
     */
    private List<Message> messagesWithLimit (List<Message> messages) {
        if (messages.size() <= maxMessage) {
            return messages;
        }

        var partitioned = messages.stream()
                .collect(Collectors.partitioningBy(
                        msg -> msg instanceof SystemMessage
                ));
        var result = new ArrayList<>(partitioned.get(Boolean.TRUE));

        if (result.size() >= maxMessage) {
            return result.subList(result.size() - maxMessage, result.size());
        }

        int left = maxMessage - result.size();
        var nonSystemMessages = partitioned.get(Boolean.FALSE);
        nonSystemMessages = nonSystemMessages.subList(nonSystemMessages.size() - left, nonSystemMessages.size());
        result.addAll(nonSystemMessages);

        return result;
    }
}
