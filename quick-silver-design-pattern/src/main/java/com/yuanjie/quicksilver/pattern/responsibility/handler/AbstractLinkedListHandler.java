package com.yuanjie.quicksilver.pattern.responsibility.handler;

public abstract class AbstractLinkedListHandler<P, R> extends AbstractHandler<P, R> {

    protected AbstractLinkedListHandler next;

    public void setNext(AbstractLinkedListHandler next) {
        this.next = next;
    }
}
