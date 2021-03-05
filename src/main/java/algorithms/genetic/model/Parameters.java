package algorithms.genetic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Parameters {
    private final int populationSize;
    private final int iterationNumber;
    private final double crossoverProbability;
    private final double mutationProbability;
}
