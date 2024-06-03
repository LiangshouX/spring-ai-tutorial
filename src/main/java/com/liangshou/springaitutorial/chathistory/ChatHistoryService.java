package com.liangshou.springaitutorial.chathistory;

import com.liangshou.springaitutorial.chathistory.common.ChatRequest;
import com.liangshou.springaitutorial.chathistory.common.ChatResponse;
import com.liangshou.springaitutorial.chathistory.manager.ChatHistory;
import com.liangshou.springaitutorial.chathistory.manager.ChatHistoryStore;
import com.liangshou.springaitutorial.chathistory.manager.impl.MessageWindowChatHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;

import java.util.List;

/**
 * @author X-L-S
 */
public class ChatHistoryService {

    private final ChatClient chatClient;

    private final ChatHistoryStore chatHistoryStore;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ChatHistoryService(ChatClient chatClient, ChatHistoryStore chatHistoryStore) {
        this.chatClient = chatClient;
        this.chatHistoryStore = chatHistoryStore;
    }

    public ChatResponse chat (ChatRequest request, String historyMessageId) {
        var message = new UserMessage(request.input());
        List<Message> messages;

        ChatHistory chatHistory = historyMessageId != null ?
                new MessageWindowChatHistory(historyMessageId, chatHistoryStore, 5) : null;

        if (chatHistory != null) {
            chatHistory.add(message);
            messages = chatHistory.getMessages();
            logger.info("Add first message \"{}\" to History", message.getContent());
        }
        else {
            messages = List.of(message);
            logger.info("Chat history is null");
        }

        var prompt = new Prompt(messages);
        var chatResponse = chatClient.call(prompt);
        var outputMessage = chatResponse.getResult().getOutput();

        if (chatHistory != null) {
            chatHistory.add(outputMessage);
            logger.info("Add output messages, total Message: {}", chatHistory.getMessages().size());
        }

        return new ChatResponse(outputMessage.getContent());
    }
}
