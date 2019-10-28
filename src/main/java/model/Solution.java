package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Solution {
    private Problem problem;

    private long delay;

    public boolean checkDelay() {
        return delay == problem.getDelay();
    }

    public boolean isProblemResolved() {
        return problem.isProblemResolved();
    }

    public boolean verifySolution() {
        return checkDelay() && isProblemResolved();
    }
}
