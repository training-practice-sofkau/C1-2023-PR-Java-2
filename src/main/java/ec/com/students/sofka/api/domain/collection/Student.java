package ec.com.students.sofka.api.domain.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "SofkaReactive")
public class Student {

    @Id
    private String studentID = UUID.randomUUID().toString().substring(0, 10);

    @NotNull(message = "Student's ID number can't be null")
    @Size(min = 10, max = 12, message = "ID must be between 6 and 12 characters long")
    private String idNumber;

    @NotNull(message = "Name can't be null")
    private String name;

    @NotNull(message = "Last name can't be null")
    private String lastName;

    private Boolean banned = false;

    private List<Integer> books = new ArrayList<>();

}
