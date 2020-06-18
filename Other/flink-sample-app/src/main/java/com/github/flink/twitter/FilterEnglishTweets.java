package com.github.flink.twitter;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.hadoop.hdfs.server.namenode.ha.proto.HAZKInfoProtos;

public class FilterEnglishTweets {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    }
}

class Tweet {
    private String language;
    private String text;

    public Tweet(String language, String text) {
        this.language = language;
        this.text = text;
    }

    public String getLanguage() {
        return language;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Tweet: " + language + " " + text;
    }
}
