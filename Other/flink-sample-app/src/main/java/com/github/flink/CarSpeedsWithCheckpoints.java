package com.github.flink;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class CarSpeedsWithCheckpoints {
    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        // Checkpointing is disabled by default unless you
        // enable it and pass in a time in milliseconds.
        // This is the checkpoint interval.
        env.enableCheckpointing(1000);

        // We don't want the transformations to be applied to the streaming
        // entities more than once.
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);

        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);

        // Any backend can be configured to store this checkpoint.
        env.setStateBackend(new FsStateBackend("file:///tmp/flink/checkpoints"));

        DataStream<String> dataStream = StreamUtil.getDataStream(env, params);

        if (dataStream == null) {
            System.exit(1);

            return;
        }

        DataStream<String> averageViewStream = dataStream.map(new Speed())
                                                        .keyBy(0)
                                                        .flatMap(new AverageSpeed());

        env.execute("Car Speeds");
    }

    public static class Speed implements MapFunction<String, Tuple2<Integer, Double>> {

        @Override
        public Tuple2<Integer, Double> map(String row) throws Exception {
            try {
                return Tuple2.of(1, Double.parseDouble(row));
            }
            catch (Exception ex) {
                System.out.println(ex);
            }

            return null;
        }
    }

    public static class AverageSpeed extends RichFlatMapFunction<Tuple2<Integer, Double>, String> {
        private transient ValueState<Tuple2<Integer, Double>> countSum;

        @Override
        public void flatMap(Tuple2<Integer, Double> input, Collector<String> out) throws Exception {
            // Access the state value.
            Tuple2<Integer, Double> currentCountSum = countSum.value();

            // Print out the message if the car exceeds the speed limit.
            if (input.f1 >= 65) {
                out.collect(String.format(
                        "EXCEEDED! The average speed of the last %s car(s) was %s," + "your speed is %s",
                        currentCountSum.f0,
                        currentCountSum.f1 / currentCountSum.f0,
                        input.f1));

                // Each time we encounter a speeding car, reset the cumulative
                // and count each time we encounter a speeding car.
                countSum.clear();
                currentCountSum = countSum.value();
            }
            else {
                out.collect("Thank you for staying under the speed limit!");
            }

            // Update the count.
            currentCountSum.f0 += 1;

            // Add the current speed of the car.
            currentCountSum.f1 += input.f1;
        }

        public void open(Configuration config) {
            // This allows us to initialize a value state.
            ValueStateDescriptor<Tuple2<Integer, Double>> descriptor = new ValueStateDescriptor<>(
                    /* The state name. */
                    "carAverageSpeed",
                    /* Type information. */
                    TypeInformation.of(new TypeHint<Tuple2<Integer, Double>>() {
                    }),
                    Tuple2.of(0, 0.0) /* Default value of the state if nothing was set. */
            );

            countSum = getRuntimeContext().getState(descriptor);
        }
    }
}
