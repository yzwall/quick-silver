package com.yuanjie.quicksilver.pattern.responsibility;

import com.google.common.collect.Lists;
import com.yuanjie.quicksilver.pattern.responsibility.handler.AbstractHandler;

import java.util.List;
import java.util.Optional;

public class ArrayListRespChain implements IRespChain<Object, String> {

    private List<AbstractHandler<Object, String>> chain = Lists.newArrayList();

    private ArrayListRespChain() {
    }

    public static ArrayListRespChain of() {
        return new ArrayListRespChain();
    }

    @Override
    public boolean addHandler(AbstractHandler<Object, String> handler) {
        return chain.add(handler);
    }

    /**
     * execute one by one until last handler finished.
     *
     * @param context
     * @return
     */
    @Override
    public Optional<String> handle(Object context) {
        for (int i = 0; i < chain.size(); i++) {
            Optional<String> result = chain.get(i).handleTemplate(context);
            if (i == chain.size() - 1) {
                return result;
            }
        }
        return Optional.empty();
    }
}
