package ec.com.students.sofka.api.domain.collection;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "students")
public class Student {

    @Id
    private String id = UUID.randomUUID().toString().substring(0, 10);

    private String idNumber;

    private String name;

    private String lastName;

    private Boolean active;

    private List<Book> books;

}
