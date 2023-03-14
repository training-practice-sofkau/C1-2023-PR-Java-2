package ec.com.students.sofka.api.domain.dto;

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
    private String idNum;
    private String name;
    private String lastName;
    private boolean isBlocked;
    private List<String> books = new ArrayList<String>();
}
