package ec.com.students.sofka.api.resource;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.service.impl.StudentServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

//@RestController
@AllArgsConstructor
public class StudentResource {

    private final StudentServiceImpl studentService;

    @GetMapping("/students")
    private Mono<ResponseEntity<Flux<StudentDTO>>> getAllStudent(){
        return studentService.getAllStudent()
                .switchIfEmpty(Flux.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                .collectList()
                .map(studentDTO -> new ResponseEntity<>(Flux.fromIterable(studentDTO),HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));

    }

    @GetMapping("/students/{id}")
    private Mono<ResponseEntity<StudentDTO>> getStudentById(@PathVariable String id){
        return studentService.getStudentById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(studentDTO -> new ResponseEntity<>(studentDTO, HttpStatus.FOUND))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @PostMapping("/students")
    private Mono<ResponseEntity<StudentDTO>> saveStudent(@Valid @RequestBody StudentDTO studentDTO){
        return studentService
                .saveStudent(studentDTO)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.EXPECTATION_FAILED.toString())))
                .map(studentDTO1 -> new ResponseEntity<>(studentDTO1,HttpStatus.CREATED))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED)));
    }

    @PutMapping("/students/{id}")
    private Mono<ResponseEntity<StudentDTO>> updateStudent(@PathVariable String id,@RequestBody StudentDTO studentDTO){
        return studentService
                .updateStudent(id,studentDTO)
                .switchIfEmpty(Mono.error(new Exception("Id not found")))
                .map(studentDTO1 -> new ResponseEntity<>(studentDTO1,HttpStatus.CREATED))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(studentDTO,HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/students/{id}")
    private Mono<ResponseEntity<String>> deleteStudent(@PathVariable String id){
        return studentService
                .deleteStudent(id)
                .flatMap(s -> Mono.just(new ResponseEntity<String>(HttpStatus.OK)))
                .onErrorResume(throwable -> Mono.just(
                        new ResponseEntity<>(HttpStatus.NOT_FOUND)
                ));
    }

}
