package com.example.biblioteca;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BookRepository extends CrudRepository<Book, Long> {
    String AVAILABLE_BOOKS = "select * from book where id not in " +
            "(select book_id from book_operations " +
            "where id in(select max(id) from book_operations group by book_id) " +
            "and type in ('CHECKOUT'))";
    ​
    @Query(nativeQuery= true, value=AVAILABLE_BOOKS + "limit :count")
    List<Movie> getBooksByCount(Long count);
}
