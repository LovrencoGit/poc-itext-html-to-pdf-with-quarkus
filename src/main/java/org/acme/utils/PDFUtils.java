package org.acme.utils;

import com.lowagie.text.DocumentException;
import org.acme.enumerations.HTMLMarkerEnum;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class PDFUtils {
    private PDFUtils(){}

    public static ByteArrayOutputStream generatePDF(String htmlContent){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        try {
            renderer.createPDF(outputStream, true);
        } catch (DocumentException e) {
            throw new RuntimeException(e); //TODO handle error
        }
        return outputStream;
    }

    public static String resolvePlaceholder(String html, Map<String, String> placeholderReplaceMap){
        return StringUtils.replace(html, placeholderReplaceMap);
    }

    public static <T> String resolveMarkerFOR(String html, String templateResourceFilePath, String templateResourceFilename, List<T> items, Map<String, Function<T, String>> placeholderReplaceMap){
        String templatePath = templateResourceFilename;
        if( templateResourceFilePath != null )
            templatePath = templateResourceFilePath + /*File.separator*/ "/" + templateResourceFilename; //TODO handle "separator" correctly
        String htmlTemplateWithPlaceholder = ResourceUtils.getResourceAsString(templatePath);
        String htmlTemplatePlaceholderResolved = StringUtils.replace(htmlTemplateWithPlaceholder, items, placeholderReplaceMap);
        String markerFor = "{{"+HTMLMarkerEnum.FOR.marker+":"+templateResourceFilename+"}}";
        html = html.replace(markerFor, htmlTemplatePlaceholderResolved);
        return html;
    }

    public static String resolveMarkerIMPORT(String html, String templateResourceFilePath, String templateResourceFilename, Map<String, String> placeholderReplaceMap){
        String templatePath = templateResourceFilename;
        if( templateResourceFilePath != null )
            templatePath = templateResourceFilePath + /*File.separator*/ "/" + templateResourceFilename; //TODO handle "separator" correctly
        String htmlTemplateWithPlaceholder = ResourceUtils.getResourceAsString(templatePath);
        String htmlTemplatePlaceholderResolved = StringUtils.replace(htmlTemplateWithPlaceholder, placeholderReplaceMap);
        String markerFor = "{{"+HTMLMarkerEnum.IMPORT.marker+":"+templateResourceFilename+"}}";
        html = html.replace(markerFor, htmlTemplatePlaceholderResolved);
        return html;
    }

    public static String resolveImagePlaceholders(String html, Map<String, String> placeholderToUrlImageReplaceMap){
        // 'placeholderToUrlImageReplaceMap' example => Map.Of( "{{LOGO}}", "https://logovtor.com/wp-content/uploads/2020/12/brandart-image-packaging-s-r-l-logo-vector.png" )
        for (Map.Entry<String, String> entry : placeholderToUrlImageReplaceMap.entrySet())
            html = resolveImagePlaceholder(html, entry.getKey(), entry.getValue());
        return html;
    }
    public static String resolveImagePlaceholder(String html, String imagePlaceholder, String imageUrl){
        String imageBase64 = StringUtils.downloadImageAsBase64(imageUrl);
        String htmlImgTag = "<img src=\"data:image/png;base64," + imageBase64 + "\" alt=\""+imagePlaceholder+"\" width=\"100\" />";
        return StringUtils.replace(html, Map.of(imagePlaceholder, htmlImgTag));
    }

}
