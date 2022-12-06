package com.devroom.utils.logs;

public class Log {

    public static void print(LogType logType, String message) {
        System.out.println("["+logType.name()+"] "+message);
    }
}
