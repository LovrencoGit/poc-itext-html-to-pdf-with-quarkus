package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.models.ItemPDFModel;
import org.acme.utils.PDFUtils;
import org.acme.utils.ResourceUtils;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@ApplicationScoped
public class AttachmentService {

    private final String ATTACHMENT_DettaglioRichiamoMerci_PATH = "/templates/ita/DettaglioRichiamoMerci.html";
    private final String ATTACHMENT_DettaglioRichiamoMerci_table_tr_FILENAME = "DettaglioRichiamoMerci_table_tr.html";
    private final String ATTACHMENT_DettaglioRichiamoMerci_footer_FILENAME = "DettaglioRichiamoMerci_footer.html";
    //private final String ATTACHMENT_IMG_loro_piana_logo_PATH = "/img/loro_piana_logo.png";
    private final String ATTACHMENT_IMG_brandart_logo_URL = "https://logovtor.com/wp-content/uploads/2020/12/brandart-image-packaging-s-r-l-logo-vector.png";

    public ByteArrayOutputStream generateDettaglioRichiamoMerciITEXT() {
        String htmlContent = ResourceUtils.getResourceAsString(ATTACHMENT_DettaglioRichiamoMerci_PATH);

        /*****  HEADER  *****/

        htmlContent = PDFUtils.resolveImagePlaceholders(htmlContent, Map.of("{{LOGO}}", ATTACHMENT_IMG_brandart_logo_URL));

        htmlContent = PDFUtils.resolvePlaceholder(htmlContent, HEADER_PLACEHOLDER_TO_VALUES_REPLACEMAP);

        /*****  TABLE  *****/

        htmlContent = PDFUtils.resolveMarkerFOR(htmlContent, "/templates/ita", ATTACHMENT_DettaglioRichiamoMerci_table_tr_FILENAME, ITEMS, PLACEHOLDER_BY_CLASS_Item_REPLACEMAP);

        /*****  FOOTER  *****/

        htmlContent = PDFUtils.resolveMarkerIMPORT(htmlContent, "/templates/ita", ATTACHMENT_DettaglioRichiamoMerci_footer_FILENAME, FOOTER_PLACEHOLDER_TO_VALUES_REPLACEMAP);

        return PDFUtils.generatePDF(htmlContent);
    }



    /***********************************************************************************/
    /***********************************************************************************/
    /***********************************************************************************/



    private final  Map<String, String> HEADER_PLACEHOLDER_TO_VALUES_REPLACEMAP =
            Map.of(
            //"{{LOGO}}",                 "<img src=\"" + getClass().getResource(ATTACHMENT_IMG_loro_piana_logo_PATH).toExternalForm() + "\" alt=\"Logo\" width=\"100\" />",
            "{{NUMERO_CALLOFF}}",           "COFLPIANA-20240517-111054",
            "{{DATA_RICHIAMO_MERCI}}",      "17/05/2024",
            "{{PRODUTTORI}}",               "710645 Italia Manifattura Loro Piana Porto San Giorgio",
            "{{INDIRIZZO_SPEDIZIONE}}",     "TRANS WORLD SHIPPING SPA, via Dell’Industria, snc – Zona Ind.le B 62012 CIVITANOVA MARCHE (MC)"
    );

    private final List<ItemPDFModel> ITEMS = Arrays.asList(
            new ItemPDFModel("Prodotto 1", "10.50"),
            new ItemPDFModel("Prodotto 2", "15.75"),
            new ItemPDFModel("Prodotto 3", "7.20")
    );


    public static final Map<String, Function<ItemPDFModel, String>> PLACEHOLDER_BY_CLASS_Item_REPLACEMAP = Map.of(
        "{{ITEM_NAME}}", ItemPDFModel::getName,
        "{{ITEM_PRICE}}", ItemPDFModel::getPrice
    );
//    public static final Map<Class<?>, Map<String, Function<?, String>>> PLACEHOLDER_BY_CLASS_REPLACEMAP = Map.of(
//            ItemPDFModel.class,(Map<String, Function<?, ?>>) PLACEHOLDER_BY_CLASS_Item_REPLACEMAP
//    );

    private final  Map<String, String> FOOTER_PLACEHOLDER_TO_VALUES_REPLACEMAP =
            Map.of(
            "{{CLIENTE}}",     "Loris Cernich"
            );
}
