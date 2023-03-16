package ec.com.students.sofka.api.usecases.interfaces;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface IDeleteStudent {

    Mono<Void> delete(String id);
}
