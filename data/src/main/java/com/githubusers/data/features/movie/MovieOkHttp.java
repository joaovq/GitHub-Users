package com.githubusers.data.features.movie;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 */
public class MovieOkHttp {
    /**
     * Função para conectar com a LOD e obter dados de um filme específico
     */
    Observable<MovieEntity> getMovie(String title) {
        return Observable.create(emitter -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(getParametros(title))
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onError(e);
            }
            assert response != null;
            MovieEntity movieEntity = fromJsonToMovieEntity(response.body().string());
            emitter.onNext(movieEntity);
            emitter.onComplete();
        });
    }

    /**
     * Função para criar parâmetros a partir do título do filme
     */
    private String getParametros(String title) {
        title = title.replace(" ", "+");

        String url = "http://data.linkedmdb.org/sparql?query=PREFIX+owl%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2002%2F07%2Fowl%23%3E%0D%0APREFIX+xsd%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0D%0APREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E%0D%0APREFIX+foaf%3A+%3Chttp%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2F%3E%0D%0APREFIX+rdf%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0D%0APREFIX+oddlinker%3A+%3Chttp%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Foddlinker%2F%3E%0D%0APREFIX+map%3A+%3Cfile%3A%2FC%3A%2Fd2r-server-0.4%2Fmapping.n3%23%3E%0D%0APREFIX+db%3A+%3Chttp%3A%2F%2Fdata.linkedmdb.org%2Fresource%2F%3E%0D%0APREFIX+dbpedia%3A+%3Chttp%3A%2F%2Fdbpedia.org%2Fproperty%2F%3E%0D%0APREFIX+skos%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2004%2F02%2Fskos%2Fcore%23%3E%0D%0APREFIX+dc%3A+%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0D%0APREFIX+movie%3A+%3Chttp%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Fmovie%2F%3E%0D%0ASELECT+*+WHERE+%7B%0D%0A+%3Fresource+dc%3Atitle+%22_(Title)_%22.+%0D%0A+%3Fresource+dc%3Adate+%3Fdate.%0D%0A+%3Fresource+dc%3Atitle+%3Ftitle.%0D%0A+%3Fresource+movie%3Ainitial_release_date+%3Frelease_date.%0D%0A+%3Fresource+movie%3Aactor+%3Factor.%0D%0A+%3Factor+movie%3Aactor_name+%3Factor_name.%0D%0A+%3Fresource+movie%3Adirector+%3Fdirector.%0D%0A+%3Fdirector+movie%3Adirector_name+%3Fdirector_name.%0D%0A+OPTIONAL+%7B+%3Fresource+movie%3Aruntime+%3Fruntime%7D.%0D%0A+OPTIONAL+%7B%3Fresource+movie%3Agenre+%3Fgenre+%7D.%0D%0A+OPTIONAL+%7B%3Fgenre+movie%3Afilm_genre_name+%3Fgenre_name%7D.%0D%0A%7D%0D%0A&output=json";
        url = url.replace("_(Title)_",title);

        return url;
    }

    /**
     * Função para criar um @link {@link MovieEntity } a partir do
     * JSON retornado da LOD
     */
    private MovieEntity fromJsonToMovieEntity(String resultToLOD) throws JSONException {
        MovieEntity movieEntity = new MovieEntity();

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(resultToLOD);
        } catch (JSONException e) {
            e.printStackTrace();
            throw e;
        }

        Iterator<String> iter = jsonObject.keys();
        JSONArray movies = null;
        while (iter.hasNext()) {
            String key = iter.next();
            if(key.equals("results")) {
                try {
                    Object value = jsonObject.get(key);
                    movies = (JSONArray) new JSONObject(value.toString()).get("bindings");
                } catch (JSONException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }

        String title = null;
        String date = null;
        String releaseDate = null;
        String director = null;
        List<String> actors = new ArrayList<>();
        assert movies != null;
        for (int i = 0; i < movies.length(); i++) {
            JSONObject movie = movies.getJSONObject(i);
            iter = movie.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                JSONObject jsonPredicade = (JSONObject) movie.get(key);
                if(key.equals("title") && title == null) {
                    title = (String) jsonPredicade.get("value");
                } else if(key.equals("date") && date == null) {
                    date = (String) jsonPredicade.get("value");
                } else if(key.equals("release_date") && releaseDate == null) {
                    releaseDate = (String) jsonPredicade.get("value");
                } else if(key.equals("director_name") && director == null) {
                    director = (String) jsonPredicade.get("value");
                } else if(key.equals("actor_name")) {
                    String actor = (String) jsonPredicade.get("value");
                    if(!actors.contains(actor))
                        actors.add(actor);
                }
            }
        }

        movieEntity.setTitle(title);
        movieEntity.setReleased(releaseDate);
        movieEntity.setDirector(director);
        movieEntity.setActors(actors);

        Log.d(MovieOkHttp.class.getCanonicalName(), "fromJsonToMovieEntity: " + movies);

        return movieEntity;
    }
}
