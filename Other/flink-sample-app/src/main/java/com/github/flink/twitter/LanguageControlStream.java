package com.github.flink.twitter;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;
import org.apache.flink.util.Collector;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;

public class LanguageControlStream {
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

        // Read from a socket.
        DataStream<LanguageConfig> controlStream = env.socketTextStream("localhost", 54055)
                                                        // It will return a stream of lines where every
                                                        // line will contain commands for one or more languages.
                                                        .flatMap(new FlatMapFunction<String, LanguageConfig>() {
                                                            // To extract these commands we use a flatmap that will
                                                            // return a stream of LanguageConfig objects.
                                                            @Override
                                                            public void flatMap(String s, Collector<LanguageConfig> collector) throws Exception {
                                                                // Split each line by comma and then split the result
                                                                // values by the equal sign to get a language code and
                                                                // a boolean value.
                                                                // E.g. "en=true,sp=false"
                                                                for (String langConfig : s.split(",")) {
                                                                    // E.g. "en=true"
                                                                    String[] kvPair = langConfig.split("=");
                                                                    collector.collect(new LanguageConfig(kvPair[0], Boolean.parseBoolean(kvPair[1])));
                                                                }
                                                            }
                                                        });

        // When we have the control stream, we can process tweets.
        env.addSource(new TwitterSource(props))
                .map(new MapToTweet())
                .keyBy(new KeySelector<Tweet, String>() {
                    // Parse tweets like before then key them by language.
                    @Override
                    public String getKey(Tweet tweet) throws Exception {
                        return tweet.getLanguage();
                    }
                })
                // Connect the control stream and the tweet stream.
                // To do this, we key by the control stream as well.
                .connect(controlStream.keyBy(new KeySelector<LanguageConfig, String>() {
                    @Override
                    public String getKey(LanguageConfig languageConfig) throws Exception {
                        return languageConfig.getLanguage();
                    }
                }))
                // What's left is to call the flatmap method that will process both
                // control messages and tweets.
                // Since we've two streams rather than one, we'll need a co-flatmap method that
                // defines first stream's input type, second stream's input type and the output type.
                // The output is a tuple of a tweet's language code and a hashtag value.
                .flatMap(new RichCoFlatMapFunction<Tweet, LanguageConfig, Tuple2<String, String>>() {
                    // If this value is true then process tweets in this language. If it's false
                    // then ignore tweets in this language.
                    ValueStateDescriptor<Boolean> shouldProcess = new ValueStateDescriptor<Boolean>(
                            "languageConfig", Boolean.class
                    );

                    // Process an item of the tweet stream.
                    @Override
                    public void flatMap1(Tweet tweet, Collector<Tuple2<String, String>> collector) throws Exception {
                        // Read the config in this flatmap method. If the boolean flag is true then we need to
                        // output the hashtags for the current tweet.
                        Boolean processLanguage = getRuntimeContext().getState(shouldProcess).value();

                        if (processLanguage != null && processLanguage) {
                            for (String tag : tweet.getTags()) {
                                collector.collect(new Tuple2<>(tweet.getLanguage(), tag));
                            }
                        }
                    }

                    // Process an item of the control stream.
                    @Override
                    public void flatMap2(LanguageConfig languageConfig, Collector<Tuple2<String, String>> collector) throws Exception {
                        // Get value of the langauge config object and store it using the update method.
                        getRuntimeContext().getState(shouldProcess).update(languageConfig.isShouldProcess());
                    }
                })
                // WARNING: Flink expects the control stream to be open
                // so open the socket where we'll write controlled messages.
                // The application will crash if the socket isn't open.
                // Open up Terminal or CMD and issue the command:
                // nc -l [port]
                // The l flag means the nc command will not try to connect anywhere but
                // instead it will open a socket and will wait for incoming connections.
                // The second parameter is the port number that nc should use.
                // All messages are ignored by default so you'll see no output.
                // To enable one or more languages for example, go back to the Terminal/CMD
                // and issue the following command while nc is open:
                // en=true
                // es=true
                // One or more languages can be disabled by issue the following command wile nc is open:
                // en=false
                // es=false
                .print();

        env.execute();
    }

    static class LanguageConfig {
        private String language;
        private boolean shouldProcess;

        public LanguageConfig(String language, boolean shouldProcess) {
            this.language = language;
            this.shouldProcess = shouldProcess;
        }

        public String getLanguage() {
            return language;
        }

        public boolean isShouldProcess() {
            return this.shouldProcess;
        }
    }
}
