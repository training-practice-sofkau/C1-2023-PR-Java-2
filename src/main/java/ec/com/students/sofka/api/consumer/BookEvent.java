package ec.com.students.sofka.api.consumer;

import ec.com.students.sofka.api.domain.book.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookEvent {
    private String idEstudiante;
    private Book bookLended;
    private String tipoEvento;
}
