package org.menesty.tradeplatform.catalog.util;

import org.menesty.tradeplatform.catalog.Catalog;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.OutputStream;

public class CatalogUtil {

    public static void exportCatalog(Catalog catalog, OutputStream os) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Catalog.class);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(catalog, os);
    }

    public static Catalog importCatalog(InputStream is) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Catalog.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (Catalog) unmarshaller.unmarshal(is);
    }
}
