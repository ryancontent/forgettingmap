package com.ryancontent.forgettingmap;

import java.util.concurrent.atomic.AtomicInteger;

public class Association {

    private final int key;
    private Object content;
    private AtomicInteger timesAccessed = new AtomicInteger(0);

    public Association(int key, Object content) {
        this.key = key;
        this.content = content;
    }

    public int getKey() {
        return this.key;
    }

    public Object getContent() {
        return this.content;
    }

    public int getTimesAccessed() {
        return this.timesAccessed.intValue();
    }

    public void incrementTimesAccessed() {
        this.timesAccessed.getAndIncrement();
    }

}
