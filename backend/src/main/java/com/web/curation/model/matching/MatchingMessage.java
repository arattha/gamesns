package com.web.curation.model.matching;

public class MatchingMessage {

    private String senderSessionId;
    private String message;
    private MessageType messageType;

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getSenderSessionId() {
        return senderSessionId;
    }

    public void setSenderSessionId(String senderSessionId) {
        this.senderSessionId = senderSessionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	@Override
	public String toString() {
		return "MatchingMessage [senderSessionId=" + senderSessionId + ", message=" + message + ", messageType="
				+ messageType + "]";
	}


}
