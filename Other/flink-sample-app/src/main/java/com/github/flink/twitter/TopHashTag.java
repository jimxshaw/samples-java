package com.github.flink.twitter;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;
import org.apache.flink.util.Collector;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;

public class TopHashTag {
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

        env.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);

        // We need to convert a stream of tweets into a stream of hashtags.
        // We use a flatmap to allow us to convert every incoming item into
        // zero, one or more result elements. The type parameter is input
        // type and output type. The input type is hashtag name and an integer.
        env.addSource(new TwitterSource(props))
                .map(new MapToTweet())
                .flatMap(new FlatMapFunction<Tweet, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(Tweet tweet, Collector<Tuple2<String, Integer>> collector) throws Exception {
                        // To create a stream of tags, we need to iterate over a list of tags and it
                        // would output a tuple for every hashtag using the collector object.
                        for (String tag : tweet.getTags()) {
                            // We use the integer, in this case 1, to sum them later using
                            // convenient Flink functions to get a count of tags.
                            collector.collect(new Tuple2<>(tag, 1));
                        }
                    }
                })
                // Create a logical stream for every hashtag.
                .keyBy(0)
                // Tumbling window of 10 minutes.
                .timeWindow(Time.minutes(10))
                // Sum every tag's integer value, in this case 1, in all
                // tuples with the same hashtag. Since each tuple's integer value is 1, this
                // can easily count how many times a hashtag has been encountered
                // in the time interval.
                .sum(1)
                .print();

        env.execute();
    }
}
