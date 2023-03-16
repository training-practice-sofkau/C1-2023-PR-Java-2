package ec.com.students.sofka.api.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

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
