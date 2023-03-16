package ec.com.students.sofka.api.util;

import ec.com.students.sofka.api.domain.collection.Student;

import java.util.ArrayList;
import java.util.List;

public class InstanceProvider {
    public static List<Student> getStudents(){
        return List.of(
                new Student("ID1", "IdNum1", "Wout", "Van Aert", false, List.of("Book1")),
                new Student("ID2", "IdNum2", "Remco", "Evenepoel", false, List.of("Book2")),
                new Student("ID3", "IdNum3", "Jasper", "Philipsen", false, List.of("Book3", "Book4"))
                );
    }

    public static Student getStudent(){
        return new Student("ID1", "IdNum1", "Wout", "Van Aert", false, List.of("Book1"));
    }
}
