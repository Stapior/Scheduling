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

    @Override
    public String toString() {
        return index + " " + String.format("%03d", size) + " " + result;
    }
}
