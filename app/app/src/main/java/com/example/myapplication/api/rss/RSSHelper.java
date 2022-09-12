package com.example.myapplication.api.rss;
import com.example.myapplication.data.addSource.Category;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class RSSHelper {
    public ArrayList<RSSArticle> createArticlesFromResponse(String rssResponse, Category.news category) {
        ArrayList<RSSArticle> articles = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new ByteArrayInputStream(rssResponse.getBytes("utf-8"))));

            NodeList list = doc.getElementsByTagName("item");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String title = element.getElementsByTagName("title").item(0).getTextContent();

                    // Picture url is optional
                    Element pictureElement = (Element) element.getElementsByTagName("enclosure").item(0);
                    String pictureUrl = "";
                    if (pictureElement != null) {
                        pictureUrl = pictureElement.getAttribute("url");
                    }

                    articles.add(new RSSArticle(title, category, pictureUrl));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return articles;
    }
}
