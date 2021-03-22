package algorithms.genetic;

import algorithms.genetic.geneticoperations.GeneticOperations;
import algorithms.genetic.model.Individual;
import algorithms.genetic.model.ParametersGenetic;
import algorithms.genetic.selection.RankSelection;
import algorithms.genetic.selection.RouletteWheelSelection;
import algorithms.genetic.selection.Selection;
import algorithms.genetic.selection.TournamentSelection;
import commons.Algorithm;
import commons.Result;
import lombok.RequiredArgsConstructor;
import model.City;
import model.Depot;
import model.Vehicle;
import utils.Writer;

import java.util.Comparator;
import java.util.List;

import static utils.Utils.getDepotByCity;

@RequiredArgsConstructor
public class GeneticAlgorithm implements Algorithm {

    private final List<City> cities;
    private final List<Vehicle> vehicles;
    private final Depot depot;
    private final ParametersGenetic params;

    private List<Individual> population;
    private Selection selection;


    public void start() {
        population = new GenerationPopulation(cities, vehicles, depot, params.getPopulationSize()).initPopulation();
        population.sort(Comparator.comparing(Result::getSum));
        Writer.buildTitleOnConsole("The best generated individual");
        Writer.writeResult(population.stream().findFirst().orElseThrow());

        for (int i = 0; i < params.getIterationNumber(); i++) {
            //Writer.buildTitleOnConsole("ITERATION = " + i);
            selection();
            geneticOperations();
        }
        population.sort(Comparator.comparing(Result::getSum));
        Individual theBest = population.stream().findFirst().orElseThrow();
        Writer.buildTitleOnConsole("Final result");
        Writer.writeResult(theBest);
    }

    private void selection() {
        //Writer.buildTitleOnConsole("Selection");
        population.sort(Comparator.comparing(Result::getSum));
        switch (params.getSelectionMethod()) {
            case ROULETTE_WHEEL:
                selection = new RouletteWheelSelection(population, params);
                break;
            case RANK:
                selection = new RankSelection(population, params);
                break;
            case TOURNAMENT:
                selection = new TournamentSelection(population, params);
                break;
        }
        selection.makeSelection();
    }

    private void geneticOperations() {
        //Writer.buildTitleOnConsole("Crossover");

        GeneticOperations geneticOperations = new GeneticOperations(selection.getPairIndividuals(), cities, vehicles,
                getDepotByCity(depot), params, selection.getTheBest());
        geneticOperations.geneticOperations();
        population = geneticOperations.getPopulationNew();
        //Writer.buildTitleOnConsole("New population");
        //Writer.writePopulation(population);
    }

}
