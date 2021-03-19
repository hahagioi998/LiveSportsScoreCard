package com.scorecard.utils;

import com.scorecard.event.model.Event;
import com.scorecard.event.model.Game;
import com.scorecard.utils.builder.EventBuilder;
import com.scorecard.utils.builder.GameBuilder;
import com.scorecard.utils.enums.Sports;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class MockedUtils {

    private final int TEAM_FOR_MATCH_AMOUNT = 2;
    private List<Long> gameIdList;

    @Autowired
    private FileUtils fileUtils;

    @PostConstruct
    public void init() {
        this.gameIdList = new ArrayList<Long>();
    }

    private List<String> getContentAsList(String fileName) {
        String basePath = System.getProperty("user.dir") + File.separator + "data";
        String fileContent = fileUtils.getFileContent(basePath, fileName);
        return Arrays.stream(fileContent.split("\n"))
                .collect(Collectors.toList());
    }

    public List<String> getLastNameList() {
        return getContentAsList("player-names.txt");
    }

    public List<String> getSoccerTeams() {
        return getContentAsList("soccer-teams.txt");
    }

    public List<String> getHockeyTeams() {
        return getContentAsList("hockey-teams.txt");
    }

    public List<String> getBasketballTeams() {
        return getContentAsList("basketball-teams.txt");
    }

    public String[] getItemsFromBaseList(List<String> baseList, int amount) {
        Collections.shuffle(baseList);
        return baseList.subList(0, amount).stream().toArray(String[]::new);
    }

    public List<String> getVerbEvent() {
        return getContentAsList("verbs.txt");
    }

    public void generateTestDataFile() throws IOException {
        List<Game> gameList = new ArrayList<Game>();
        List<String> soccerTeams = getSoccerTeams();
        List<String> hockeyTeams = getHockeyTeams();
        List<String> basketballTeams = getBasketballTeams();
        Arrays.stream(Sports.values())
                .forEach(sport -> {

                    Game game = null;
                    for (int i = 0; i < sport.getGameAmount(); i++) {
                        game = generateGameData(sport, soccerTeams, hockeyTeams, basketballTeams, gameList);
                        gameList.add(game);
                    }

                    try {
                        fileUtils.saveObjectToFile(gameList, sport.getName() + ".json");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    gameList.clear();
                });
    }

    private String[] getTeams(Sports sport, List<String> soccerTeams, List<String> hockeyTeams, List<String> basketballTeams) {
        final String[] teams;
        if (sport == Sports.SOCCER) {
            teams = getItemsFromBaseList(soccerTeams, TEAM_FOR_MATCH_AMOUNT);
        } else if (sport == Sports.HOCKEY) {
            teams = getItemsFromBaseList(hockeyTeams, TEAM_FOR_MATCH_AMOUNT);
        } else {
            teams = getItemsFromBaseList(basketballTeams, TEAM_FOR_MATCH_AMOUNT);
        }
        return teams;
    }

    private String getInformation(Sports sport, String teams[]) {
        String information = null;
        if (sport == Sports.SOCCER) {
            information = "Soccer Match Between " + Arrays.stream(teams).collect(Collectors.joining(" and "));
        } else if (sport == Sports.HOCKEY) {
            information = "Hockey Match Between " + Arrays.stream(teams).collect(Collectors.joining(" and "));
        } else {
            information = "Basketball Match Between " + Arrays.stream(teams).collect(Collectors.joining(" and "));
        }
        return information;
    }

    private boolean teamsAlreadyIncluded(String teamsToInclude[], List<Game> gameList) {
        return gameList.stream()
                .noneMatch(game -> {
                    return Arrays.stream(game.getTeams()).collect(Collectors.toList()).containsAll(Arrays.stream(teamsToInclude).collect(Collectors.toList()));
                });
    }

    private Long generateSequentialId() {
        Long nextId = Long.valueOf(this.gameIdList.size());
        this.gameIdList.add(nextId);
        return nextId;
    }

    private Game generateGameData(Sports sport, List<String> soccerTeams, List<String> hockeyTeams, List<String> basketballTeams, List<Game> gameList) {
        LocalDateTime startDate = LocalDateTime.now().plus(5, ChronoUnit.MINUTES);
        Long nextGameId = generateSequentialId();
        GameBuilder gameBuilder = GameBuilder.getInstance()
                .id(nextGameId)
                .sport(sport)
                .startDate(convertToDate(startDate));

        String[] teams = getTeams(sport, soccerTeams, hockeyTeams, basketballTeams);
        while (!teamsAlreadyIncluded(teams, gameList)) {
            teams = getTeams(sport, soccerTeams, hockeyTeams, basketballTeams);
        }
        String information = getInformation(sport, teams);

        gameBuilder.teams(teams).information(information);

        List<Event> eventList = IntStream.rangeClosed(0, sport.getEventAmount())
                .mapToObj(index -> {
                    List<String> playerList = getLastNameList();
                    List<String> verbList = getVerbEvent();
                    Collections.shuffle(playerList);
                    Collections.shuffle(verbList);
                    String player = playerList.get(0);
                    String verb = verbList.get(0);
                    return EventBuilder.getInstance()
                            .id(Long.valueOf(index))
                            .gameId(nextGameId)
                            .information(player + " " + verb).build();
                }).collect(Collectors.toList());
        gameBuilder.events(eventList);
        return gameBuilder.build();
    }

    private Date convertToDate(LocalDateTime dateToConvert) {
        return Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}