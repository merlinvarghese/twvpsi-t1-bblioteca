package com.example.biblioteca;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface MovieRepository extends CrudRepository<Movie,Long> {
    String AVAILABLE_MOVIES = "select * from movie where id not in " +
            "(select movie_id from movie_operations " +
                "where id in(select max(id) from movie_operations group by movie_id) " +
                "and type in ('CHECKOUT'))";

    @Query(nativeQuery= true, value=AVAILABLE_MOVIES + "limit :count")
    List<Movie> getMoviesByCount(Long count);
}
