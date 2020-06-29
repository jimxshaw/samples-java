package com.github.flink.twitter;

import com.github.flink.StreamUtil;
import com.github.flink.WordCountTumblingAndSlidingWindow;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingAlignedProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;
import org.apache.flink.util.Collector;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;

public class TwitterTumblingWindow {
    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        Yaml yaml = new Yaml();
        Reader yamlFile = new FileReader("secrets.yml");

        Map<String, Object> yamlMaps = yaml.load(yamlFile);

        Properties props = new Properties();
        props.setProperty(TwitterSource.CONSUMER_KEY, yamlMaps.get("API_KEY").toString());
        props.setProperty(TwitterSource.CONSUMER_SECRET, yamlMaps.get("API_KEY_SECRET").toString());
        props.setProperty(TwitterSource.TOKEN, yamlMaps.get("ACCESS_TOKEN").toString());
        props.setProperty(TwitterSource.TOKEN_SECRET, yamlMaps.get("ACCESS_TOKEN_SECRET").toString());

        DataStream<String> streamSource = env.addSource(new TwitterSource(props));

        // Count the number of users in a particular language in a certain time window.
        DataStream<Tuple2<String, Integer>> tweets = streamSource.flatMap(new LanguageCount())
                                                                .keyBy(0)
                                                                .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
                                                                .sum(1);

        tweets.print();

        env.execute("Twitter Tumbling Window");
    }

    public static class LanguageCount implements FlatMapFunction<String, Tuple2<String, Integer>> {
        // Every tweet arrives in a form of a string, a JSON object that has been serialized
        // to a string. We parse it and extract the information we need and output
        // a tuple with 2 values, a language code and a count of 1 associated with it.
        private transient ObjectMapper jsonParser;

        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
            // We instantiate the parse the first time and don't need to
            // keep instantiating it each time.
            if (jsonParser == null) {
                jsonParser = new ObjectMapper();
            }

            // Get the json node of the tweet's json object.
            JsonNode jsonNode = jsonParser.readValue(value, JsonNode.class);

            // Get the language of the tweet if it exists.
            String language = jsonNode.has("user") && jsonNode.get("user").has("lang")
                    ? jsonNode.get("user").get("lang").getValueAsText()
                    : "unknown";

            out.collect(new Tuple2<>(language, 1));
        }
    }
}
