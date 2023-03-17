package ec.com.students.sofka.api.domain.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
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
    private String idNum;
    @NotNull(message = "The name cannot be null")
    private String name;
    @NotNull(message = "The last name cannot be null")
    private String lastName;
    private boolean blocked =  false;
    private List<String> books = new ArrayList<>();

}
