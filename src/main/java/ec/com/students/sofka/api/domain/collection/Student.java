package ec.com.students.sofka.api.domain.collection;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@Document(collection = "students")
public class Student {
    @Id
    private String id = UUID.randomUUID().toString().substring(1, 10);
    @NotNull(message = "Id num can't be blank")
    private String idNum;
    @NotNull(message = "Id name can't be blank")
    private String name;
    @NotNull(message = "Id lastName can't be blank")
    private String lastName;
    private Boolean blocked = false;
}
