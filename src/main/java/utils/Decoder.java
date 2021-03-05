package utils;

import model.City;
import model.Vehicle;

import java.util.List;
import java.util.Map;

//@RequiredArgsConstructor
public class Decoder {

    private final List<City> cities;
    private Integer[][] decodedIndividual;

    public Decoder(List<City> cities) {
        this.cities = cities;
    }

    //todo sorted vehicle by id in a map
    public Integer[][] decodeIndividual(Map<Vehicle, List<City>> individual) {
        initDecoders(individual.keySet().size(), cities.size());
        for (int i = 0; i < individual.keySet().size(); i++) {
            Vehicle vehicle = (Vehicle) individual.keySet().toArray()[i];
            for (int j = 1; j < individual.get(vehicle).size() - 1; j++) { //iterate without depot: <1, N-1>
                City foundCity = individual.get(vehicle).get(j);
                decodedIndividual[i][cities.indexOf(foundCity)] = j;
            }
        }
        return decodedIndividual;
    }

    private void initDecoders(int numberOfVehicle, int numberOfCity) {
        decodedIndividual = new Integer[numberOfVehicle][numberOfCity];
        for (int i = 0; i < numberOfVehicle; i++) {
            for (int j = 0; j < numberOfCity; j++) {
                decodedIndividual[i][j] = 0;
            }
        }
    }
}