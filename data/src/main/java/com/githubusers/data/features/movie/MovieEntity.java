package com.githubusers.data.features.movie;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that represents a movie
 */
class MovieEntity {
  @Expose
  private String Released;

  @Expose
  private String Website;

  @Expose
  private String Type;

  @Expose
  private String imdbVotes;

  private String Runtime;

  @Expose
  private String Response;

  @Expose
  private String Poster;

  @Expose
  private String imdbID;

  @Expose
  private String Country;

  @Expose
  private String BoxOffice;

  @Expose
  private String Title;

  @Expose
  private String DVD;

  @Expose
  private String imdbRating;

  @Expose
  private String Year;

  @Expose
  private String Rated;

  @Expose
  private String Actors;

  @Expose
  private String Plot;

  @Expose
  private String Metascore;

  @Expose
  private String Writer;

  @Expose
  private String Production;

  @Expose
  private String Genre;

  @Expose
  private String Language;

  @Expose
  private String Awards;

  @Expose
  private String Director;

  private String Budget;

  private List<String> Cinematographers;

  private List<String> producers;

  private List<String> Editors;

  private List<String> Distribution;

  private List<String> Musicians;

  String getBudget() {
    return Budget;
  }

  void setBudget(String budget) {
    Budget = budget;
  }

  List<String> getDistribution() {
    return Distribution;
  }

  void setDistribution(List<String> distributions) {
    Distribution = distributions;
  }

  List<String> getCinematographers() {
    return Cinematographers;
  }

  void setCinematographers(List<String> cinematographers) {
    Cinematographers = cinematographers;
  }

  List<String> getEditors() {
    return Editors;
  }

  void setEditors(List<String> editors) {
    Editors = editors;
  }

  List<String> getMusicians() {
    return Musicians;
  }

  void setMusicians(List<String> musicians) {
    Musicians = musicians;
  }

  List<String> getProducers() {
    return producers;
  }

  void setProducers(List<String> producers) {
    this.producers = producers;
  }

  String getReleased() {
    return Released;
  }

  void setReleased(String released) {
    Released = released;
  }

  String getWebsite() {
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

  List<String> getRuntimes() {
    return transformStringToList(Runtime);
  }

  void setRuntimes(List<String> runtime) {
    Runtime = transformListToString(runtime);
  }

  String getResponse() {
    return Response;
  }

  void setResponse(String response) {
    Response = response;
  }

  String getPoster() {
    return Poster;
  }

  void setPoster(String poster) {
    Poster = poster;
  }

  String getImdbID() {
    return imdbID;
  }

  void setImdbID(String imdbID) {
    this.imdbID = imdbID;
  }

  List<String> getCountries() {
    return transformStringToList(Country);
  }

  void setCountry(String country) {
    Country = country;
  }

  void setCountries(List<String> countries) {
    Country = transformListToString(countries);
  }

  String getBoxOffice() {
    return BoxOffice;
  }

  void setBoxOffice(String boxOffice) {
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

  List<String> getWriters() {
    return transformStringToList(Writer);
  }

  void setWriters(List<String> writers) {
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
