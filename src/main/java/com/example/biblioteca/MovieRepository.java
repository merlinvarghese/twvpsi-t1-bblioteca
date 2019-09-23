package com.example.biblioteca;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MovieRepository extends CrudRepository<Movie,Long> {
}
