package com.scorecard.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scorecard.event.model.Game;
import com.scorecard.utils.parser.GameParsingUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.io.FileUtils.readFileToString;

@Component
public class FileUtils {
    public List<String> getConfiguredFileNamesToLoad(List<String> configuredSports, boolean news) {
        String basePath = System.getProperty("user.dir");
        File targetFile = new File(basePath + File.separator);
        return Arrays.stream(targetFile.list())
                .filter(fileName -> {
                    return configuredSports.stream().anyMatch(sport -> fileName.startsWith(sport));
                })
                .filter(fileName -> {
                    String fileContent = getFileContent(basePath, fileName);
                    return Objects.nonNull(fileContent) && ((news && isJsonFileValid(fileContent, true)) || isJsonFileValid(fileContent, true));
                })
                .collect(Collectors.toList());
    }

    public String getFileContent(String basePath, String fileName) {
        try {
            File targetFile = new File(basePath + File.separator + fileName);
            return readFileToString(targetFile, "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isJsonFileValid(String fileContent, boolean array) {
        try {
            if (array) {
                new JSONArray(fileContent);
            } else {
                new JSONObject(fileContent);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Game> loadGames(String fileName) throws IOException {
        File targetFile = new File(System.getProperty("user.dir") + File.separator + fileName);
        String fileContent = readFileToString(targetFile, "UTF-8");
        List<Game> gameList = new ArrayList<Game>();
        JSONArray gamesArray = new JSONArray(fileContent);
        gamesArray.toList()
                .stream()
                .map(gameObject -> {
                    HashMap gameJsonObject = (HashMap) gameObject;
                    return GameParsingUtils.parseGames(gameJsonObject);
                }).forEach(gameList::add);
        return gameList;
    }

    public void saveObjectToFile(Object object, String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(System.getProperty("user.dir") + File.separator + fileName), object);
    }
}
