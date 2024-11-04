package org.mach.screenmatch.manager;

import org.mach.screenmatch.model.Episode;
import org.mach.screenmatch.model.EpisodesData;
import org.mach.screenmatch.model.SeasonData;
import org.mach.screenmatch.model.SeriesData;
import org.mach.screenmatch.service.ApiService;
import org.mach.screenmatch.service.DataConverter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AppManager {

    private final ApiService apiService = new ApiService();
    private final DataConverter converter = new DataConverter();
    private final Scanner scanner = new Scanner(System.in);
    private final String API_KEY = "&apikey=" + System.getenv("OMDB_API_KEY");
    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void menu() {
        System.out.println("Digite o nome da série desejada:");
        var inputName = scanner.nextLine();

        String ENDERECO = "https://www.omdbapi.com/?t=";
        var data = apiService.obterDados(ENDERECO + URLEncoder.encode(inputName, StandardCharsets.UTF_8) + API_KEY);
        SeriesData seriesData = converter.obterDados(data, SeriesData.class);
//        System.out.println(data);

        List<SeasonData> seasonsList = new ArrayList<>();
        for(int i = 1; i<=seriesData.totalSeasons(); i++) {
            data = apiService.obterDados(ENDERECO + URLEncoder.encode(inputName, StandardCharsets.UTF_8) + "&season=" + i + API_KEY);
            SeasonData seasonData = converter.obterDados(data, SeasonData.class);
            seasonsList.add(seasonData);
        }
//        seasonsList.forEach(t -> t.Episodes().forEach(e -> System.out.println(e.Title())));

        List<EpisodesData> episodesData = seasonsList.stream()
                .flatMap(t -> t.episodes().stream())
                .toList();

//        episodesData.stream()
//                .filter(e -> !e.imdbRating().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(EpisodesData::imdbRating).reversed())
//                .limit(10)
//                .map(e -> e.title().toUpperCase())
//                .forEach(System.out::println);

        List<Episode> episodes = seasonsList.stream()
                .flatMap(t -> t.episodes().stream()
                .map(d -> new Episode(t.season(), d)))
                .toList();
        episodes.forEach(System.out::println);

        System.out.println("Qual o titulo do episódio que você procura?");
        var titleSearch = scanner.nextLine();

        Optional<Episode> optionalEpisode = episodes.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(titleSearch.toUpperCase()))
                .findFirst();
        System.out.println(optionalEpisode);

        if (optionalEpisode.isPresent()) {
            System.out.println("Episódio encontrado.");
            System.out.println(
                    "Season: " + optionalEpisode.get().getSeason() +
                    "\nEpisode: " + optionalEpisode.get().getEpisode() +
                    "\nName: " + optionalEpisode.get().getTitle() +
                    "\nReleased: " + optionalEpisode.get().getReleased().format(df)
            );
        } else {
            System.out.println("Episódio não encontrado.");
        }

//        System.out.println("A partir de que data você quer listar os episódios?");
//        int year = scanner.nextInt();
//        scanner.nextLine();
//        scanner.close();
//
//      LocalDate searchdate = LocalDate.of(year, 1, 1);
//
//        episodes.stream()
//                .filter(e -> e.getReleased() != null && e.getReleased().isAfter(searchdate))
//                .forEach(e -> System.out.println(
//                        "Season: " + e.getSeason() +
//                        "\nEpisode: " + e.getEpisode() +
//                        "\nName: " + e.getTitle() +
//                        "\nReleased: " + e.getReleased().format(df)
//                ));
    }
}
