package ec.com.students.sofka.api.domain.dto;

import ec.com.students.sofka.api.domain.book.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private String id;

    private String idNum;
    private String name;
    private String lastname;
    //private Boolean blocked;
    private List<Book> books;
}
