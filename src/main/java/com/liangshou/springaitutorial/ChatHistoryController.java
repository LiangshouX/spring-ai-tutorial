package com.liangshou.springaitutorial;

import com.liangshou.springaitutorial.chathistory.ChatHistoryService;
import com.liangshou.springaitutorial.chathistory.common.ChatRequest;
import com.liangshou.springaitutorial.chathistory.common.ChatResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author X-L-S
 */
@RestController
@RequestMapping("/api")
public class ChatHistoryController {

    private final ChatHistoryService chatHistoryService;

    public ChatHistoryController(ChatHistoryService chatHistoryService) {
        this.chatHistoryService = chatHistoryService;
    }

    @PostMapping(
            value = "/chat",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ChatResponse chat (
            @RequestHeader(value = "history-id", required = false) String historyMessageId,
            @RequestBody ChatRequest chatRequest) {
        return chatHistoryService.chat(chatRequest, historyMessageId);
    }
}
