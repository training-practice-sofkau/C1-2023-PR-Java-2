package ec.com.students.sofka.api.domain.collection;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Document(collection = "students")
public class Student {

    @Id
    private String id = UUID.randomUUID().toString().substring(0, 10);

    @NotNull(message = "ID number cannot be null")
    @Size(min = 8, max = 10, message = "Id number should have between 8 and 10 characters")
    private String idNum;

    @NotNull(message = "A name must be entered")
    @Size(min = 1, message = "Name should have at least one character")
    private String studentName;

    @NotNull(message = "A lastname must be entered")
    @Size(min = 1, message = "Lastname should have at least one character")
    private String studentLastName;

    private Boolean blocked = false;

    private List<String> books;


}
