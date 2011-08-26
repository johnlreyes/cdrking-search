package per.search;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import lombok.extern.log4j.Log4j;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.json.JSONObject;
import org.w3c.dom.Document;

import per.search.model.Product;

@Log4j
public class Scrapper {

	private final static String BASE_URL = "http://www.cdrking.com/?mod=products&type=view&sid=";

	public static String toString(int sid) {
		String url = BASE_URL + sid;

		String returnValue = null;
		String content = getContent(url);
		if (content != null) {
			Product product = parse(content, sid);
			log.info("product=" + product);
			if (product != null) {
				JSONObject jsonObject = new JSONObject(product);
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

			log.info("value_code=" + value_code);

			String xpath_name = "//td[@class='product_name']/text()";
			String value_name = xpath.evaluate(xpath_name, document);
			log.info("value_name=" + value_name);

			String xpath_price = "//table[@class='products_details']/tbody/tr[3]/td/b/font/text()";
			String value_price = xpath.evaluate(xpath_price, document);
			log.info("value_price=" + value_price);

			String xpath_status = "//span[@class='product_info']/b/text()";
			String value_status = xpath.evaluate(xpath_status, document);
			log.info("value_status=" + value_status);

			StringBuilder category = new StringBuilder();
			int categoryIndex = 1;
			String xpath_category = "//table[@id='table29']/tbody/tr[2]/td[2]/table/tbody/tr/td/font["+categoryIndex+"]/text()";
			String value_category = xpath.evaluate(xpath_category, document);
			log.info("value_category["+categoryIndex+"]=" + value_category);
			while (value_category != null && value_category.trim().length()>0) {
				category.append(value_category.replaceAll(">", "").trim()).append(" > ");
				categoryIndex++;
				xpath_category = "//table[@id='table29']/tbody/tr[2]/td[2]/table/tbody/tr/td/font["+categoryIndex+"]/text()";
				value_category = xpath.evaluate(xpath_category, document);
			}

			returnValue = new Product();
			returnValue.setCode(value_code);
			returnValue.setSid(sid);
			returnValue.setCategory(category.toString());
			returnValue.setName(value_name);
			returnValue.setPrice(value_price);
			returnValue.setStatus(value_status);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;
	}

	private static String getContent(String url) {
		log.info("getting " + url);
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

		log.info("content size " + returnValue != null ? returnValue.length() : 0 + " took " + (timeStop - startTime) + " ms");

		return returnValue;
	}

}
