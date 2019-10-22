package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private int duration;
    private int startTime;
    private int endTime;
    private int id;

    public String toFileFormat() {
        return duration + " " + startTime + " " + endTime;
    }
}
