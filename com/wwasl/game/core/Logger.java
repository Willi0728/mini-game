package com.wwasl.game.core;

import java.io.PrintStream;
import java.time.Instant;
import java.util.Date;

public class Logger extends PrintStream {
    private Logger(PrintStream fd) { super(fd); }
    {
        System.setOut(new Logger(System.out));
        System.setErr(new Logger(System.err));
    }
    @Override
    public void println(String x) {
        super.println(getTimestamp() + x);
    }

    @Override
    public void println(int x) {
        super.println(getTimestamp() + x);
    }

    private String getTimestamp() {
        return Date.from(Instant.now()).toString();
    }
}
