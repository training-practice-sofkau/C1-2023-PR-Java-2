package ec.com.students.sofka.api.service.impl;

import ec.com.students.sofka.api.domain.collection.Book;
import ec.com.students.sofka.api.domain.dto.BookDTO;
import ec.com.students.sofka.api.repository.BookRepository;
import ec.com.students.sofka.api.service.IBookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Flux<BookDTO> findAll() {
        return bookRepository.findAll()
                .switchIfEmpty(Flux.empty())
                .map(this::toDto);
    }

    @Override
    public Mono<BookDTO> findById(String id) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .map(this::toDto);
    }

    @Override
    public Mono<BookDTO> save(BookDTO bookDTO) {
        return bookRepository.save(toEntity(bookDTO))
                .map(this::toDto);
    }

    @Override
    public Mono<BookDTO> update(String id, BookDTO bookDTO) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .map(book -> {
                    book.setIsbn(bookDTO.getIsbn());
                    book.setTitle(bookDTO.getTitle());
                    book.setAuthors(bookDTO.getAuthors());
                    book.setGenres(bookDTO.getGenres());
                    book.setYear(bookDTO.getYear());
                    book.setAvailable(bookDTO.getAvailable());
                    return book;
                })
                .flatMap(bookRepository::save)
                .map(this::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(bookRepository::delete);
    }

    @Override
    public BookDTO toDto(Book book) {
        return new ModelMapper().map(book, BookDTO.class);
    }

    @Override
    public Book toEntity(BookDTO bookDTO) {
        return new ModelMapper().map(bookDTO, Book.class);
    }

}
