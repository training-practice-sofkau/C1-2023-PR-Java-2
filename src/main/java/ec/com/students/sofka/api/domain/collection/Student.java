package ec.com.students.sofka.api.domain.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "students")
public class Student {
    @Id
    private String id = UUID.randomUUID().toString().substring(0, 10);

    @NonNull

    private String idNum;

    @NonNull
    private String name;

    @NonNull

    private String lastName;

    private Boolean blocked = false;

   // private List<Book> books = new ArrayList<>();


}
