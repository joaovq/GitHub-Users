package com.githubusers.presentation.features.movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;

/**
 * Class that represents a user in the presentation layer.
 */
class MovieModel {
    private String Released;

    private String Website;

    private String Type;

    private String imdbVotes;

    private String Runtime;

    private String Response;

    private String Poster;

    private String imdbID;

    private String Country;

    private String BoxOffice;

    private String Title;

    private String DVD;

    private String imdbRating;

    private String Year;

    private String Rated;

    private String Actors;

    private String Plot;

    private String Metascore;

    private String Writer;

    private String Production;

    private String Genre;

    private String Language;

    private String Awards;

    private String Director;

    private String Budget;

    private List<String> Cinematographers;

    private List<String> producers;

    private List<String> Editors;

    private List<String> Distribution;

    private List<String> Musicians;

    public String getBudget() {
        return Budget;
    }

    public void setBudget(String budget) {
        Budget = budget;
    }

    public List<String> getDistribution() {
        return Distribution;
    }

    public void setDistribution(List<String> distributions) {
        Distribution = distributions;
    }

    public List<String> getCinematographers() {
        return Cinematographers;
    }

    public void setCinematographers(List<String> cinematographers) {
        Cinematographers = cinematographers;
    }

    public List<String> getEditors() {
        return Editors;
    }

    public void setEditors(List<String> editors) {
        Editors = editors;
    }

    public List<String> getMusicians() {
        return Musicians;
    }

    public void setMusicians(List<String> musicians) {
        Musicians = musicians;
    }

    public List<String> getProducers() {
        return producers;
    }

    public void setProducers(List<String> producers) {
        this.producers = producers;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String released) {
        Released = released;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getBoxOffice() {
        return BoxOffice;
    }

    public void setBoxOffice(String boxOffice) {
        BoxOffice = boxOffice;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDVD() {
        return DVD;
    }

    public void setDVD(String DVD) {
        this.DVD = DVD;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getRated() {
        return Rated;
    }

    public void setRated(String rated) {
        Rated = rated;
    }

    public List<String> getActors() {
        return transformStringToList(Actors);
    }

    public void setActors(List<String> actors) {
        Actors = transformListToString(actors);
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public String getMetascore() {
        return Metascore;
    }

    public void setMetascore(String metascore) {
        Metascore = metascore;
    }

    public List<String> getWriters() {
        return transformStringToList(Writer);
    }

    public void setWriters(List<String> writers) {
        Writer = transformListToString(writers);

    }

    public String getProduction() {
        return Production;
    }

    public void setProduction(String production) {
        Production = production;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getAwards() {
        return Awards;
    }

    public void setAwards(String awards) {
        Awards = awards;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    private String transformListToString(List<String> strings) {
        String finalString = "";
        for(String string : strings) {
            finalString += (string + ",");
        }
        return finalString;
    }

    private List<String> transformStringToList(String string) {
        if(string == null)
            return new ArrayList<>();
        String [] actors = string.split(",");
        return Arrays.asList(actors);
    }
}
