package ec.com.students.sofka.api.usecases.impl;

import ec.com.students.sofka.api.domain.book.Book;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface AddBookList {
    Mono<StudentDTO> add(String id, Book book);
}
