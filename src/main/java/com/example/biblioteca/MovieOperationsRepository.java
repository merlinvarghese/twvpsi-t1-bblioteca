package com.example.biblioteca;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MovieOperationsRepository extends CrudRepository<MovieOperations, Long> {
    @Query(nativeQuery= true, value="select max(mo.id) from movie_operations mo where mo.movie_id = :movieId")
    Long getLastOperationId(Long movieId);
}
