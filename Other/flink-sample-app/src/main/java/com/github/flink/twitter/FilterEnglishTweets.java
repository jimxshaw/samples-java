package com.github.flink.twitter;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;

public class FilterEnglishTweets {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Yaml yaml = new Yaml();
        Reader yamlFile = new FileReader("secrets.yml");

        Map<String, Object> yamlMaps = yaml.load(yamlFile);

        Properties props = new Properties();
        props.setProperty(TwitterSource.CONSUMER_KEY, yamlMaps.get("API_KEY").toString());
        props.setProperty(TwitterSource.CONSUMER_SECRET, yamlMaps.get("API_KEY_SECRET").toString());
        props.setProperty(TwitterSource.TOKEN, yamlMaps.get("ACCESS_TOKEN").toString());
        props.setProperty(TwitterSource.TOKEN_SECRET, yamlMaps.get("ACCESS_TOKEN_SECRET").toString());

        env.addSource(new TwitterSource(props))
           .print();

        env.execute();
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
