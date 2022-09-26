package com.example.myapplication.api.rss;

import com.example.myapplication.data.addSource.Constants;

import java.util.HashMap;

public class RssUrls {

    private HashMap<Constants.interests,String> fazHashMap = new HashMap<>();
    private HashMap<Constants.interests,String> spiegelHashMap = new HashMap<>();


    public RssUrls(){
        initFaz();
        initSpiegel();
    }

    private void initFaz() {
        fazHashMap.put(
                Constants.interests.Corona,
                "https://www.faz.net/rss/aktuell/gesellschaft/gesundheit/coronavirus");

        fazHashMap.put(
                Constants.interests.Wirtschaft,
                "https://www.faz.net/rss/aktuell/wirtschaft");

        fazHashMap.put(
                Constants.interests.Politik,
                "https://www.faz.net/rss/aktuell/politik");

        fazHashMap.put(
                Constants.interests.Sport,
                "https://www.faz.net/rss/aktuell/sport");

        fazHashMap.put(
                Constants.interests.Technik,
                "https://www.faz.net/rss/aktuell/technik-motor");
    }

    private void initSpiegel(){
        spiegelHashMap.put(
                Constants.interests.Corona,
                "https://www.spiegel.de/thema/coronavirus/index.rss");

        spiegelHashMap.put(
                Constants.interests.Wirtschaft,
                "https://www.spiegel.de/wirtschaft/index.rss");

        spiegelHashMap.put(
                Constants.interests.Technik,
                "https://www.spiegel.de/wissenschaft/technik/index.rss");

        spiegelHashMap.put(
                Constants.interests.Gaming,
                "https://www.spiegel.de/netzwelt/games/inde.rss");

        spiegelHashMap.put(
                Constants.interests.Politik,
                "https://www.spiegel.de/politik/index.rss");

        spiegelHashMap.put(
                Constants.interests.Sport,
                "https://www.spiegel.de/sport/index.rss");
    }

    public HashMap<Constants.news,String> getCategory(Constants.interests interests){
        HashMap<Constants.news,String> result = new HashMap<>();
        result.put(Constants.news.FAZ,fazHashMap.get(interests));
        result.put(Constants.news.Spiegel,spiegelHashMap.get(interests));
        return result;
    }

    public HashMap<Constants.news,HashMap<Constants.interests,String>> getAll(){
        HashMap<Constants.news,HashMap<Constants.interests,String>> result = new HashMap<>();
        result.put(Constants.news.FAZ,fazHashMap);
        result.put(Constants.news.Spiegel,spiegelHashMap);
        return result;
    }

    public HashMap<Constants.interests, String> getFazHashMap() {
        return fazHashMap;
    }

    public HashMap<Constants.interests, String> getSpiegelHashMap() {
        return spiegelHashMap;
    }
}
