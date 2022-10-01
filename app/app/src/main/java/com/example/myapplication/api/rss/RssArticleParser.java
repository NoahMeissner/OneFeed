package com.example.myapplication.api.rss;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.data.addSource.Constants;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class RssArticleParser {
    public ArrayList<RssArticle> parseArticles(
            String rssResponse,
            Constants.news category
    ) {
        // Todo: running async or not? nested functions!
        ArrayList<RssArticle> articles = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(
                    new ByteArrayInputStream(rssResponse.getBytes("utf-8"))
            ));

            NodeList list = doc.getElementsByTagName("item");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String title = getContent(element, "title");
                    String publicationDateString = getContent(element, "pubDate");
                    LocalDateTime publicationDate = parsePublicationDate(publicationDateString);
                    String webUrl = getContent(element, "link");
                    String sourceIconUrl = getSourceIconUrl(doc);

                    // Todo: Evaluate if using try-until-match is better fit than individual
                    //   implementations for specific sources.
                    String sourceName = "";
                    Element sourceInfoElement = ((Element) doc.getElementsByTagName("image").item(0));
                    switch (category) {
                        case Spiegel:
                            sourceName = getContent(sourceInfoElement, "title");
                            break;
                        case FAZ:
                            sourceName = getContent(doc.getDocumentElement(), "generator");
                            break;
                    }

                    String pictureUrl = "";
                    switch (category) {
                        case Spiegel:
                            pictureUrl = getImageUrl(element, "enclosure");
                            break;
                        case FAZ:
                            pictureUrl = getImageUrl(element, "media:thumbnail");
                            break;
                        default:
                            Log.d("RSSHelper", "Unexpected category: " + category);
                    }

                    articles.add(new RssArticle(
                            title,
                            category,
                            pictureUrl,
                            publicationDate,
                            sourceName,
                            sourceIconUrl,
                            webUrl
                    ));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return articles;
    }

    @NonNull
    private String getSourceIconUrl(Document doc) {
        String rssUrl = getContent(doc.getDocumentElement(), "link");

        // Pattern find the base url of an given url:
        //   e.g. "https://www.faz.net/rss/aktuell" -> ["https://www.faz.net/", "rss/aktuell"]
        Pattern baseUrlPattern = Pattern.compile("http\\w*:\\/\\/[^\\/]+\\/");
        Matcher baseUrlMatcher = baseUrlPattern.matcher(rssUrl);

        String iconUrl = "";
        if (baseUrlMatcher.find()) {
            iconUrl = baseUrlMatcher.group(0) + "favicon.ico";
        }

        return iconUrl;
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
