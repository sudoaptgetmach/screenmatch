package org.mach.screenmatch.manager;

import org.mach.screenmatch.model.SeasonData;
import org.mach.screenmatch.model.SeriesData;
import org.mach.screenmatch.service.ApiService;
import org.mach.screenmatch.service.DataConverter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppManager {

    private final ApiService apiService = new ApiService();
    private final DataConverter converter = new DataConverter();
    private final Scanner scanner = new Scanner(System.in);
    private final String API_KEY = "&apikey=" + System.getenv("OMDB_API_KEY");

    public void menu() {
        System.out.println("Digite o nome da série desejada:");
        var inputName = scanner.nextLine();

        String ENDERECO = "https://www.omdbapi.com/?t=";
        var data = apiService.obterDados(ENDERECO + URLEncoder.encode(inputName, StandardCharsets.UTF_8) + API_KEY);
        SeriesData seriesData = converter.obterDados(data, SeriesData.class);
        System.out.println(data);

        List<SeasonData> seasonsList = new ArrayList<>();
        for(int i = 1; i<=seriesData.totalSeasons(); i++) {
            data = apiService.obterDados(ENDERECO + URLEncoder.encode(inputName, StandardCharsets.UTF_8) + "&season=" + i + API_KEY);
            SeasonData seasonData = converter.obterDados(data, SeasonData.class);
            seasonsList.add(seasonData);
        }
        seasonsList.forEach(t -> t.Episodes().forEach(e -> System.out.println(e.Title())));
    }
}
