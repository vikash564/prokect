package com.observer.pattern.service;


public interface DataPushHandler<T> {
    void handle(T t);
}