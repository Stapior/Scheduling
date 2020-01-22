package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public
class Stats {
    String index;
    int size;
    long result;
    long time;

    @Override
    public String toString() {
        return index + " " + String.format("%03d", size) + " " + result;
    }

    public String toResult() {
        return String.format("%03d", size) +  " " +index + " " + String.format("%08d", time) + " " + result;
    }
}
