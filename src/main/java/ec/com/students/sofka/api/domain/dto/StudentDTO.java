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
