package ru.kkiselev;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Created by kv on 14.12.16.
 */
public class MyAppender extends AppenderSkeleton {
    @Override
    protected void append(LoggingEvent event) {


        System.out.println("MyAppender MyAppender MyAppender MyAppender MyAppender " + event.getMessage());
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
