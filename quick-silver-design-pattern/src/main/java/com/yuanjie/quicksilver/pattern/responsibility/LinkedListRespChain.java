package com.yuanjie.quicksilver.pattern.responsibility;

import com.yuanjie.quicksilver.pattern.responsibility.handler.AbstractHandler;
import com.yuanjie.quicksilver.pattern.responsibility.handler.AbstractLinkedListHandler;

import java.util.Objects;
import java.util.Optional;

public class LinkedListRespChain implements IRespChain<Object, String> {

    private AbstractLinkedListHandler<Object, String> head;

    private AbstractLinkedListHandler<Object, String> tail;

    private LinkedListRespChain() {
    }

    public static LinkedListRespChain of() {
        return new LinkedListRespChain();
    }

    @Override
    public boolean addHandler(AbstractHandler<Object, String> handler) {
        if (!(handler instanceof AbstractLinkedListHandler)) {
            throw new IllegalArgumentException("handler's type must be AbstractLinkedListHandler. handler type:" + handler.getClass().getSimpleName());
        }
        AbstractLinkedListHandler linkedListHandler = (AbstractLinkedListHandler)handler;
        if (Objects.isNull(head)) {
            head = linkedListHandler;
            tail = head;
        } else {
            tail.setNext(linkedListHandler);
            tail = linkedListHandler;
        }
        return true;
    }

    @Override
    public Optional<String> handle(Object context) {
        return head.handleTemplate(context);
    }
}
