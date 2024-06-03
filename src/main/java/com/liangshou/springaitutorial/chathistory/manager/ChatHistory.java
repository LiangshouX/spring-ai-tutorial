package com.liangshou.springaitutorial.chathistory.manager;

import org.springframework.ai.chat.messages.Message;

import java.util.List;

/**
 * 表示聊天历史记录的接口
 *
 * @author X-L-S
 */
public interface ChatHistory {

    /**
     * 每个历史记录都有一个唯一的 ID，
     * 此方法用于返回历史记录的 ID
     */
    String getId ();

    /**
     * 往历史记录中添加新的消息
     *
     * @param message 表示添加消息的 Message 对象
     */
    void add (Message message);

    /**
     * 获取当前的全部历史消息
     */
    List<Message> getMessages ();

    /**
     * 清空全部的历史消息
     */
    void clear ();
}
