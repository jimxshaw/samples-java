package com.github.flink;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class RoundUp {
    public static void main(String[] args) throws Exception {
        // Anatomy of a Flink program
        // Execution environment: local or remote.
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Initial data: load or create.
        DataStream<Long> dataStream = env.socketTextStream("localhost", 9999)
                .filter(new FilterStrings.Filter())
                .map(new Round());

        // Transformations: slice and dice data in difference ways.

        // Save results: file or stream.
        // This serves as our data sink.
        // WARNING: Open the socket where we'll write controlled messages.
        // The application will crash if the socket isn't open.
        // Open up Terminal or CMD and issue the command:
        // nc -l [port]
        // The l flag means the nc command will not try to connect anywhere but
        // instead it will open a socket and will wait for incoming connections.
        // The second parameter is the port number that nc should use.
        // All messages are ignored by default so you'll see no output.
        dataStream.print();

        // Execute: trigger execution.
        env.execute();
    }

    // This generic data type specifies both the input and output
    // of our filter operation.
    public static class Filter implements FilterFunction<String> {

        // Return value of true if the entity is to be included in
        // the resultant stream, otherwise false.
        @Override
        public boolean filter(String input) throws Exception {
            try {
                Double.parseDouble(input.trim());

                return true;
            }
            catch(Exception ignored) {

            }

            return false;
        }
    }

    // States the input type and the output type
    // after the map operation has been applied.
    public static class Round implements MapFunction<String, Long> {

        @Override
        public Long map(String input) throws Exception {
            double d = Double.parseDouble(input.trim());

            return Math.round(d);
        }
    }

}
