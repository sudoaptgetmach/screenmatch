package org.mach.screenmatch.manager;

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

    private List<Series> series = new ArrayList<>();

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


        var data = apiService.obterDados(ENDERECO + URLEncoder.encode(inputName, StandardCharsets.UTF_8) + API_KEY);
        SeriesData seriesData = converter.obterDados(data, SeriesData.class);

        if (seriesData == null || seriesData.totalSeasons() == null) {
            System.out.println("Série não encontrada ou número de temporadas não disponível.");
            return;
        }

        Series seriesObj = new Series(seriesData);

        Series existingSeries = repository.findByTitle(seriesObj.getTitle());
        repository.save(existingSeries != null ? existingSeries : seriesObj);
    }

    private void searchEpisodes() {
        showSearchedSeries();
        System.out.println("Digite o nome da série desejada:");
        var seriesName = scanner.nextLine();

        Optional<Series> serie = series.stream()
                .filter(s -> s.getTitle().toLowerCase().contains(seriesName.toLowerCase()))
                .findFirst();

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
        series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Series::getGenre))
                .forEach(System.out::println);
    }
}