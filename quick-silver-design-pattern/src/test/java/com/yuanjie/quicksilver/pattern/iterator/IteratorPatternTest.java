package com.yuanjie.quicksilver.pattern.iterator;

import com.yuanjie.quicksilver.AbstractSpringBootTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IteratorPatternTest extends AbstractSpringBootTest {

    @Test
    public void returnExpectationWhenIterateArrayLineTable() {

        LineTable<Integer> array = ArrayLineTable.of();
        array.add(1, 3, 5, 7);
        assertThat(array.size()).isEqualTo(4);

        // iterate in order
        Iterator<Integer> orderIterator = array.iterator();
        assertThat(orderIterator.hasNext()).isTrue();
        int loop = 0;
        while (orderIterator.hasNext()) {
            Integer value = orderIterator.next().orElse(null);
            assertThat(value).isNotNull().isEqualTo(array.get(loop++).orElse(null));
        }

        // iterate in reverse order
        Iterator<Integer> reverseOrderIterator = array.reverseIterator();
        // update visit idx
        reverseOrderIterator.handleFirstIterateEvent();
        assertThat(reverseOrderIterator.hasNext()).isTrue();
        loop = array.size() - 1;
        while (reverseOrderIterator.hasNext()) {
            Integer value = reverseOrderIterator.next().orElse(null);
            assertThat(value).isNotNull().isEqualTo(array.get(loop--).orElse(null));
        }
    }
}
