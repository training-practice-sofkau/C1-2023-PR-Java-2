package ec.com.students.sofka.api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private String id;
    @NotBlank(message = "name is required")
    @Pattern(regexp = "^[0-9]{7,}$", message = "your id number should have only 6 numbers at least")
    private String idNum;
    @NotBlank(message = "name is required")
    @Pattern(regexp = "^[a-zA-Z ]{2,}$", message = "This field must have at least 2 characters.")
    private String name;
    @NotBlank(message = "name is required")
    @Pattern(regexp = "^[a-zA-Z ]{2,}$", message = "This field must have at least 2 characters.")
    private String lastName;
    private boolean isBlocked;
    private List<String> books = new ArrayList<String>();
}
