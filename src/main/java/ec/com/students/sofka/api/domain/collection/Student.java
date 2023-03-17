package ec.com.students.sofka.api.domain.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Document(collection = "students")
public class Student {

    @Id
    private String id; // UUID.randomUUID().toString().substring(0, 10);

    //@NotNull(message = "idNum can't be null")
    private String idNum;

    //@NotNull(message = "name can't be null")
    private String name;

    //@NotNull(message = "lastName can't be null")
    private String lastName;

    private Boolean blocked; // false;

    //private List<String> books;


    public Student( String idNum, String name, String lastName) {
        this.id = UUID.randomUUID().toString().substring(0, 10);
        this.idNum = idNum;
        this.name = name;
        this.lastName = lastName;
        this.blocked = false;
    }
}
