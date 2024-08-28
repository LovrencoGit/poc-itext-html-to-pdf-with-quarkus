package org.acme.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class StringUtils {
    private StringUtils(){}

    public static String replace(String text, Map<String, String> replaceMap){
        return replace(text, replaceMap, null, null);
    }
    public static String replace(String text, Map<String, String> replaceMap, String keyPrefixToAdd, String keySuffixToAdd){    // , "\\{\\{", "}}"
        for(Map.Entry<String, String> entry : replaceMap.entrySet()){
            String key = entry.getKey();
            if(keyPrefixToAdd != null) key = keyPrefixToAdd+key;
            if(keySuffixToAdd != null) key = key+keySuffixToAdd;
            String value = entry.getValue();
            text = text.replace(key, value);
        }
        return text;
    }
    public static <T> String replace(String text, List<T> models, Map<String, Function<T, String>> replaceMap){
        StringBuilder outputBuilder = new StringBuilder();
        for(T model : models){
            String modelText = text;
            modelText = StringUtils.replace(modelText, model, replaceMap);
            outputBuilder.append(modelText);
        }
        return outputBuilder.toString();
    }
    public static <T> String replace(String text, T model, Map<String, Function<T, String>> replaceMap){
        for(Map.Entry<String, Function<T, String>> entry : replaceMap.entrySet()){
            String keyPlaceholder = entry.getKey();
            Function<T, ?> valueFunctionGetter = replaceMap.get(keyPlaceholder);
            String valueToReplace = (String) valueFunctionGetter.apply(model);
            text = text.replace(keyPlaceholder, valueToReplace);
        }
        return text;
    }
    public static String downloadImageAsBase64(String imageUrl){
        try{
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            byte[] imageBytes = inputStream.readAllBytes();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
