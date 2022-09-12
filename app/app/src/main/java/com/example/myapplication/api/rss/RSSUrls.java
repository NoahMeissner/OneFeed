package com.example.myapplication.api.rss;

import com.example.myapplication.data.addSource.Category;

import java.util.HashMap;

public class RSSUrls {

    private HashMap<Category.interests,String> fazHashMap = new HashMap<>();
    private HashMap<Category.interests,String> spiegelHashMap = new HashMap<>();


    public RSSUrls(){
        initFaz();
        initSpiegel();
    }

    private void initFaz() {
        fazHashMap.put(
                Category.interests.Corona,
                "https://www.faz.net/rss/aktuell/gesellschaft/gesundheit/coronavirus");

        fazHashMap.put(
                Category.interests.Wirtschaft,
                "https://www.faz.net/rss/aktuell/wirtschaft");

        fazHashMap.put(
                Category.interests.Politik,
                "https://www.faz.net/rss/aktuell/politik");

        fazHashMap.put(
                Category.interests.Sport,
                "https://www.faz.net/rss/aktuell/sport");

        fazHashMap.put(
                Category.interests.Technik,
                "https://www.faz.net/rss/aktuell/technik-motor");
    }

    private void initSpiegel(){
        spiegelHashMap.put(
                Category.interests.Corona,
                "https://www.spiegel.de/thema/coronavirus/index.rss");

        spiegelHashMap.put(
                Category.interests.Wirtschaft,
                "https://www.spiegel.de/wirtschaft/index.rss");

        spiegelHashMap.put(
                Category.interests.Technik,
                "https://www.spiegel.de/wissenschaft/technik/index.rss");

        spiegelHashMap.put(
                Category.interests.Gaming,
                "https://www.spiegel.de/netzwelt/games/inde.rss");

        spiegelHashMap.put(
                Category.interests.Politik,
                "https://www.spiegel.de/politik/index.rss");

        spiegelHashMap.put(
                Category.interests.Sport,
                "https://www.spiegel.de/sport/index.rss");
    }

    public HashMap<Category.news,String> getCategory(Category.interests interests){
        HashMap<Category.news,String> result = new HashMap<>();
        result.put(Category.news.FAZ,fazHashMap.get(interests));
        result.put(Category.news.Spiegel,spiegelHashMap.get(interests));
        return result;
    }

    public HashMap<Category.news,HashMap<Category.interests,String>> getAll(){
        HashMap<Category.news,HashMap<Category.interests,String>> result = new HashMap<>();
        result.put(Category.news.FAZ,fazHashMap);
        result.put(Category.news.Spiegel,spiegelHashMap);
        return result;
    }

    public HashMap<Category.interests, String> getFazHashMap() {
        return fazHashMap;
    }

    public HashMap<Category.interests, String> getSpiegelHashMap() {
        return spiegelHashMap;
    }
}
