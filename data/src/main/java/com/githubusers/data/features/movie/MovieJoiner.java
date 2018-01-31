package com.githubusers.data.features.movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;

/**
 * Class to execute the movie data
 */
public class MovieJoiner {
    private Map<String,Set<String>> attributes;
    private Map<String,String> countriesISO3;

    /**
     * Method to add an attribute
     */
    private void addAttribute(String field, String newAttribute) {
        if(newAttribute == null)
            return;

       Set<String> currentAttributes = this.attributes.get(field);
       if(currentAttributes == null) {
           currentAttributes = new TreeSet<>();
            currentAttributes.add(newAttribute);
       }

        for(String value : currentAttributes) {
            if(StringMatching.editDistance(value,newAttribute) < 3)
                currentAttributes.add(newAttribute);
        }

        this.attributes.put(field, currentAttributes);
    }

    /**
     * Method to add an attribute
     */
    private void addAttributes(String field, List<String> newAttributes) {
        if(newAttributes == null)
            return;

        Set<String> currentAttributes = this.attributes.get(field);
        if(currentAttributes == null) {
            currentAttributes = new TreeSet<>(newAttributes);
            this.attributes.put(field,currentAttributes);
        } else {
            Set<String> attributesToAdd = new TreeSet<>();
            for(String newAttribute : newAttributes) {
                Boolean shouldAddAttribute = true;
                for (String currentAttribute : currentAttributes) {
                    switch (field) {
                        case "runtimes":
                            newAttribute = getRuntimeNumber(newAttribute);
                            currentAttribute = getRuntimeNumber(currentAttribute);
                            break;
                    }
                    if (StringMatching.editDistance(currentAttribute, newAttribute) < 3) {
                        shouldAddAttribute = false;
                        break;
                    } else {
                        String currentAttributePhoneticCode = StringMatching.soundex(currentAttribute);
                        String attributesToAddPhoneticCode = StringMatching.soundex(newAttribute);
                        if (StringMatching.editDistance(currentAttributePhoneticCode, attributesToAddPhoneticCode) <= 1) {
                            shouldAddAttribute = false;
                            break;
                        }

                    }
                }
                if(shouldAddAttribute)
                    attributesToAdd.add(newAttribute);
            }

            currentAttributes.addAll(attributesToAdd);
        }
    }

    /**
     * Methods get only numbers of runtime
     */
    private String getRuntimeNumber(String runtime) {
        return runtime.replaceAll("\\D+","");

    }

    /**
     * Method to create countrie's iso code
     */
    private Map<String,String> createISO3Countries() {
        final Locale englishLocale = new Locale("en", "EN");
        Map<String,String> map = new HashMap<>();
        for (Locale locale : englishLocale.getAvailableLocales()) {
            try {
                map.put(locale.getDisplayCountry(), locale.getISO3Country());
            } catch (Exception e) {e.printStackTrace();}
        }
        map.put("United Kigdom","UK");
        return map;
    }

