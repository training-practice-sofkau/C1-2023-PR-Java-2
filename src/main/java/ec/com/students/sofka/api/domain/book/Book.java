package ec.com.students.sofka.api.domain.book;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Book {
    private String id;
    private String isbn;
    private String title;
    private List<String> authors;
    private List<String> categories;
    private Integer year;
    private Boolean available;
}
