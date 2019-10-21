package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private int duration;
    private int startTime;
    private int endTime;

    public String toFileFormat() {
        return duration + " " + startTime + " " + endTime;
    }
}
