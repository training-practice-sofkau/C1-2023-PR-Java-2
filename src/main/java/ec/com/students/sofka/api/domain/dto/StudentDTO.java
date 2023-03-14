package ec.com.students.sofka.api.domain.dto;

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

    private String idNum;
    private String name;
    private String lastname;
    private String blocked;
    private List<String> books;
}
