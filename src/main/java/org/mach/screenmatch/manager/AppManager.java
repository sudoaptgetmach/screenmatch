package org.mach.screenmatch.manager;

import org.mach.screenmatch.model.*;
import org.mach.screenmatch.repository.SeriesRepository;
import org.mach.screenmatch.service.ApiService;
import org.mach.screenmatch.service.DataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class AppManager {

    private final ApiService apiService = new ApiService();
    private final DataConverter converter = new DataConverter();
    private final Scanner scanner = new Scanner(System.in);
    private final String API_KEY = "&apikey=" + System.getenv("OMDB_API_KEY");
    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final List<Series> searchedSeries = new ArrayList<>();

    @Autowired
    private SeriesRepository repository;

    public AppManager(SeriesRepository repository) {
        this.repository = repository;
    }

    public void menu() {
        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Buscar séries");
            System.out.println("2 - Buscar episódios");
            System.out.println("3 - Listar séries buscadas");
            System.out.println("0 - Sair");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    searchSeries();
                    break;
                case 2:
                    searchEpisodes();
                    break;
                case 3:
                    showSearchedSeries();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void searchSeries() {
        System.out.println("Digite o nome da série desejada:");
        var inputName = scanner.nextLine();

        String ENDERECO = "https://www.omdbapi.com/?t=";
        var data = apiService.obterDados(ENDERECO + URLEncoder.encode(inputName, StandardCharsets.UTF_8) + API_KEY);
        SeriesData seriesData = converter.obterDados(data, SeriesData.class);

        if (seriesData == null || seriesData.totalSeasons() == null) {
            System.out.println("Série não encontrada ou número de temporadas não disponível.");
            return;
        }

        Series seriesObj = new Series(seriesData);

        Series existingSeries = repository.findByTitle(seriesObj.getTitle());
        Series newSeries = seriesObj;
        repository.save(existingSeries != null ? existingSeries : newSeries);

        List<SeasonData> seasonsList = new ArrayList<>();
        for (int i = 1; i <= seriesData.totalSeasons(); i++) {
            data = apiService.obterDados(ENDERECO + URLEncoder.encode(inputName, StandardCharsets.UTF_8) + "&season=" + i + API_KEY);
            SeasonData seasonData = converter.obterDados(data, SeasonData.class);
        }

        List<Episode> episodes = seasonsList.stream()
                .flatMap(t -> t.episodes().stream()
                        .map(d -> new Episode(t.season(), d)))
                .toList();
        episodes.forEach(System.out::println);
    }

    private List<Episode> fetchAllEpisodes(String seriesName) {
        List<Episode> allEpisodes = new ArrayList<>();
        String ENDERECO = "https://www.omdbapi.com/?t=";

        var data = apiService.obterDados(ENDERECO + URLEncoder.encode(seriesName, StandardCharsets.UTF_8) + API_KEY);
        SeriesData seriesData = converter.obterDados(data, SeriesData.class);

        for (int i = 1; i <= seriesData.totalSeasons(); i++) {
            data = apiService.obterDados(ENDERECO + URLEncoder.encode(seriesName, StandardCharsets.UTF_8) + "&season=" + i + API_KEY);
            SeasonData seasonData = converter.obterDados(data, SeasonData.class);
            List<Episode> episodes = seasonData.episodes().stream()
                    .map(e -> new Episode(seasonData.season(), e))
                    .toList();
            allEpisodes.addAll(episodes);
        }

        return allEpisodes;
    }

    private void searchEpisodes() {
        System.out.println("Digite o nome da série desejada:");
        var seriesName = scanner.nextLine();

        System.out.println("Qual o título do episódio que você procura?");
        var titleSearch = scanner.nextLine();

        List<Episode> episodes = fetchAllEpisodes(seriesName);

        Optional<Episode> optionalEpisode = episodes.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(titleSearch.toUpperCase()))
                .findFirst();

        if (optionalEpisode.isPresent()) {
            Episode episode = optionalEpisode.get();
            System.out.println("Episódio encontrado.");
            System.out.println(
                    "Season: " + episode.getSeason() +
                            "\nEpisode: " + episode.getEpisode() +
                            "\nName: " + episode.getTitle() +
                            "\nReleased: " + (episode.getReleased() != null ? episode.getReleased().format(df) : "N/A")
            );
        } else {
            System.out.println("Episódio não encontrado.");
            System.out.println("A partir de que data você quer listar os episódios?");
            int year = scanner.nextInt();
            scanner.nextLine();

            LocalDate searchdate = LocalDate.of(year, 1, 1);

            episodes.stream()
                    .filter(e -> e.getReleased() != null && e.getReleased().isAfter(searchdate))
                    .forEach(e -> System.out.println(
                            "Season: " + e.getSeason() +
                                    "\nEpisode: " + e.getEpisode() +
                                    "\nName: " + e.getTitle() +
                                    "\nReleased: " + e.getReleased().format(df)
                    ));

            Map<Integer, Double> ratingsBySeason = episodes.stream()
                    .collect(Collectors.groupingBy(Episode::getSeason, Collectors.averagingDouble(Episode::getImdbRating)));
            System.out.println(ratingsBySeason);

            DoubleSummaryStatistics summaryStatistics = episodes.stream()
                    .filter(e -> e.getImdbRating() > 0.0)
                    .collect(Collectors.summarizingDouble(Episode::getImdbRating));
            System.out.println("Média: " + summaryStatistics.getAverage());
        }
    }

    private SeriesData getSeriesData() {
        System.out.println("Digite o nome da série desejada:");
        var inputName = scanner.nextLine();

        String ENDERECO = "https://www.omdbapi.com/?t=";
        var data = apiService.obterDados(ENDERECO + URLEncoder.encode(inputName, StandardCharsets.UTF_8) + API_KEY);
        return converter.obterDados(data, SeriesData.class);
    }

    private void showSearchedSeries() {
        List<Series> searchedSeries = new ArrayList<>();
        searchedSeries = repository.findAll();

    }
}