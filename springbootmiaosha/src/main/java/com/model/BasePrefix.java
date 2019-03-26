package com.model;

public abstract class BasePrefix implements KeyPrefix {
    private int expireSeconds;
    private String prefix;

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix) {
         this(0,prefix);
    }

    @Override
    public int expireSeconds() { //0表示永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

}
