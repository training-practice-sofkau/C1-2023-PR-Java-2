package ec.com.students.sofka.api.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private String id;

    private String idNum;

    private String name;

    private String lastName;

    private Boolean blocked;

    //private List<String> books;

}
