package com.example.biblioteca;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BookOperationsRepository  extends CrudRepository<BookOperations, Long> {
    @Query(nativeQuery= true, value="select max(mo.id) from book_operations mo where mo.book_id = :bookId")
    Long getLastOperationId(Long bookId);
}

