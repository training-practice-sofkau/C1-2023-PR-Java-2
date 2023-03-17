package ec.com.students.sofka.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private String id;
    private String idNum;
    @NotNull(message = "The name cannot be null")
    private String name;
    @NotNull(message = "The last name cannot be null")
    private String lastName;
    private boolean blocked;
    private List<String> books;

}
