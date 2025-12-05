package cn.netbuffer.spring.boot3.mcp.demo.mcpclient.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import java.io.Serializable;
import java.util.Map;

@Data
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum MessageType {USER, ASSISTANT, SYSTEM}

    private final MessageType messageType;
    private final String text;
    private final Map<String, Object> metaData;

    public ChatMessage(Message message) {
        this.messageType = getMessageType(message);
        this.text = message.getText();
        this.metaData = message.getMetadata();
    }

    private MessageType getMessageType(Message message) {
        if (message instanceof SystemMessage) return MessageType.SYSTEM;
        if (message instanceof UserMessage) return MessageType.USER;
        if (message instanceof AssistantMessage) return MessageType.ASSISTANT;
        throw new IllegalArgumentException("invalid message type: " + message.getClass().getName());
    }

    public Message toMessage() {
        switch (messageType) {
            case SYSTEM:
                return new SystemMessage(text);
            case ASSISTANT:
                return new AssistantMessage(text);
            case USER:
                return new UserMessage(text);
            default:
                throw new IllegalArgumentException("invalid message type: " + messageType);
        }
    }

    @JsonCreator
    public ChatMessage(
            @JsonProperty("messageType") MessageType messageType,
            @JsonProperty("text") String text,
            @JsonProperty("metaData") Map<String, Object> metaData) {
        this.messageType = messageType;
        this.text = text;
        this.metaData = metaData;
    }

}