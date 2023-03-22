package ec.com.students.sofka.api.domain.collection;

import ec.com.students.sofka.api.domain.book.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "students")
public class Student {
    @Id
    private String id = UUID.randomUUID().toString().substring(0,10);
    private String idNum;
    private String name;
    private String lastname;
    //private Boolean blocked;
    private List<Book> books = new ArrayList<>();
}
