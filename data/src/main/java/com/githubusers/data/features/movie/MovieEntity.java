package com.githubusers.data.features.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Class that represents a movieFromAPI
 */
public class MovieEntity {
  @Expose
  private String Released;

  @Expose
  private String Website;

  @Expose
  private String Type;

  @Expose
  private String imdbVotes;

//  @Expose
//  private List<String> Ratings;

  @Expose
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

//  public List<String> getRatings() {
//    return Ratings;
//  }
//
//  public void setRatings(List<String> ratings) {
//    Ratings = ratings;
//  }

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

  public String getActors() {
    return Actors;
  }

  public void setActors(String actors) {
    Actors = actors;
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

  public String getWriter() {
    return Writer;
  }

  public void setWriter(String writer) {
    Writer = writer;
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
}
