package ec.com.students.sofka.api.domain.collection;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "books")
public class Book {

    @Id
    private String id;
    
    private String isbn;
    
    private String title;
    
    private List<String> authors;

    private List<String> genres;

    private Integer year;

    private Boolean available;

}
