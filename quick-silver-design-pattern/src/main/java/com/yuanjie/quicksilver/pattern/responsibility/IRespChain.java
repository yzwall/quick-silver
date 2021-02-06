package com.yuanjie.quicksilver.pattern.responsibility;

import com.yuanjie.quicksilver.pattern.responsibility.handler.AbstractHandler;

import java.util.Optional;

public interface IRespChain<P, R> {

    boolean addHandler(AbstractHandler<P, R> handler);

    Optional<R> handle(P context);
}
