package com.github.flink.twitter;

import org.apache.flink.api.common.functions.MapFunction;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class MapToTweet implements MapFunction<String, Tweet> {
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
