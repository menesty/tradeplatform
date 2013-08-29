package org.menesty.tradeplatform.web;

import com.google.common.base.Charsets;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Menesty
 * Date: 8/21/13
 * Time: 10:18 AM
 */
public class CssImageParse {

    public static void main(String... arg) throws IOException, URISyntaxException {
        Path p = Paths.get("D:\\development\\workspace\\TradePlatform\\wicket-web\\src\\main\\webapp\\styles\\icons.css");
        List<String> lines = Files.readAllLines(p, Charsets.UTF_8);
        Pattern pattern = Pattern.compile("url\\('(.*?)'\\)");

         for (String line: lines){
             Matcher m = pattern.matcher(line);
             if(m.find()){
                 String path =  m.group(1).substring(2,  m.group(1).length());

                 Path target = Paths.get("D:\\development\\workspace\\TradePlatform\\wicket-web\\src\\main\\webapp"+ path);
                 if(target.toFile().exists()) continue;
                 System.out.println(target.toFile().getPath());
                // target.toFile().createNewFile();
                // target.toFile().mkdirs();
                 InputStream io = new URL("http://aqvatarius.com/themes/aquarius_v17"+path).openStream();
                 Files.copy(io, target);
                 //System.out.print(m.group(1));
             }
         }
    }
}
