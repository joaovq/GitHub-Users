package com.githubusers.data.features.movie;

import com.githubusers.data.exception.MovieNotFoundException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

/**
 * Extrator de dados de páginas web
 */
public class MovieWebExtractor {
    private final String TAG = MovieWebExtractor.class.getCanonicalName();
    private Integer trialNumber = 0;


    /**
     * Função para conectar com a LOD e obter dados de um filme específico
     */
    Observable<MovieEntity> getMovie(String title, MovieEntity movieFromOMDb) {
        return Observable.create(emitter -> {
            MovieEntity movieEntity = new MovieEntity();

            trialNumber = 0;
            String URL = createURL(title, movieFromOMDb.getYear());
            while(URL != null) {
                try {
                    Document wikipedia = Jsoup.connect(URL).get();

                    Elements bodies = wikipedia.getElementsByTag("tbody");
                    Element infobox = bodies.get(0);

                    if(!infobox.text().contains("Theatrical release poster"))
                        throw new MovieNotFoundException ();

                    Elements columns = infobox.getElementsByTag("tr");

                    movieEntity.setTitle(Jsoup.parse(columns.get(0).html()).text());
                    for(Element column : columns) {
                        String columnText = column.text();
                        if(columnText.contains("Directed by"))
                            movieEntity.setDirector(getTextByTag(column,"td"));
                        else if(columnText.contains("Produced by"))
                            movieEntity.setProducers(getTextsByTag(column,"li"));
                        else if(columnText.contains("Written by"))
                            movieEntity.setWriters(getTextsByTag(column,"td"));
                        else if(columnText.contains("Starring"))
                            movieEntity.setActors(getTextsByTag(column,"li"));
                        else if(columnText.contains("Music By"))
                            movieEntity.setMusicians(getTextsByTag(column,"td"));
                        else if(columnText.contains("Cinematography"))
                            movieEntity.setCinematographers(getTextsByTag(column,"td"));
                        else if(columnText.contains("Edited By"))
                            movieEntity.setEditors(getTextsByTag(column,"li"));
                        else if(columnText.contains("Production company")) {
                            List<String> production = getTextsByTag(column,"li");
                            if(!production.isEmpty())
                                movieEntity.setProduction(production);
                            else
                                movieEntity.setProduction(getTextByTag(column, "td"));
                        }
                        else if(columnText.contains("Distributed by"))
                            movieEntity.setDistribution(getTextsByTag(column,"td"));
                        else if(columnText.contains("Release date"))
                            movieEntity.setReleased(getTextByTag(column,"td"));
                        else if(columnText.contains("Running time")) {
                            List<String> runningTimes = getTextsByTag(column,"li");
                            if(!runningTimes.isEmpty())
                                movieEntity.setRuntimes(runningTimes);
                            else
                                movieEntity.setRuntimes(Collections.singletonList(getTextByTag(column, "td")));
                        } else if(columnText.contains("Country")) {
                            List<String> countries = getTextsByTag(column,"li");
                            if(!countries.isEmpty())
                                movieEntity.setCountries(countries);
                            else
                                movieEntity.setCountry(getTextByTag(column, "td"));
                        }
                        else if(columnText.contains("Language"))
                            movieEntity.setLanguage(getTextByTag(column,"td"));
                        else if(columnText.contains("Budget"))
                            movieEntity.setBudget(getTextByTag(column,"td"));
                        else if(columnText.contains("Box Office"))
                            movieEntity.setBoxOffice(getTextByTag(column,"td"));
                    }
                    URL = null;
                } catch (MovieNotFoundException e) {
                    e.printStackTrace();
                    URL = createURL(title, movieFromOMDb.getYear());
                } catch (Exception e) {
                    e.printStackTrace();
                    URL = null;
                }
            }

            emitter.onNext(movieEntity);
            emitter.onComplete();
        });
    }

    /**
     * Creates the wikipedia URL
     */
    private String createURL(String title, String year) {
        String URL = "https://en.wikipedia.org/wiki/";
        title = title.replace("+","_");
        switch (trialNumber) {
            case 0:
                URL += title;
                break;
            case 1:
                title += "_(film)";
                URL += title;
                break;
            case 2:
                title += "_(" + year + "_film)";
                URL += title;
                break;
            default:
               URL = null;
        }
        trialNumber++;
        return URL;
    }

    /**
     * Função para obter um texto a partir de uma tag
     */
    private String getTextByTag(Element column, String tag) {
        List<String> texts = getTextsByTag(column,tag);
        String text = texts.get(0);
        text = text.replaceAll("\\[([0-9]+)\\]","");
        return text;
    }

    /**
     * Função para obter um texto a partir de uma tag
     */
    private List<String> getTextsByTag(Element column, String tag) {
        List<String> texts = new ArrayList<>();
        Elements subColumns = column.getElementsByTag(tag);
        for (Element subColumn : subColumns) {
            String text = Jsoup.parse(subColumn.html()).text();
            text = text.replaceAll("\\[([0-9]+)\\]","");
            texts.add(text);
        }
        return texts;
    }
}
