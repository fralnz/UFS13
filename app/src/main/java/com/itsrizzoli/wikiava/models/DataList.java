package com.itsrizzoli.wikiava.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class DataList {
    private static DataList instance;
    private ArrayList<String> tags = new ArrayList<>();
    private Map<Integer, Integer> bodyCountMap = new HashMap<>();

    private DataList() {
        tags.add("sporcona");
        tags.add("candida");
        tags.add("cucciolona");
    }

    public static DataList getInstance() {
        if (instance == null) {
            instance = new DataList();
        }
        return instance;
    }

    public static void setInstance(DataList instance) {
        DataList.instance = instance;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public Map<Integer, Integer> getBodyCountMap() {
        return bodyCountMap;
    }

    public void setBodyCountMap(Map<Integer, Integer> bodyCountMap) {
        this.bodyCountMap = bodyCountMap;
    }
}
