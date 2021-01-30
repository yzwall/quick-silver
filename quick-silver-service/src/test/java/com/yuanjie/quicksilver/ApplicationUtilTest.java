package com.yuanjie.quicksilver;

import com.yuanjie.quicksilver.service.IResultChecker;
import com.yuanjie.quicksilver.service.impl.DefaultResultChecker;
import com.yuanjie.quicksilver.util.ApplicationUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author yuanjie(qinhua)
 * @date 2021/01/21
 */
@SpringBootTest(classes = {
    DefaultResultChecker.class,
    ApplicationUtil.class
})
public class ApplicationUtilTest extends AbstractSpringBootTest {

    @Autowired
    ApplicationUtil applicationUtil;

    @Autowired
    DefaultResultChecker defaultResultChecker;

    @Test
    public void throwNoExceptionWhenGetBean() {

        assertThat(applicationUtil).isNotNull();

        DefaultResultChecker checker1 =
            applicationUtil.getBeanByName("defaultResultChecker", DefaultResultChecker.class).orElse(null);

        assertThat(checker1).isNotNull();
        assertThat(checker1 == defaultResultChecker).isTrue();

        DefaultResultChecker checker2 =
            applicationUtil.getBeanByType(DefaultResultChecker.class).orElse(null);
        assertThat(checker2).isNotNull();
        assertThat(checker2 == defaultResultChecker).isTrue();


        IResultChecker checker3 =
            applicationUtil.getBeanByType(IResultChecker.class).orElse(null);
        assertThat(checker3).isNotNull();
        assertThat(checker3 == defaultResultChecker).isTrue();
        assertThat(checker3.getClass() == DefaultResultChecker.class).isTrue();
    }
}