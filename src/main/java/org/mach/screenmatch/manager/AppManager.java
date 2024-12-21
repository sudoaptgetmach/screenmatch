package org.mach.screenmatch.manager;

import org.apache.commons.text.WordUtils;
import org.mach.screenmatch.model.*;
import org.mach.screenmatch.repository.SeriesRepository;
import org.mach.screenmatch.service.ApiService;
import org.mach.screenmatch.service.DataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class AppManager {

    private final ApiService apiService = new ApiService();
    private final DataConverter converter = new DataConverter();
    private final Scanner scanner = new Scanner(System.in);
    private final String API_KEY = "&apikey=" + System.getenv("OMDB_API_KEY");
    private final String ENDERECO = "https://www.omdbapi.com/?t=";

    @Autowired
    private SeriesRepository repository;

    public AppManager(SeriesRepository repository) {
        this.repository = repository;
    }

    public void menu() {
        while (true) {
            var menu = """
                       Escolha uma opção:
                       1 - Buscar séries
                       2 - Buscar episódios
                       3 - Buscar séries por Ator
                       4 - Buscar séries por Categoria
                       5 - Buscar séries por número de temporadas e avaliação
                       
                       8 - TOP 5 séries
                       9 - Listar séries buscadas
                       0 - Sair
                       """;

            System.out.println(menu);
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
                    searchSeriesByActor();
                    break;
                case 4:
                    searchSeriesByGenre();
                    break;
                case 5:
                    searchBySeasonsAndRating();
                    break;
                case 8:
                    searchTopSeries();
                    break;
                case 9:
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

    private void searchBySeasonsAndRating() {

        System.out.println("Quantas temporadas você quer limitar a pesquisa?");
        var seasons = scanner.nextInt();

        System.out.println("Qual a avaliação mínima que essa séries deve ter?");
        var rating = scanner.nextDouble();

        List<Series> foundBySeasonsAndRating = repository.findSeriesByTotalSeasonsLessThanAndImdbRatingIsGreaterThanEqual(seasons, String.valueOf(rating));
        foundBySeasonsAndRating.forEach(System.out::println);
    }

    private void searchSeriesByGenre() {
        System.out.println("Informe o nome da categoria/gênero:");
        var genreInput = scanner.nextLine();

        Categories categories = Categories.fromPortuguese(genreInput);
        List<Series> seriesByGenre = repository.findByGenre(categories);

        System.out.println("Séries da categoria: " + genreInput);
        seriesByGenre.forEach(System.out::println);
    }

    private void searchTopSeries() {

        List<Series> foundTopSeries = repository.findTop5ByOrderByImdbRatingDesc();

        foundTopSeries.forEach(s -> System.out.println(s.getTitle() + "\n" +
                " Rating: " + s.getImdbRating() + "\n" +
                " Plot: " + s.getPlot()));

    }

    private void searchSeriesByActor() {
        System.out.println("Digite o nome do ator desejado:");
        var actorName = scanner.nextLine();
        actorName = WordUtils.capitalizeFully(actorName);

        List<Series> foundSeries = repository.findByActorsContainingIgnoreCase(actorName);

        System.out.println("Obras em que " + actorName + " participou: ");
        foundSeries.forEach(s -> System.out.println(s.getTitle() + "\n" +
                " Rating: " + s.getImdbRating() + "\n" +
                " Plot: " + s.getPlot()));
    }

    private void searchSeries() {

        System.out.println("Digite o nome da série desejada:");
        var inputName = scanner.nextLine();

        var data = apiService.obterDados(ENDERECO + URLEncoder.encode(inputName, StandardCharsets.UTF_8) + API_KEY);
        SeriesData seriesData = converter.obterDados(data, SeriesData.class);

        if (seriesData == null || seriesData.totalSeasons() == null) {
            System.out.println("Série não encontrada ou número de temporadas não disponível.");
            return;
        }

        Series seriesObj = new Series(seriesData);

        Optional<Series> existingSeries = repository.findByTitleContainingIgnoreCase(seriesObj.getTitle());
        repository.save(existingSeries.orElse(seriesObj));
    }

    private void searchEpisodes() {
        showSearchedSeries();
        System.out.println("Digite o nome da série desejada:");
        var seriesName = scanner.nextLine();

        Optional<Series> serie = repository.findByTitleContainingIgnoreCase(seriesName);

        if (serie.isPresent()) {
            var seriesFound = serie.get();

            List<SeasonData> season = new ArrayList<>();

            for (int i = 1; i <= seriesFound.getTotalSeasons(); i++) {

                var json = apiService.obterDados(ENDERECO + URLEncoder.encode(seriesFound.getTitle(), StandardCharsets.UTF_8) + "&season=" + i + API_KEY);
                SeasonData seasonData = converter.obterDados(json, SeasonData.class);
                season.add(seasonData);

            }
            season.forEach(System.out::println);

            List<Episode> episodes = season.stream()
                    .flatMap(d -> d.episodes().stream()
                            .map(e -> new Episode(d.season(), e)))
                    .toList();

            seriesFound.setEpisodes(episodes);
            repository.save(seriesFound);
        } else {
            System.out.print("Série não encontrada.");
        }
    }

    private void showSearchedSeries() {
        List<Series> series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Series::getGenre))
                .forEach(System.out::println);
    }
}