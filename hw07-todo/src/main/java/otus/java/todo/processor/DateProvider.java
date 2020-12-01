package otus.java.todo.processor;

import java.time.LocalDateTime;

public interface DateProvider {
    LocalDateTime getDate();
}