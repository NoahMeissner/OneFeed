package com.example.myapplication.api.rss;
import android.util.Log;

import com.example.myapplication.data.addSource.Category;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

                    String title = getContent(element, "title");
                    String publicationDateString = getContent(element, "pubDate");
                    LocalDateTime publicationDate = parsePublicationDate(publicationDateString);

                    String pictureUrl = "";
                    switch (category) {
                        case Spiegel:
                            pictureUrl = getImageUrl(element, "enclosure");
                            break;
                        case FAZ:
                            pictureUrl = getImageUrl(element, "media:thumbnail");
                            break;
                        default:
                            Log.d("RSSHelper", "Unexpected category: " + category);;
                    }

                    articles.add(new RSSArticle(title, category, pictureUrl, publicationDate));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return articles;
    }

    private String getImageUrl(Element element, String tagName) {
        String url = "";
        Element pictureElement = (Element) element.getElementsByTagName(tagName).item(0);
        // Picture url is optional
        if (pictureElement != null) {
            url = pictureElement.getAttribute("url");
        }

        return url;
    }

    private LocalDateTime parsePublicationDate(String publicationDateString) {
        return LocalDateTime.parse(
                // e.g. Tue, 13 Sep 2022 10:17:05 +0200
                publicationDateString,
                DateTimeFormatter.RFC_1123_DATE_TIME

        );
    }

    private String getContent(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }
}
