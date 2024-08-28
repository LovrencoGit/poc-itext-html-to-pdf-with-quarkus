package org.acme.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ResourceUtils {
    private ResourceUtils(){}


    public static String getResourceAsString(String resourcePath){
        return getResourceAsString(resourcePath, StandardCharsets.UTF_8);
    }
    public static String getResourceAsString(String resourcePath, Charset charsets){
        InputStream templateInputStream = ResourceUtils.class.getResourceAsStream(resourcePath);    // es. "/templates/ita/DettaglioRichiamoMerci.html"
        try {
            return new String(templateInputStream.readAllBytes(), charsets);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
