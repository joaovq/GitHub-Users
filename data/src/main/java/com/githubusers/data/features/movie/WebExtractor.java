package com.githubusers.data.features.movie;

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

            emitter.onNext(movieEntity);
            emitter.onComplete();
        });
    }
}
