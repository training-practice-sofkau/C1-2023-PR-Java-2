package ec.com.students.sofka.api.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.com.students.sofka.api.usecases.UpdateBookListUsecase;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StudentConsumer {
    private final ObjectMapper objectMapper;
    private final UpdateBookListUsecase updateBookListUsecase;

    @RabbitListener(queues = "books.queue") //lending queue
    public void receiveEventBook(String message) throws JsonProcessingException{
        BookEvent event = objectMapper.readValue(message, BookEvent.class);
        updateBookListUsecase.add(event.getIdEstudiante(), event.getBookLended());

    }

}
