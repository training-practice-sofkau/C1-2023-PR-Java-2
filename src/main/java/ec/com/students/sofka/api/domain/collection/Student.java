package ec.com.students.sofka.api.domain.collection;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="students")
public class Student {

    @Id
    private String id =UUID.randomUUID().toString().substring(0,10);

    @NotNull(message ="idNum is required")
    private String idNum;
    @NotNull(message ="name is required")
    private String name;
    @NotNull(message ="lastname is required")
    private String lastname;
    private Boolean blocked;
    private List<String> books;
}
