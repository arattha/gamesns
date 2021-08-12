package com.web.curation.model.matching;

import java.util.Objects;

import lombok.Data;

@Data
public class MatchingRequest {

    private String sessionId;
    private String gameName;

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchingRequest)) {
            return false;
        }

        MatchingRequest that = (MatchingRequest) o;
        return Objects.equals(this.sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return sessionId.hashCode();
    }

    @Override
	public String toString() {
		return "MatchingRequest [sessionId=" + sessionId + ", gameName=" + gameName + "]";
	}

	public MatchingRequest(String sessionId, String gameName) {
		super();
		this.sessionId = sessionId;
		this.gameName = gameName;
	}
}
