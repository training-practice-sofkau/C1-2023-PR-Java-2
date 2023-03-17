package ec.com.students.sofka.api.domain.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "students")
public class Student {
    @Id
    private String id;

    @NonNull

    private String idNum;

    @NonNull
    private String name;

    @NonNull

    private String lastName;

    private Boolean blocked = false;

    public Student(@NonNull String idNum, @NonNull String name, @NonNull String lastName) {
        this.id = UUID.randomUUID().toString().substring(0, 10);;
        this.idNum = idNum;
        this.name = name;
        this.lastName = lastName;
        this.blocked  = false;
    }

    public void setId(String id) {
        this.id = id;
    }
}