    /**
     * Method to execute movie data
     */
    MovieEntity execute(List<MovieEntity> movies) {
        attributes = new TreeMap<>();
        countriesISO3 = createISO3Countries();

        MovieEntity finalMovieEntity = new MovieEntity();

        for (MovieEntity movieEntity : movies) {
            addAttribute("title", movieEntity.getTitle());
            addAttribute("released", movieEntity.getReleased());
            addAttribute("website", movieEntity.getWebsite());
            addAttribute("boxoffice", movieEntity.getBoxOffice());
            addAttribute("year", movieEntity.getYear());
            addAttribute("language", movieEntity.getLanguage());
            addAttribute("director", movieEntity.getDirector());
            addAttribute("budget", movieEntity.getBudget());
            addAttribute("genre",movieEntity.getGenre());
            addAttribute("plot",movieEntity.getPlot());
            addAttribute("awards",movieEntity.getAwards());
            addAttribute("poster",movieEntity.getPoster());
            addAttribute("metascore",movieEntity.getMetascore());
            addAttribute("imdbrating",movieEntity.getImdbRating());
            addAttribute("imdbvotes",movieEntity.getImdbVotes());

            addAttributes("production", movieEntity.getProduction());
            addAttributes("countries", movieEntity.getCountries());
            addAttributes("runtimes", movieEntity.getRuntimes());
            addAttributes("writters", movieEntity.getWriters());
            addAttributes("actors", movieEntity.getActors());
            addAttributes("producers", movieEntity.getProducers());
            addAttributes("distribution", movieEntity.getDistribution());
            addAttributes("cinematographers", movieEntity.getCinematographers());
            addAttributes("musicians", movieEntity.getMusicians());
            addAttributes("editors", movieEntity.getEditors());

        }


        for(String key : attributes.keySet()) {
            List<String> selectedAttributes = new ArrayList<>(attributes.get(key));
            Random random = new Random(selectedAttributes.size());
            Integer attributeIndex;
            if(selectedAttributes.size() > 1)
                attributeIndex = random.nextInt(selectedAttributes.size());
            else
                attributeIndex = 0;
            switch (key) {
                case "title":
                    finalMovieEntity.setTitle(selectedAttributes.get(attributeIndex));
                    break;
                case "year":
                    finalMovieEntity.setYear(selectedAttributes.get(attributeIndex));
                    break;
                case "production":
                    finalMovieEntity.setProduction(selectedAttributes);
                    break;
                case "genre":
                    finalMovieEntity.setGenre(selectedAttributes.get(attributeIndex));
                    break;
                case "plot":
                    finalMovieEntity.setPlot(selectedAttributes.get(attributeIndex));
                    break;
                case "awards":
                    finalMovieEntity.setAwards(selectedAttributes.get(attributeIndex));
                    break;
                case "metascore":
                    finalMovieEntity.setMetascore(selectedAttributes.get(attributeIndex));
                    break;
                case "imdbrating":
                    finalMovieEntity.setImdbRating(selectedAttributes.get(attributeIndex));
                    break;
                case "imdbvotes":
                    finalMovieEntity.setImdbVotes(selectedAttributes.get(attributeIndex));
                    break;
                case "poster":
                    finalMovieEntity.setPoster(selectedAttributes.get(attributeIndex));
                    break;
                case "released":
                    finalMovieEntity.setReleased(selectedAttributes.get(attributeIndex));
                    break;
                case "website":
                    finalMovieEntity.setWebsite(selectedAttributes.get(attributeIndex));
                    break;
                case "runtimes":
                    finalMovieEntity.setRuntimes(selectedAttributes);
                    break;
                case "countries":
                    List<String> abbreviations = new ArrayList<>();
                    for(String selectedAttribute : selectedAttributes) {
                        for(String country : countriesISO3.keySet())
                            if(StringMatching.editDistance(selectedAttribute,countriesISO3.get(country)) <= 1)
                                abbreviations.add(selectedAttribute);
                    }
                    selectedAttributes.removeAll(abbreviations);
                    finalMovieEntity.setCountries(selectedAttributes);
                    break;
                case "boxoffice":
                    finalMovieEntity.setBoxOffice(selectedAttributes.get(attributeIndex));
                    break;
                case "laguage":
                    finalMovieEntity.setLanguage(selectedAttributes.get(attributeIndex));
                    break;
                case "director":
                    finalMovieEntity.setDirector(selectedAttributes.get(attributeIndex));
                    break;
                case "budget":
                    finalMovieEntity.setBudget(selectedAttributes.get(attributeIndex));
                    break;
                case "actors":
                    finalMovieEntity.setActors(selectedAttributes);
                    break;
                case "producers":
                    finalMovieEntity.setProducers(selectedAttributes);
                    break;
                case "distribution":
                    finalMovieEntity.setDistribution(selectedAttributes);
                    break;
                case "writters":
                    finalMovieEntity.setWriters(selectedAttributes);
                    break;
                case "cinematographers":
                    finalMovieEntity.setCinematographers(selectedAttributes);
                    break;
                case "musicians":
                    finalMovieEntity.setMusicians(selectedAttributes);
                    break;
                case "editors":
                    finalMovieEntity.setEditors(selectedAttributes);
                    break;
            }
        }
        return finalMovieEntity;
    }
}
