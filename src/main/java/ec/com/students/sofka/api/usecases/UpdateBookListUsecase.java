package ec.com.students.sofka.api.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import ec.com.students.sofka.api.consumer.BookEvent;
import ec.com.students.sofka.api.consumer.StudentConsumer;
import ec.com.students.sofka.api.domain.book.Book;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.StudentRepository;
import ec.com.students.sofka.api.usecases.impl.AddBookList;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class UpdateBookListUsecase implements AddBookList {
    private final ModelMapper mapper;
    private final StudentRepository studentRepository;

    @Override
    public Mono<StudentDTO> add(String id, Book book){
        //System.out.println("Student id: " +id);
        //System.out.println("Book: "+book);
        return this.studentRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(student -> {
                    var listOfBooks = student.getBooks();
                    listOfBooks.add(book);
                    student.setName("Mishelle");
                    student.setBooks(listOfBooks);
                    return this.studentRepository.save(student);
                })
                .map(student -> mapper.map(student, StudentDTO.class));
    }

}
