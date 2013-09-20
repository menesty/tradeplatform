package org.menesty;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.menesty.tradeplatform.catalog.*;
import org.menesty.tradeplatform.catalog.util.CatalogUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragonardCatalogExporter {

    private static final Pattern digitalPattern = Pattern.compile("\\d+");

    private static final Pattern artNumberPattern = Pattern.compile(" (.*)");

    private static final Pattern pricePattern = Pattern.compile("\\d+,\\d+");

    private static final Pattern productScriptPattern = Pattern.compile("id:(\\d+).*?productType:'(.*?)'.*?getElementById\\(\"(.*?)\"\\)");

    private static final String SITE_URL = "http://www.fragonard.com";

    public static void main(String... arg) throws Exception {

        FragonardCatalogExporter exporter = new FragonardCatalogExporter();
        Catalog catalog = exporter.parseCatalog("http://www.fragonard.com/parfums_grasse/");

        OutputStream outputStream = new FileOutputStream("/Users/Menesty/development/workspace/tradeplatform/catalog-migration/src/main/resources/fragonard.xml");
        CatalogUtil.exportCatalog(catalog, outputStream);

    }

    private Catalog parseCatalog(String catalogUrl) throws IOException {
        Catalog catalog = new Catalog();
        catalog.setName("Fragonard");
        catalog.setImageBase(SITE_URL);
        Document doc = Jsoup.connect(catalogUrl).get();

        Elements categoriesEl = doc.select("ol#navMain a");
        for (Element categoryEl : categoriesEl) {
            Category category = new Category();
            category.setName(categoryEl.text());
            catalog.getCategories().add(category);
            String categoryUrl = categoryEl.attr("href");
            category.setId(getId(categoryUrl));
            catalog.getProducts().addAll(parseCategoryPage(categoryUrl, category.getId()));
        }
        return catalog;

    }

    private List<Product> parseCategoryPage(String categoryUrl, Long categoryId) throws IOException {
        Document doc = Jsoup.connect(categoryUrl).get();
        Elements productEls = doc.select("#navMenu a");
        List<Product> products = new ArrayList<>();

        for (Element productEl : productEls) {
            Product product = new Product();
            product.setCategoryId(categoryId);
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
                    if (image != null)
                        productItem.getImages().add(image.child(0).attr("src"));

                    productItem.setCurrency("eu");
                }
                if (e.tagName().equals("strong")) {
                    Matcher m = pricePattern.matcher(e.html());
                    m.find();

                    productItem.setPrice(Double.valueOf(m.group().replace(",", ".")));
                }
                if (e.tagName().equals("p")) {
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
