package common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private long id;
    private String name;
    private Priority priority;
    private LocalDateTime deadline;
    private double price;
    private Collection<Task> relatedTask;
    private String[] tags;
    
    
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }
    
    @Override public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override public String toString() {
        return new StringJoiner(", ", Task.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("priority=" + priority)
                .add("deadline=" + deadline)
                .add("price=" + price)
                .toString();
    }
    
    
    public enum Priority {LOW, MIDDLE, HIGH}
}
