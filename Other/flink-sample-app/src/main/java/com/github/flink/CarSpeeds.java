package com.github.flink;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import scala.Int;

public class CarSpeeds {
    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataStream<String> dataStream = StreamUtil.getDataStream(env, params);

        if (dataStream == null) {
            System.exit(1);

            return;
        }

        // Find the average speed of the cars since the last
        // car that was speeding.
        // Use map operation to extract the car speeds as a number.
//        DataStream<String> averageViewStream = dataStream.map(new Speed())
//                                                        .keyBy(0)
//                                                        .flatMap(new AverageSpeedValueState());

        DataStream<String> averageViewStream = dataStream.map(new Speed())
                                                        .keyBy(0)
                                                        .flatMap(new AverageSpeedListState());

        env.execute("Car Speeds");
    }

    public static class Speed implements MapFunction<String, Tuple2<Integer, Double>> {
        // We have to convert a string to a tuple as later on the
        // flat map operation only works on Keyed Streams.
        // We generate a tuple from the incoming speeds by mapping all
        // car speeds to the same integer key.
        // All speeds have the same key so all averages are calculated on this key.
        @Override
        public Tuple2<Integer, Double> map(String row) throws Exception {
            return Tuple2.of(1, Double.parseDouble(row));
        }
    }

    public static class AverageSpeedValueState extends RichFlatMapFunction<Tuple2<Integer, Double>, String> {
        // The rich flat map's input tuple is the same tuple where all the integer keys are the same and the
        // values are the car speeds that we just encountered. The output is a string because we
        // want to display the message to the screen saying this was the average speed so far and
        // your speed is beyond the speed limit.

        // We use value state in order to store information about the cars that we've
        // seen so far, which have been under the speed limit and their corresponding
        // speeds. This stores the cumulative counts and sum of the car speeds.
        private transient ValueState<Tuple2<Integer, Double>> countSumState;
        @Override
        public void flatMap(Tuple2<Integer, Double> input, Collector<String> out) throws Exception {
            // We get the current value stored, the current count of
            // cars and the current sum of speeds.
            Tuple2<Integer, Double> currentCountSum = countSumState.value();

            if (input.f1 >= 65) {
                out.collect(String.format(
                        "EXCEEDED! The average speed of the last %s car(s) was %s," + "your speed is %s",
                        currentCountSum.f0,
                        currentCountSum.f1 / currentCountSum.f0,
                        input.f1));

                // Each time we encounter a speeding car, reset the cumulative
                // and count each time we encounter a speeding car.
                countSumState.clear();
                currentCountSum = countSumState.value();
            }
            else {
                out.collect("Thank you for staying under the speed limit!");
            }

            // Add the information about the current car on to our cumulative
            // counts and sum.
            currentCountSum.f0 += 1;

            // We implement the cumulative count and add the speed of the
            // current car to our cumulative sum. This helps us keep track
            // of the car speeds across the stream.
            currentCountSum.f1 += input.f1;

            // Update the value state with this information.
            countSumState.update(currentCountSum);
        }



        // Set up the initial values for the count and sum.
        // The Flink runtime and the rich function will ensure
        // that this open method is called before flat map is called
        // for the first time.
        public void open(Configuration config) {
            // This allows us to initialize a value state.
            ValueStateDescriptor<Tuple2<Integer, Double>> descriptor = new ValueStateDescriptor<>(
                    "carAverageSpeed",
                    TypeInformation.of(new TypeHint<Tuple2<Integer, Double>>() {
                    }),
                    Tuple2.of(0, 0.0) /* initial speed and sum of speeds */
            );

            countSumState = getRuntimeContext().getState(descriptor);
        }
    }

    public static class AverageSpeedListState extends RichFlatMapFunction<Tuple2<Integer, Double>, String> {
        private transient ListState<Double> speedListState;

        @Override
        public void flatMap(Tuple2<Integer, Double> input, Collector<String> out) throws Exception {
            if (input.f1 >= 65) {
                // This iterable holds all the car speeds since the
                // last car that exceeded the speed limit.
                Iterable<Double> carSpeeds = speedListState.get();

                int count = 0;
                double sum = 0;

                for (Double carSpeed : carSpeeds) {
                    count++;
                    sum += carSpeed;
                }

                out.collect(String.format(
                        "EXCEEDED! The average speed of the last %s car(s) was %s," + "your speed is %s",
                        count,
                        sum / count,
                        input.f1));

                speedListState.clear();
            }
            else {
                out.collect("Thank you for staying under the speed limit!");
            }

            // Since we calculate the sum and the count when the car
            // exceeds the speed limit, for every car we only need
            // to add the speed of the car to our list.
            speedListState.add(input.f1);
        }

        public void open(Configuration config) {
            // This allows us to initialize a value state.
            ListStateDescriptor<Double> descriptor = new ListStateDescriptor<>(
                    "carAverageSpeed", Double.class);

            speedListState = getRuntimeContext().getListState(descriptor);
        }
    }
}
