package org.mach.screenmatch;

import org.mach.screenmatch.model.SeriesData;
import org.mach.screenmatch.service.ApiService;
import org.mach.screenmatch.service.JsonConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	public void run(String... args) {
		String apiKey = System.getenv("OMDB_API_KEY");
		var ApiService = new ApiService();
		var json = ApiService.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=" + apiKey);
		System.out.println(json);

		JsonConverter converter = new JsonConverter();
		SeriesData seriesData = converter.obterDados(json, SeriesData.class);
		System.out.println(seriesData);
	}
}
