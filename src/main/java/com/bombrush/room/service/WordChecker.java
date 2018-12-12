/*
 * Copyright © BNP PARIBAS - All rights reserved.
 */
package com.bombrush.room.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
public class WordChecker {

    private static final String URL = "https://fr.wiktionary.org/w/api.php?action=query&prop=categories&clcategories=cat%C3%A9gorie:fran%C3%A7ais&format=json&titles=";

    public boolean frenchWordExists(String word) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // the api filters the returned categories on "catégorie:français"
            // thus if the response has a "categories" section it is a French word
            JsonNode jsonNode = mapper.readTree(new URL(URL + word));
            JsonNode categoriesNode = jsonNode.get("query").get("pages").elements().next().get("categories");
            return categoriesNode != null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
