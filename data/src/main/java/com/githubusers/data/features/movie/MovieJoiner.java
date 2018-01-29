package com.githubusers.data.features.movie;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Class to execute the movie data
 */
public class MovieJoiner {
    private Map<String,Set<String>> attributes;

    /**
     * Method to add an attribute
     */
    private void addAttribute(String field, String newAttribute) {
        if(newAttribute == null)
            return;

       Set<String> values = this.attributes.get(field);
       if(values == null)
           values = new TreeSet<>();

        values.add(newAttribute);
        this.attributes.put(field, values);
    }

    /**
     * Method to add an attribute
     */
    private void addAttributes(String field, List<String> newAttributes) {
        if(newAttributes == null)
            return;

        Set<String> values = this.attributes.get(field);
        if(values == null) {
            values = new TreeSet<>(newAttributes);
            this.attributes.put(field,values);
        } else {
            String differenceField = field + "_difference";
            Set<String> differentAttributes = this.attributes.get(differenceField);
            if(differentAttributes == null)
                differentAttributes = new TreeSet<>();

            for(String attribute : newAttributes) {
               if(!values.contains(attribute))
                  differentAttributes.add(attribute);
            }
            this.attributes.put(differenceField,differentAttributes);
        }
    }

    /**
     * Method to execute movie data
     */
    MovieEntity execute(List<MovieEntity> movies) {
        this.attributes = new TreeMap<>();

        MovieEntity finalMovieEntity = new MovieEntity();

        for(MovieEntity movieEntity : movies) {
            addAttribute("title", movieEntity.getTitle());
            addAttribute("released", movieEntity.getReleased());
            addAttribute("website", movieEntity.getWebsite());
            addAttribute("runtime", movieEntity.getRuntime());
            addAttribute("country", movieEntity.getCountry());
            addAttribute("boxoffice", movieEntity.getBoxOffice());
            addAttribute("year", movieEntity.getYear());
            addAttribute("language", movieEntity.getLanguage());
            addAttribute("director", movieEntity.getDirector());
            addAttribute("budget", movieEntity.getBudget());

            addAttributes("actors",movieEntity.getActors());
            addAttributes("writters",movieEntity.getWriters());
            addAttributes("producers",movieEntity.getProducers());
            addAttributes("distribution",movieEntity.getDistribution());
            addAttributes("cinematographers",movieEntity.getCinematographers());
            addAttributes("musiciians",movieEntity.getMusicians());
            addAttributes("editors",movieEntity.getEditors());

        }
        Log.d("MovieJoiner", "execute: " + this.attributes.toString());
        return finalMovieEntity;
    }

    /**
     * Method to compare and execute two strings
     */
    private String compareAndJoinStrings(String string1, String string2) {
        if(string1 == null) {
          if(string2 == null)
              return null;
            else
              return string2;
        } else if(string2 == null)
            return string1;
         else if(string1.equals(string2))
            return string1;
        else {
            return null;
        }
    }
}
