package com.github.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class Words {
    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);

        // Anatomy of a Flink program
        // Execution environment: local or remote.
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        // Initial data: load or create.
        DataStream<String> dataStream;

        if (params.has("input")) {
            System.out.println("Executing Words example with file input");

            // Read the text file from given input path.
            dataStream = env.readTextFile(params.get("input"));
        }
        else if (params.has("host") && params.has("port")) {
            System.out.println("Executing Words example with socket stream");

            // Get default test text data.
            dataStream = env.socketTextStream(params.get("host"), Integer.parseInt(params.get("port")));
        }
        else {
            System.out.println("Use --host and --port to specify socket");
            System.out.println("Use --input and to specify file input");

            System.exit(1);

            return;
        }

        // Transformations: slice and dice data in difference ways.
        DataStream<String> wordDataStream = dataStream.flatMap(new Splitter());

        // Save results: file or stream.
        wordDataStream.print();

        // Execute: trigger execution.
        env.execute();
    }

    // Split a sentence into words.
    public static class Splitter implements FlatMapFunction<String, String> {
        // First parameter is the input streaming entity, which in our case
        // is a sentence. Second parameter is a collector, which is a utility
        // that allows you to emit a record to a stream. After a sentence is
        // broken down into words, you emit each individual word as a record
        // using the collector.
        @Override
        public void flatMap(String sentence, Collector<String> out) throws Exception {
            // Split by space then emit each word using the collector.
            for (String word : sentence.split(" ")) {
                out.collect(word);
            }
        }
    }
}
