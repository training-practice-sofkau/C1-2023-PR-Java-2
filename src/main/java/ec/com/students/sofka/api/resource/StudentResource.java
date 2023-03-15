package ec.com.students.sofka.api.resource;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.service.impl.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.naming.NotContextException;

@RestController
@RequestMapping(path = "/students")
public class StudentResource {

    @Autowired
    private StudentService studentService;

    @GetMapping(path = "/")
    public Mono<ResponseEntity<Flux<StudentDTO>>> getAll(){
        return studentService.findAll()
                .switchIfEmpty(Flux.error(new Exception("No content found")))
                .collectList()
                .map(studentDTOS -> new ResponseEntity<>(Flux.fromIterable(studentDTOS), HttpStatus.OK))
                .onErrorResume(error -> Mono.just(new ResponseEntity<>(Flux.empty(), HttpStatus.NO_CONTENT)));
    }

    @GetMapping(path = "/{id}")
    public Mono<ResponseEntity<StudentDTO>> getById(@PathVariable String id){
        return studentService.findById(id)
                .switchIfEmpty(Mono.error(new Exception("No content found")))
                .map(studentDTO -> new ResponseEntity<>(studentDTO, HttpStatus.OK))
                .onErrorResume(error -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @PostMapping(path = "/")
    public Mono<ResponseEntity<StudentDTO>> save(@RequestBody StudentDTO studentDTO){
        return studentService.save(studentDTO)
                .switchIfEmpty(Mono.error(new Exception("No content found")))
                .map(studentDTO1 -> new ResponseEntity<>(studentDTO1, HttpStatus.CREATED))
                .onErrorResume(error -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @PutMapping(path = "/{id}")
    public Mono<ResponseEntity<StudentDTO>> update(@PathVariable String id, @RequestBody StudentDTO studentDTO){
        return studentService.update(id, studentDTO)
                .switchIfEmpty(Mono.error(new Exception("No content found")))
                .map(studentDTO1 -> new ResponseEntity<>(studentDTO1, HttpStatus.OK))
                .onErrorResume(error -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping(path = "/{id}")
    public Mono<ResponseEntity<String>> delete(@PathVariable String id){
        return studentService.delete(id)
                .map(response -> ResponseEntity.ok("Deleted successfully"))
                .onErrorResume(error -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

}
