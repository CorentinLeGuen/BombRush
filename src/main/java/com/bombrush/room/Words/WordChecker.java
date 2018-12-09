/*
 * Copyright © BNP PARIBAS - All rights reserved.
 */
package com.bombrush.room.Words;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class WordChecker {

    private static final String URL = "https://fr.wiktionary.org/w/api.php?action=parse&format=json&prop=text|revid|displaytitle&page=";

    public boolean frenchWordExists(String word) {
        try {
            URL url = new URL(URL + word);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = in.readLine();
            in.close();
            connection.disconnect();
            System.out.println("RESULT: " + result);
            return !result.startsWith("{\"error\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
