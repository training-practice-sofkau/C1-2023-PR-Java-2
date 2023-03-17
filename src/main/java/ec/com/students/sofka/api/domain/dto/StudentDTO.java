package ec.com.students.sofka.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

        public StudentDTO(String idNum, String name, String lastName, Boolean blocked) {
                this.idNum = idNum;
                this.name = name;
                this.lastName = lastName;
                this.blocked = blocked;
        }

        private String id;
        @NotNull(message = "Id num can't be blank")
        private String idNum;
        @NotNull(message = "Id name can't be blank")
        private String name;
        @NotNull(message = "Id lastName can't be blank")
        private String lastName;
        private Boolean blocked = false;
}
