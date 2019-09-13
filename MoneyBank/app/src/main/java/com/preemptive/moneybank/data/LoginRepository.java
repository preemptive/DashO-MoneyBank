package com.preemptive.moneybank.data;

import com.preemptive.moneybank.data.model.LoggedInUser;

public class LoginRepository {

    private static LoginRepository INSTANCE = null;

    public static LoginRepository getInstance() {
        return INSTANCE;
    }

    public static void init(LoginDataSource dataSource) {
        INSTANCE = new LoginRepository(dataSource);
    }

    private final LoginDataSource loginDataSource;
    private LoggedInUser loggedInUser = null;

    private LoginRepository(LoginDataSource loginDataSource) {
        this.loginDataSource = loginDataSource;
    }

    public void logout() {
        loggedInUser = null;
        loginDataSource.logout();
    }

    public void login(String username, String password, final ResultCallback<LoggedInUser> callback) {
        ResultCallback<LoggedInUser> setUserCallback = new ResultCallback<LoggedInUser>() {
            @Override
            public void handleResult(Result<LoggedInUser> result) {
                if (result instanceof Result.Success) {
                    loggedInUser = ((Result.Success<LoggedInUser>) result).getData();
                }
                callback.handleResult(result);
            }

        };
        loginDataSource.login(username, password, setUserCallback);
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }
}
