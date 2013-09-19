package org.menesty.tradeplatform.catalog;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.util.ArrayList;
import java.util.List;

public class JAXBJavaToXml {
    public static void main(String[] args) {

        Catalog catalog = new Catalog();
        catalog.setName("Fragonar");

        List<Product> products = new ArrayList<>();
        catalog.setProducts(products);

        for (int i = 0; i < 3; i++) {
            Product product = new Product();
            product.setName("Diamant " + i);
            List<ProductItem> productItems = new ArrayList<>();
            product.setItems(productItems);
            for (int j = 0; j < 3; j++) {
                ProductItem productItem = new ProductItem();
                productItem.setName("Natural spray 200 ml " + i);
                productItems.add(productItem);
            }
            products.add(product);
        }

        // Creating listOfStates

        try {

            // create JAXB context and initializing Marshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // for getting nice formatted output
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //specify the location and name of xml file to be created

            // Writing to XML file
            // Writing to console
            jaxbMarshaller.marshal(catalog, System.out);

        } catch (JAXBException e) {
            // some exception occured
            e.printStackTrace();
        }

    }
}
