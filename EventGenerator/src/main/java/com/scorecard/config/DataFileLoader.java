package com.scorecard.config;

import com.scorecard.event.handler.EventGenerator;
import com.scorecard.event.model.Game;
import com.scorecard.utils.FileUtils;
import com.scorecard.utils.MockedUtils;
import com.scorecard.utils.ScoreCardConfig;
import com.scorecard.utils.pojo.SportConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * This component will be injected into the application context because it's annotated with @Component (so you will
 * be able to reference from any part of the app). This component will be in charge of generating dummy data in memory,
 * generate the dummy data json files and load them again.
 *
 */
@Component
public class DataFileLoader extends ScoreCardConfig {

    @Autowired
    private EventGenerator eventGenerator;

    @Autowired
    private MockedUtils mockedUtils;

    @Autowired
    private FileUtils fileUtils;

    // Flags
    private boolean loaded = false;
    private boolean testFilesGeneratedCorrectly = true;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws IOException {

        if (!loaded) {
            this.loaded = true;

            /*
             * This will generate 3 different files that corresponds to 3 different sports. In each file,
             * there will be dummy data to be published. The dummy data was taken from mockaroo.
             */
            try {
                mockedUtils.generateTestDataFile();
            } catch (Exception e) {
                testFilesGeneratedCorrectly = false;
            }

            if (testFilesGeneratedCorrectly) {
                List<SportConfig> sportConfig = this.getConfiguredSports();
                List<String> sportsConfigured = sportConfig.stream().map(SportConfig::getName).collect(Collectors.toList());
                List<String> filesToLoad = fileUtils.getConfiguredFileNamesToLoad(sportsConfigured, false);

                if (!filesToLoad.isEmpty()) {
                    Map<String, List<Game>> sportMapGames = new HashMap<String, List<Game>>();
                    filesToLoad.stream()
                            .forEach(fileName -> {
                                String sportKey = sportsConfigured.stream().filter(config -> fileName.startsWith(config)).findFirst().get();
                                try {
                                    List<Game> gameList = fileUtils.loadGames(fileName);
                                    if (sportMapGames.containsKey(sportKey)) {
                                        sportMapGames.get(sportKey).addAll(gameList);
                                    } else {
                                        sportMapGames.put(sportKey, gameList);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });

                    eventGenerator.setSportMapGames(sportMapGames);
                }

//                if (this.isNewsEnabled()) {
//                    List<String> newsFiles = fileUtils.getConfiguredFileNamesToLoad(Arrays.asList("news"), true);
//                    List<News> newsList = fileUtils.loadNews(newsFiles);
//                    eventGenerator.setNews(newsList);
//                }
            }
        }
    }

}
