package per.search;


import lombok.extern.log4j.Log4j;
import org.json.JSONObject;
import per.search.model.Information;
import per.search.model.Product;
import per.search.model.Status;
import per.search.model.Support;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

@Log4j
public class CdrKingJson {

    private final static String BASE_URL = "http://www.cdrking.com/?mod=products&type=view&sid=";

    public static String toString(int sid) {
        String url = BASE_URL + sid;

        String returnValue = null;
        String content = getContent(url);
        if (content != null) {
            Product product = parse(content, sid);
            log.info("product="+product);
            if (product != null) {
                JSONObject jsonObject = new JSONObject(product);
                for (String key : product.getInformation().keySet()) {
                    try {
                        Collection<String> list = product.getInformation().get(key);
                        JSONObject ji = (JSONObject) jsonObject.get("information");
                        ji.put(key, list);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                returnValue = jsonObject.toString();
            }
        }

        return returnValue;
    }

    private static Product parse(String htmlSource, int sid) {
        Product returnValue = null;
        try {
            TagNode tagNode = new HtmlCleaner().clean(htmlSource);
            Document document = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
            XPath xpath = XPathFactory.newInstance().newXPath();

            String xpath_code = "//table[@class='products_details']/tbody/tr/td/b/font/i/text()";
            String value_code = xpath.evaluate(xpath_code, document);

            log.info("value_code="+value_code);


            String xpath_name = "//td[@class='product_name']/text()";
            String value_name =  xpath.evaluate(xpath_name, document);
            log.info("value_name="+value_name);


            String xpath_price = "//table[@class='products_details']/tbody/tr[3]/td/b/font/text()";
            String value_price =  xpath.evaluate(xpath_price, document);
            log.info("value_price="+value_price);


            String xpath_status = "//span[@class='product_info']/b/text()";
            String value_status = xpath.evaluate(xpath_status, document);
            log.info("value_status="+value_status);

            returnValue = new Product();
            returnValue.setCode(value_code);
            returnValue.setSid(sid);
            returnValue.setCategory("");
            returnValue.setName(value_name);
            returnValue.setPrice(value_price);
            returnValue.setStatus(value_status);

            while (true) {
                int title_index = 1;
                String xpath_title = "//div[@id='product1']/table/tbody/tr["+title_index+"]/td[@class='product_label']/text()";
                String value_title = xpath.evaluate(xpath_title, document);
                log.info("value_title="+value_title);
                if (value_title == null) {
                    break;
                }

                Information information = new Information();
                Collection<String> iList = new ArrayList<String>();
                information.put(value_title, iList);

                int value_index = 2;
                int li_index = 1;
                while (true) {
                    String xpath_value = "//div[@id='product1']/table/tbody/tr["+value_index+"]/td/p/span/span/li["+li_index+"]/text()";
                    String value_value = xpath.evaluate(xpath_value, document);
                    log.info("value_value="+value_value);
                    if (value_value == null) {
                        break;
                    }
                    iList.add(value_value);
                }
                if (!iList.isEmpty()) {
                    information.put(value_title, iList);
                }
            }

            Support support = new Support();
            String xpath_warranty_period = "//div[@id='product5']/table/tbody/tr[2]/td/text()";
            String value_warranty_period = xpath.evaluate(xpath_warranty_period, document);
            log.info("value_warranty_period="+value_warranty_period);
            support.setWarrantyPeriod(value_warranty_period.replace("Warranty Period : ", ""));
            String xpath_replacement_period = "//div[@id='product5']/table/tbody/tr[3]/td/text()";
            String value_replacement_period = xpath.evaluate(xpath_replacement_period, document);
            log.info("value_replacement_period="+value_replacement_period);
            support.setReplacementPeriod(value_replacement_period.replace("Replacement Period : ", ""));

            returnValue.setInformation(information);
            returnValue.setSupport(support);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return returnValue;
    }

    private static String getContent(String url) {
        log.info("getting "+url);
        long startTime = System.currentTimeMillis();
        String returnValue = null;
        StringBuilder content = new StringBuilder();
        try {
            URL cdrking = new URL(url);
            URLConnection yc = cdrking.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                content.append(inputLine);
            in.close();
        } catch (Exception ex) {
        }
        if (content.length() > 0) {
            returnValue = content.toString();
        }
        long timeStop = System.currentTimeMillis();

        log.info("content size "+returnValue!=null?returnValue.length():0+" took "+(timeStop-startTime)+" ms");

        return returnValue;
    }

}
