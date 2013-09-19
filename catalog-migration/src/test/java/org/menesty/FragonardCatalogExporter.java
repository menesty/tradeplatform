package org.menesty;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.menesty.tradeplatform.catalog.Attribute;
import org.menesty.tradeplatform.catalog.Catalog;
import org.menesty.tradeplatform.catalog.Product;
import org.menesty.tradeplatform.catalog.ProductItem;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragonardCatalogExporter {

    private static final Pattern digitalPattern = Pattern.compile("\\d+");

    private static final Pattern artNumberPattern = Pattern.compile(" (.*)");

    private static final Pattern pricePattern = Pattern.compile("\\d+,\\d+");

    private static final Pattern productScriptPattern = Pattern.compile("id:(\\d+).*?productType:'(.*?)'.*?getElementById\\(\"(.*?)\"\\)");

    private static final String SITE_URL = "http://www.fragonard.com";

    private static final String[] CATEGORIES_URL = new String[]{
            "http://www.fragonard.com/parfums_grasse/perfumes-c-13.htm",
            "http://www.fragonard.com/parfums_grasse/eaux-de-parfum-c-4.htm",
            "http://www.fragonard.com/parfums_grasse/eaux-de-toilette-c-6.htm",
            "http://www.fragonard.com/parfums_grasse/hommes-c-10.htm",
            "http://www.fragonard.com/parfums_grasse/cosmetiques-c-3.htm",
            "http://www.fragonard.com/parfums_grasse/savons-douche-c-16.htm",
            "http://www.fragonard.com/parfums_grasse/senteurs-maison-c-17.htm",
            "http://www.fragonard.com/parfums_grasse/coffrets-et-trousses-c-1.htm",
            "http://www.fragonard.com/parfums_grasse/cadeaux-c-11.htm"
    };

    public static void main(String... arg) throws IOException, JAXBException {
        Catalog catalog = new Catalog();
        catalog.setName("fragonard");
        catalog.setImageBase(SITE_URL);

        FragonardCatalogExporter exporter = new FragonardCatalogExporter();
        catalog.setProducts(exporter.parseCategoryPage(CATEGORIES_URL[0]));
        //exporter.parseProductPage("http://www.fragonard.com/parfums_grasse/eaux-de-toilette/belle-cherie-c-231.htm");
       /* String text = "var OProduct3184 = new Product('OProduct3184', {id:3184, has_option:false, productType:'Eau de toilette 80% vol'});\n" +
                "if(document.getElementById(\"imggal_2000031840000\")) document.getElementById(\"imggal_2000031840000\").href = \"javascript:OProduct3184.get('3184');void(0);\";";

        Pattern test = Pattern.compile("id:(\\d+).*?productType:'(.*?)'.*\\n+.*?getElementById\\(\"(.*?)\"\\)");
        System.out.println(text);
        Matcher m = test.matcher(text);
        if(m.find()){
            System.out.println(m.group(1));
            System.out.println(m.group(2));
            System.out.println(m.group(3));

        }*/



        JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // for getting nice formatted output
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        //specify the location and name of xml file to be created

        // Writing to XML file
        // Writing to console
        jaxbMarshaller.marshal(catalog, System.out);
    }

    private List<Product> parseCategoryPage(String categoryUrl) throws IOException {
        Document doc = Jsoup.connect(categoryUrl).get();
        Elements productEls = doc.select("#navMenu a");
        List<Product> products = new ArrayList<>();

        for (Element productEl : productEls) {
            Product product = new Product();
            product.setName(productEl.text());
            String productUrl = productEl.attr("href");
            product.setId(getId(productUrl));
            products.add(product);
            product.setItems(parseProductPage(productEl.attr("href")));
        }
        return products;
    }

    private List<ProductItem> parseProductPage(String productUrl) throws IOException {
        Document doc = Jsoup.connect(productUrl).get();

        List<ProductItem> result = new ArrayList<>();

        Elements subProducts = doc.select("#categoryProducts .sub-product");
        for (Element subProduct : subProducts) {

          /*  System.out.println(subProduct.html());
            System.out.println("===============================");

*/
            Elements elements = subProduct.children();
            ProductItem productItem = null;
            for (Element e : elements) {
                if (e.tagName().equals("h2")) continue;
                if (e.tagName().equals("script")) {
                    if (productItem != null) result.add(productItem);
                    productItem = new ProductItem();
                    String scriptContent = e.html().replaceAll("\\r\\n|\\r|\\n", "");
                    Matcher m = productScriptPattern.matcher(scriptContent);
                    m.find();
                    productItem.setId(Long.valueOf(m.group(1)));
                    productItem.getAttributes().add(new Attribute("type", m.group(2)));

                    Element image = doc.getElementById(m.group(3));
                    if (image != null) {
                        productItem.getImages().add(image.child(0).attr("src"));
                    }
                    productItem.setCurrency("eu");
                }
                if(e.tagName().equals("strong")){
                    Matcher m = pricePattern.matcher(e.html());
                    m.find();
                    productItem.setPrice(Double.valueOf(m.group().replace(",",".")));
                }
                if(e.tagName().equals("p")){
                    productItem.setName(e.select(".name").html());
                    Element ref = e.select(".ref").first();
                    Matcher m = artNumberPattern.matcher(ref.text());
                    m.find();
                    productItem.setArtNumber(m.group(1));
                }
            }
        }
        return result;
    }

    private Long getId(String text) {
        Matcher matcher = digitalPattern.matcher(text);
        if (matcher.find()) {
            return Long.valueOf(matcher.group());
        }
        return 0l;
    }
}
