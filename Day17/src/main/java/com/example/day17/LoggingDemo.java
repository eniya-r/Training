
package com.example.day17;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingDemo {
    private static final Logger log = LoggerFactory.getLogger(LoggingDemo.class);

    public static void main(String[] args) {
        log.info("Hello, logging world!");
        log.debug("This is a DEBUG message");
        log.warn("This is a WARN message");
        log.error("This is an ERROR message");
    }
}
