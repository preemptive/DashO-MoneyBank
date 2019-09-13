package com.preemptive.moneybank.data.model;

import java.util.Objects;

public class LoggedInUser {
    private final String userId;
    private final String displayName;
    private final String authToken;

    public LoggedInUser(String userId, String displayName, String authToken) {
        this.userId = userId;
        this.displayName = displayName;
        this.authToken = authToken;
    }

    public String getUserID() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAuthToken() {
        return authToken;
    }

}
