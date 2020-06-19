package com.github.flink.twitter;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
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
                .map(new MapToTweet())
                .filter(new FilterFunction<Tweet>() {
                    @Override
                    public boolean filter(Tweet tweet) throws Exception {
                        return tweet.getLanguage().equals("en");
                    }
                })
                .print();

        env.execute();
    }

    private static class MapToTweet implements MapFunction<String, Tweet> {
        static private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public Tweet map(String tweetJsonStr) throws Exception {
            JsonNode tweetJson = mapper.readTree(tweetJsonStr);
            JsonNode textNode = tweetJson.get("text");
            JsonNode langNode = tweetJson.get("lang");

            String text = textNode == null ? "" : textNode.getTextValue();
            String lang = langNode == null ? "" : langNode.getTextValue();

            return new Tweet(lang, text);
        }
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
