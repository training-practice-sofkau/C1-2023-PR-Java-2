package ec.com.students.sofka.api.service;

import ec.com.students.sofka.api.domain.collection.Book;
import ec.com.students.sofka.api.domain.dto.BookDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBookService {

    Flux<BookDTO> findAll();

    Mono<BookDTO> findById(String id);

    Mono<BookDTO> save(BookDTO bookDTO);

    Mono<BookDTO> update(String id, BookDTO bookDTO);

    Mono<Void> delete(String id);

    BookDTO toDto(Book book);

    Book toEntity(BookDTO bookDTO);

}
