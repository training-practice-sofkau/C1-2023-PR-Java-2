package ec.com.students.sofka.api.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private String id;

    @NotNull(message = "Student's ID number can't be null")
    @Size(min = 10, max = 12, message = "ID must be between 6 and 12 characters long")
    private String idNumber;

    @NotNull(message = "Name can't be null")
    private String name;

    @NotNull(message = "Last name can't be null")
    private List<String> lastName;

    private List<String> banned;

    private Integer books;
}
