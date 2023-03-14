package ec.com.students.sofka.api.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StudentDTO {

    private String id;
    @NotNull(message = "Identification number required")
    private String idNum;
    @NotNull(message = "Firstname required")
    private String firstName;
    @NotNull(message = "Lastname required")
    private String lastName;
    private Boolean blocked;
    private List<String> books;
}
