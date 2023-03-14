package ec.com.students.sofka.api.domain.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class BookDTO {

    @Id
    private String id;

    private String isbn;

    private String title;

    private List<String> authors;

    private List<String> genres;

    private Integer year;

    private Boolean available;

}
