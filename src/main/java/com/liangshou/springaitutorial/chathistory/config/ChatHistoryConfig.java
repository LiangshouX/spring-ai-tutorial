package com.liangshou.springaitutorial.chathistory.config;

import com.liangshou.springaitutorial.chathistory.ChatHistoryService;
import com.liangshou.springaitutorial.chathistory.manager.ChatHistoryStore;
import com.liangshou.springaitutorial.chathistory.manager.impl.InMemoryChatHistoryStore;
import io.github.alexcheng1982.springai.dashscope.DashscopeChatClient;
import org.springframework.ai.chat.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author X-L-S
 */

@Configuration
public class ChatHistoryConfig {

    @Bean
    public ChatClient chatClient () {
        return DashscopeChatClient.createDefault();
    }

    @Bean
    public ChatHistoryStore chatHistoryStore () {
        return new InMemoryChatHistoryStore();
    }

    @Bean
    public ChatHistoryService chatHistoryService (ChatClient chatClient, ChatHistoryStore chatHistoryStore) {
        return new ChatHistoryService(chatClient, chatHistoryStore);
    }
}
