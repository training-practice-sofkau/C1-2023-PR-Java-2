package ec.com.students.sofka.api.domain.dto;

import ec.com.students.sofka.api.domain.collection.Book;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

@Data
public class StudentDTO {

    @Id
    private String id = UUID.randomUUID().toString().substring(0, 10);

    private String idNumber;

    private String name;

    private String lastName;

    private Boolean active;

    private List<Book> books;

}
