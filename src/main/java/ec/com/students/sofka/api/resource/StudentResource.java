package ec.com.students.sofka.api.resource;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.service.imp.StudentServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class StudentResource {
    private final StudentServiceImp studentService;

//    @PostMapping("/students")
//    private Mono<ResponseEntity<StudentDTO>> save(@Valid @RequestBody StudentDTO studentDTO){
//        return this.studentService
//                .saveStudent(studentDTO)
//                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.EXPECTATION_FAILED.toString())))
//                .map(studentDTO1 -> new ResponseEntity<>(studentDTO1,HttpStatus.CREATED))
//                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED)));
//    }

    @GetMapping("/students")
    private Mono<ResponseEntity<Flux<StudentDTO>>> getAll(){
        return this.studentService
                .getAllStudents()
                .switchIfEmpty(Flux.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                .collectList()
                .map(studentDTOS -> new ResponseEntity<>(Flux.fromIterable(studentDTOS), HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));
    }

    @GetMapping("/students/{id}")
    private Mono<ResponseEntity<StudentDTO>> getById(@PathVariable String id){
        return this.studentService
                .getStudentById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(studentDTO -> new ResponseEntity<>(studentDTO, HttpStatus.FOUND))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));
    }


//    @PutMapping("/students/{id}")
//    private Mono<ResponseEntity<StudentDTO>> update(@PathVariable String id, @Valid @RequestBody StudentDTO studentDTO){
//        return this.studentService
//                .updateStudent(id, studentDTO)
//                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
//                .map(studentDTO1 -> new ResponseEntity<>(studentDTO1, HttpStatus.OK))
//                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(studentDTO, HttpStatus.NOT_FOUND)));
//    }

    @DeleteMapping("/students/{id}")
    private Mono<ResponseEntity<String>> delete(@PathVariable String id){
        return this.studentService
                .deleteStudent(id)
                .map(s -> ResponseEntity.ok("Student deleted"))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }
}
