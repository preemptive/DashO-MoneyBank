package com.preemptive.moneybank.data;

public interface ResultCallback<T> {
    void handleResult(Result<T> result);
}
