package com.githubusers.data.features.movie;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import io.reactivex.Observable;

/**
 * Extrator de dados de páginas web
 */
public class WebExtractor {
    /**
     * Função para conectar com a LOD e obter dados de um filme específico
     */
    Observable<MovieEntity> getMovie(String title) {
        return Observable.create(emitter -> {
            MovieEntity movieEntity = new MovieEntity();
            movieEntity.setTitle("Teste");

            try {
                Document wikipedia = Jsoup.connect("https://pt.wikipedia.org/wiki/The_Shining").get();
                Elements bodies = wikipedia.getElementsByTag("tbody");
                Element infobox = bodies.get(0);
                Elements colunas = infobox.getElementsByTag("tr");
                Log.d(WebExtractor.class.getCanonicalName(), "getMovie: " + infobox);

            } catch (Exception e) {
                e.printStackTrace();
            }

            emitter.onNext(movieEntity);
            emitter.onComplete();
        });
    }
}
