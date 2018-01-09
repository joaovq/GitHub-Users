package com.githubusers.data.features.movie;

import android.util.Log;

import java.io.IOException;
import io.reactivex.Observable;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 */
public class MovieOkHttp {
    public Observable<MovieEntity> getMovie(String title){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getParametros(title))
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
            Observable.error(e);
        }
        assert response != null;
        Log.d(MovieOkHttp.class.getCanonicalName(),response.toString());
        return Observable.just(new MovieEntity());
    }

    private String getParametros(String title) {
        String firstPart = "http://www.linkedmdb.org/sparql?query=PREFIX+owl%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2002%2F07%2Fowl%23%3E%0D%0APREFIX+xsd%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0D%0APREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E%0D%0APREFIX+foaf%3A+%3Chttp%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2F%3E%0D%0APREFIX+rdf%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0D%0APREFIX+oddlinker%3A+%3Chttp%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Foddlinker%2F%3E%0D%0APREFIX+map%3A+%3Cfile%3A%2FC%3A%2Fd2r-server-0.4%2Fmapping.n3%23%3E%0D%0APREFIX+db%3A+%3Chttp%3A%2F%2Fdata.linkedmdb.org%2Fresource%2F%3E%0D%0APREFIX+dbpedia%3A+%3Chttp%3A%2F%2Fdbpedia.org%2Fproperty%2F%3E%0D%0APREFIX+skos%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2004%2F02%2Fskos%2Fcore%23%3E%0D%0APREFIX+dc%3A+%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0D%0APREFIX+movie%3A+%3Chttp%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Fmovie%2F%3E%0D%0ASELECT+%3Fresource+WHERE+%7B%0D%0A++++%3Fresource+movie%3Afilmid+%3Furi+.%0D%0A++++%3Fresource+dc%3Atitle+%22";
        String secondPart = "%22+.%0D%0A%7D&output=json";

        title = title.replace(" ", "+");

        String parametro = firstPart + title + secondPart;

        Log.d(MovieOkHttp.class.getCanonicalName(),parametro);

        return parametro;
    }
}
