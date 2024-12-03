package hva;

import java.io.Serial;
import java.io.Serializable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import hva.exceptions.ImportFileException;
import hva.exceptions.UnrecognizedEntryException;
import hva.exceptions.WrongVaccineException;
import hva.exceptions.NoSuchResponsabilityException;
import hva.exceptions.UnauthorizedVeterinarianException;
import hva.exceptions.duplicated.DuplicatedAnimalIdException;
import hva.exceptions.duplicated.DuplicatedHabitatIdException;
import hva.exceptions.duplicated.DuplicatedEmployeeIdException;
import hva.exceptions.duplicated.DuplicatedVaccineIdException;
import hva.exceptions.duplicated.DuplicatedTreeIdException;
import hva.exceptions.duplicated.DuplicatedSpeciesIdException;
import hva.exceptions.duplicated.DuplicatedSpeciesNameException;
import hva.exceptions.unknown.UnknownAnimalIdException;
import hva.exceptions.unknown.UnknownEmployeeIdException;
import hva.exceptions.unknown.UnknownHabitatIdException;
import hva.exceptions.unknown.UnknownSpeciesIdException;
import hva.exceptions.unknown.UnknownTreeIdException;
import hva.exceptions.unknown.UnknownVaccineIdException;
import hva.exceptions.unknown.UnknownVeterinarianIdException;
import hva.strategies.AnimalSatisfactionStrategy;
import hva.strategies.HabitatWorkStrategy;
import hva.strategies.SatisfactionStrategy;
import hva.strategies.TreeWorkStrategy;
import hva.strategies.VeterinarianSatisfactionStrategy;
import hva.strategies.VeterinarianVaccinationStrategy;
import hva.strategies.ZookeeperSatisfactionStrategy;
import hva.tree.Tree;
import hva.treeStates.DeciduousTreeStateSpring;
import hva.treeStates.EvergreenTreeStateSpring;
import hva.tree.DeciduousTree;
import hva.tree.EvergreenTree;
import hva.employee.Employee;
import hva.employee.Zookeeper;
import hva.employee.Veterinarian;
import hva.enums.VaccineDamage;
import hva.enums.HabitatInfluence;
import hva.Species;
import hva.Habitat;
import hva.Animal;
import hva.Vaccine;
import hva.Vaccination;


/**
 * The {@code Hotel} class represents a hotel that manages species, habitats, animals, employees, vaccines, and trees.
 * 
 * <p>This class provides methods to register, retrieve, and manage various entities within the hotel, such as species, habitats, animals, employees, vaccines, and trees. It also supports importing data from a file, handling vaccinations, and calculating satisfaction levels for animals and employees.</p>
 * 
 * <p>It implements the {@code Serializable} interface to allow its instances to be serialized and deserialized.</p>
 * 
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Registering and retrieving species, habitats, animals, employees, vaccines, and trees.</li>
 *   <li>Importing data from a text file.</li>
 *   <li>Managing vaccinations and tracking wrong vaccinations.</li>
 *   <li>Calculating satisfaction levels for animals and employees.</li>
 *   <li>Handling responsibilities of employees.</li>
 *   <li>Advancing the season for trees.</li>
 * </ul>
 * 
 * <p>Exceptions are thrown for various error conditions, such as unknown IDs, duplicated entries, and unauthorized actions.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * Hotel hotel = new Hotel();
 * hotel.registerSpecies("ESPÉCIE", "S1", "Lion");
 * hotel.registerHabitat("HABITAT", "H1", "Savannah", "1000");
 * hotel.registerAnimal("ANIMAL", "A1", "Simba", "S1", "H1");
 * hotel.registerEmployee("TRATADOR", "E1", "John");
 * hotel.registerVaccine("VACINA", "V1", "Rabies", "S1");
 * hotel.vaccinateAnimal("V1", "E1", "A1");
 * }
 * </pre>
 * 
 * @see hva.exceptions.ImportFileException
 * @see hva.exceptions.UnrecognizedEntryException
 * @see hva.exceptions.WrongVaccineException
 * @see hva.exceptions.NoSuchResponsabilityException
 * @see hva.exceptions.UnauthorizedVeterinarianException
 * @see hva.exceptions.duplicated.DuplicatedAnimalIdException
 * @see hva.exceptions.duplicated.DuplicatedHabitatIdException
 * @see hva.exceptions.duplicated.DuplicatedEmployeeIdException
 * @see hva.exceptions.duplicated.DuplicatedVaccineIdException
 * @see hva.exceptions.duplicated.DuplicatedTreeIdException
 * @see hva.exceptions.duplicated.DuplicatedSpeciesIdException
 * @see hva.exceptions.duplicated.DuplicatedSpeciesNameException
 * @see hva.exceptions.unknown.UnknownAnimalIdException
 * @see hva.exceptions.unknown.UnknownEmployeeIdException
 * @see hva.exceptions.unknown.UnknownHabitatIdException
 * @see hva.exceptions.unknown.UnknownSpeciesIdException
 * @see hva.exceptions.unknown.UnknownTreeIdException
 * @see hva.exceptions.unknown.UnknownVaccineIdException
 * @see hva.exceptions.unknown.UnknownVeterinarianIdException
 */
public class Hotel implements Serializable {

    /** Class serial number for serialization. */
    // @Serial // Removed as it is not available in Java SE
    private static final long serialVersionUID = 202407081733L;

    /** Collection of species in the hotel, identified by their IDs. */
    private Map<String, Species> _species = new HashMap<>();

    /** Collection of habitats in the hotel, identified by their IDs, case insensitive. */
    private Map<String, Habitat> _habitats = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Collection of animals in the hotel, identified by their IDs, case insensitive. */
    private Map<String, Animal> _animals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Collection of employees in the hotel, identified by their IDs, case insensitive. */
    private Map<String, Employee> _employees = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Collection of vaccines in the hotel, identified by their IDs, case insensitive. */
    private Map<String, Vaccine> _vaccines = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Collection of trees not assigned to any habitat, identified by their IDs. */
    private Map<String, Tree> _trees = new HashMap<>();

    /** Collection of all the vaccinations that happened in the hotel. */
    private List<Vaccination> _vaccinations = new ArrayList<>();

    /** Collection of all the vaccinations in which the vaccine was not the proper one for the animal */
    private List<Vaccination> _wrongVaccinations = new ArrayList<>();

    /** Indicates if the hotel object has been modified since the last change. */
    private boolean _changed = false;

    /** Shortcut to the first evergreen tree. */
    private Tree _rootEvergreenTree;
    
    /** Shortcut to the first deciduous tree. */
    private Tree _rootDeciduousTree;

    /**
     * Constructs a new {@code Hotel} object with default values.
     * 
     * <p>It initializes the root evergreen and deciduous trees.</p>
     */
    public Hotel() {
        // type|id|name|age|cleaningDifficulty|treetype
        String[] evergreenFields = {"ÁRVORE", "RootEvergreenTree", "EvergreenTree", "0", "0", "PERENE"};
        String[] deciduousFields = {"ÁRVORE", "RootDeciduousTree", "DeciduousTree", "0", "0", "CADUCA"};
        try {
            _rootEvergreenTree = registerTree(evergreenFields);
            _rootDeciduousTree = registerTree(deciduousFields);
            _trees.remove("RootEvergreenTree");
            _trees.remove("RootDeciduousTree");
            setChanged(false);
        } catch (UnrecognizedEntryException | DuplicatedTreeIdException e) {
            // should not happen
            e.printStackTrace();
        }
    }


    /**
     * Returns whether the hotel has been changed.
     * 
     * @return {@code true} if the hotel object has been changed, {@code false} otherwise.
     */
    public boolean hasChanged() {
        return _changed;
    }

    /**
     * Sets the changed status of the hotel.
     * 
     * @param changed the new changed status.
     */
    public void setChanged(boolean changed) {
        _changed = changed;
    }

    /**
     * Marks the hotel as changed.
     */
    public void changed() {
        setChanged(true);
    }

    /**
     * Retrieves the species with the specified ID.
     * 
     * @param id the ID of the species to retrieve.
     * @return the species corresponding to the given ID.
     * @throws UnknownSpeciesIdException if the ID does not correspond to a valid species.
     */
    public Species getSpecies(String id) throws UnknownSpeciesIdException {
        return fetchSpecies(id);
    }

    /**
     * Fetches the species identified by the given key.
     * 
     * @param key the ID of the species.
     * @return the species corresponding to the given key.
     * @throws UnknownSpeciesIdException if the species is not found.
     */
    private Species fetchSpecies(String key) throws UnknownSpeciesIdException {
        if (!_species.containsKey(key))
            throw new UnknownSpeciesIdException(key);
        return _species.get(key);
    }

    /**
     * Retrieves the habitat with the specified ID.
     * 
     * @param id the ID of the habitat to retrieve.
     * @return the habitat corresponding to the given ID.
     * @throws UnknownHabitatIdException if the ID does not correspond to a valid habitat.
     */
    public Habitat getHabitat(String id) throws UnknownHabitatIdException {
        return fetchHabitat(id);
    }

    /**
     * Fetches the habitat identified by the given key.
     * 
     * @param key the ID of the habitat.
     * @return the habitat corresponding to the given key.
     * @throws UnknownHabitatIdException if the habitat is not found.
     */
    private Habitat fetchHabitat(String key) throws UnknownHabitatIdException {
        if (!_habitats.containsKey(key))
            throw new UnknownHabitatIdException(key);
        return _habitats.get(key);
    }

    /**
     * Retrieves the animal with the specified ID.
     * 
     * @param id the ID of the animal to retrieve.
     * @return the animal corresponding to the given ID.
     * @throws UnknownAnimalIdException if the ID does not correspond to a valid animal.
     */
    public Animal getAnimal(String id) throws UnknownAnimalIdException {
        return fetchAnimal(id);
    }

    /**
     * Fetches the animal identified by the given key.
     * 
     * @param key the ID of the animal.
     * @return the animal corresponding to the given key.
     * @throws UnknownAnimalIdException if the animal is not found.
     */
    private Animal fetchAnimal(String key) throws UnknownAnimalIdException {
        if (!_animals.containsKey(key))
            throw new UnknownAnimalIdException(key);
        return _animals.get(key);
    }

    /**
     * Retrieves the employee with the specified ID.
     * 
     * @param id the ID of the employee to retrieve.
     * @return the employee corresponding to the given ID.
     * @throws UnknownEmployeeIdException if the ID does not correspond to a valid employee.
     */
    public Employee getEmployee(String id) throws UnknownEmployeeIdException {
        return fetchEmployee(id);
    }

    /**
     * Fetches the employee identified by the given key.
     * 
     * @param key the ID of the employee.
     * @return the employee corresponding to the given key.
     * @throws UnknownEmployeeIdException if the employee is not found.
     */
    private Employee fetchEmployee(String key) throws UnknownEmployeeIdException {
        if (!_employees.containsKey(key))
            throw new UnknownEmployeeIdException(key);
        return _employees.get(key);
    }

    /**
     * Retrieves the vaccine with the specified ID.
     * 
     * @param id the ID of the vaccine to retrieve.
     * @return the vaccine corresponding to the given ID.
     * @throws UnknownVaccineIdException if the ID does not correspond to a valid vaccine.
     */
    public Vaccine getVaccine(String id) throws UnknownVaccineIdException {
        return fetchVaccine(id);
    }

    /**
     * Fetches the vaccine identified by the given key.
     * 
     * @param key the ID of the vaccine.
     * @return the vaccine corresponding to the given key.
     * @throws UnknownVaccineIdException if the vaccine is not found.
     */
    private Vaccine fetchVaccine(String key) throws UnknownVaccineIdException {
        if (!_vaccines.containsKey(key))
            throw new UnknownVaccineIdException(key);
        return _vaccines.get(key);
    }

    /**
     * Retrieves the tree with the specified ID.
     * 
     * @param id the ID of the tree to retrieve.
     * @return the tree corresponding to the given ID.
     * @throws UnknownTreeIdException if the ID does not correspond to a valid tree.
     */
    public Tree getTree(String id) throws UnknownTreeIdException {
        return fetchTree(id);
    }

    /**
     * Fetches the tree identified by the given key.
     * 
     * @param key the ID of the tree.
     * @return the tree corresponding to the given key.
     * @throws UnknownTreeIdException if the tree is not found.
     */
    private Tree fetchTree(String key) throws UnknownTreeIdException {
        if (!_trees.containsKey(key))
            throw new UnknownTreeIdException(key);
        return _trees.get(key);
    }


    /**
     * Reads a text input file and creates the corresponding domain entities.
     * 
     * @param filename the name of the text input file.
     * @throws ImportFileException if there is an error reading the file.
     */
    public void importFile(String filename) throws ImportFileException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|");
                try {   
                    registerEntry(fields);
                } catch (UnrecognizedEntryException | DuplicatedAnimalIdException | DuplicatedEmployeeIdException
                        | DuplicatedHabitatIdException | DuplicatedSpeciesIdException | DuplicatedSpeciesNameException
                        | DuplicatedTreeIdException | DuplicatedVaccineIdException | UnknownHabitatIdException
                        | UnknownSpeciesIdException | UnknownTreeIdException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e1) {
            throw new ImportFileException(filename);
        }
    }
    
    /**
     * Registers an entry based on the provided fields.
     * 
     * <p>The first field should correspond to one of the following:</p>
     * <ul>
     *   <li> "ESPÉCIE" </li>
     *   <li> "HABITAT" </li>
     *   <li> "ANIMAL" </li>
     *   <li> "TRATADOR" </li>
     *   <li> "VETERINÁRIO" </li>
     *   <li> "VACINA" </li>
     *   <li> "ÁRVORE" </li>
     * </ul>
     * 
     * <p>If not, it throws UnrecognizedEntryException.</p>
     *
     * 
     * @param fields the fields describing the entity.
     * @throws UnrecognizedEntryException if the entry type is unrecognized.
     * @throws DuplicatedAnimalIdException if an animal with the same ID is already registered.
     * @throws DuplicatedEmployeeIdException if an employee with the same ID is already registered.
     * @throws DuplicatedHabitatIdException if a habitat with the same ID is already registered.
     * @throws DuplicatedSpeciesIdException if a species with the same ID is already registered.
     * @throws DuplicatedSpeciesNameException if a species with the same Name is already registered.
     * @throws DuplicatedTreeIdException if a tree with the same ID is already registered.
     * @throws DuplicatedVaccineIdException if a vaccine with the same ID is already registered.
     * @throws UnknownHabitatIdException if the habitat ID is unknown.
     * @throws UnknownSpeciesIdException if the species ID is unknown.
     * @throws UnknownTreeIdException if the tree ID is unknown.
     */
    public void registerEntry(String... fields) throws UnrecognizedEntryException, DuplicatedAnimalIdException,
        DuplicatedEmployeeIdException, DuplicatedHabitatIdException, DuplicatedSpeciesIdException, 
        DuplicatedSpeciesNameException, DuplicatedTreeIdException, DuplicatedVaccineIdException, UnknownHabitatIdException, 
        UnknownSpeciesIdException, UnknownTreeIdException {
        switch (fields[0]) {
            case "ESPÉCIE" -> registerSpecies(fields);
            case "HABITAT" -> registerHabitat(fields);
            case "ANIMAL" -> registerAnimal(fields);
            case "TRATADOR", "VETERINÁRIO" -> registerEmployee(fields);
            case "VACINA" -> registerVaccine(fields);
            case "ÁRVORE" -> registerTree(fields);
            default -> throw new UnrecognizedEntryException(fields[0]);
        }
    }

    /**
     * Registers a species using the provided fields.
     * 
     * <p><b>Fields format:</b></p>
     * <ul>
     *   <li> 0 - type (should be "ESPÉCIE") </li>
     *   <li> 1 - speciesId </li>
     *   <li> 2 - speciesName </li>
     * </ul>
     * 
     * <p>The type must be "ESPÉCIE", and the necessary fields must be provided. If these conditions are not met, an exception will be thrown.</p>
     * 
     * @param fields an array of strings containing the species' details
     * @throws UnrecognizedEntryException if the type provided is not "ESPÉCIE".
     * @throws DuplicatedSpeciesIdException if a species with the same ID already exists.
     * @throws DuplicatedSpeciesNameException if a species with the same name already exists.
     */
    public void registerSpecies(String... fields) throws UnrecognizedEntryException, DuplicatedSpeciesIdException, DuplicatedSpeciesNameException {
        if (!fields[0].equals("ESPÉCIE"))
            throw new UnrecognizedEntryException(fields[0]);

        Species species = new Species(fields[1], fields[2]);
        addSpecies(fields[1], species);
        changed();
    }

    /**
     * Registers a habitat using the provided fields.
     * 
     * <p><b>Fields format:</b></p>
     * <ul>
     *   <li> 0 - type (should be "HABITAT") </li>
     *   <li> 1 - habitatId </li>
     *   <li> 2 - habitatName </li>
     *   <li> 3 - area (in square meters) </li>
     *   <li> 4 - listOfTreeIds (optional, comma-separated) </li>
     * </ul>
     * 
     * <p>The type must be "HABITAT", and the area must be a valid integer. If these conditions are not met, an exception will be thrown.</p>
     * 
     * @param fields an array of strings containing the habitat's details
     * @throws UnrecognizedEntryException if the type provided is not "HABITAT".
     * @throws DuplicatedHabitatIdException if a habitat with the same ID is already registered.
     * @throws UnknownTreeIdException if any tree ID provided in the list is not recognized.
     */
    public void registerHabitat(String... fields) throws UnrecognizedEntryException, DuplicatedHabitatIdException, UnknownTreeIdException {
        if (!fields[0].equals("HABITAT"))
            throw new UnrecognizedEntryException(fields[0]);

        Habitat habitat = new Habitat(fields[1], fields[2], Integer.parseInt(fields[3]));
        if (fields.length > 4) {
            String[] treesIds = fields[4].split(",");
            for (String id : treesIds) {
                habitat.addTree(getTree(id));
            }
        }
        addHabitat(fields[1], habitat);
        changed();
    }

    /**
     * Registers an animal using the provided fields.
     * 
     * <p><b>Fields format:</b></p>
     * <ul>
     *   <li> 0 - type (should be "ANIMAL") </li>
     *   <li> 1 - animalId </li>
     *   <li> 2 - animalName </li>
     *   <li> 3 - speciesId </li>
     *   <li> 4 - habitatId </li>
     * </ul>
     * 
     * <p>The type must be "ANIMAL", and valid speciesId and habitatId must be provided. If these conditions are not met, an exception will be thrown.</p>
     * 
     * @param fields an array of strings containing the animal's details
     * @throws UnrecognizedEntryException if the type provided is not "ANIMAL".
     * @throws DuplicatedAnimalIdException if an animal with the same ID is already registered.
     * @throws UnknownSpeciesIdException if the species ID provided is not recognized.
     * @throws UnknownHabitatIdException if the habitat ID provided is not recognized.
     */
    public void registerAnimal(String... fields) throws UnrecognizedEntryException, DuplicatedAnimalIdException, 
        UnknownSpeciesIdException, UnknownHabitatIdException {
        if (!fields[0].equals("ANIMAL"))
            throw new UnrecognizedEntryException(fields[0]);

        Species species = getSpecies(fields[3]);
        Habitat habitat = getHabitat(fields[4]);

        Animal animal = new Animal(fields[1], fields[2], species, habitat);
        addAnimal(fields[1], animal);
        species.addAnimal(animal);
        habitat.addAnimal(animal);
        changed();
    }

    /**
     * Registers an employee using the provided fields.
     * 
     * <p><b>Fields format:</b></p>
     * <ul>
     *   <li> 0 - type (should be "TRATADOR"/"TRT" or "VETERINÁRIO"/"VET") </li>
     *   <li> 1 - employeeId </li>
     *   <li> 2 - employeeName </li>
     * </ul>
     * 
     * <p>The type must be either "TRATADOR" (Zookeeper) or "VETERINÁRIO" (Veterinarian). If these conditions are not met, an exception will be thrown.</p>
     * 
     * @param fields an array of strings containing the employee's details
     * @throws UnrecognizedEntryException if the type provided is not "TRATADOR" or "VETERINÁRIO".
     * @throws DuplicatedEmployeeIdException if an employee with the same ID is already registered.
     * @throws UnknownHabitatIdException if the any of the habitats atributed to the employee are not recognized.
     * @throws UnknownSpeciesIdException if the any of the species atributed to the employee are not recognized.
     */
    public void registerEmployee(String... fields) throws UnrecognizedEntryException, DuplicatedEmployeeIdException,
        UnknownHabitatIdException, UnknownSpeciesIdException {
        Employee employee  = switch (fields[0]) {
            case "TRATADOR", "TRT" -> {
                Zookeeper zookeeper = new Zookeeper(fields[1], fields[2]);
                if (fields.length > 3) {
                    String[] habitatsIds = fields[3].split(",");
                    for (String id : habitatsIds) {
                        zookeeper.assignHabitat(getHabitat(id));
                    }
                }
                yield zookeeper;
            }
            case "VETERINÁRIO", "VET" -> {
                Veterinarian veterinarian = new Veterinarian(fields[1], fields[2]);
                if (fields.length > 3) {
                    String[] speciesIds = fields[3].split(",");
                    for (String id : speciesIds) {
                        veterinarian.assignSpecies(getSpecies(id));
                    }
                }
                yield veterinarian;
            }
            default -> throw new UnrecognizedEntryException(fields[0]);
        };
        addEmployee(fields[1], employee);
        changed();
    }

    /**
     * Registers a vaccine using the provided fields.
     * 
     * <p><b>Fields format:</b></p>
     * <ul>
     *   <li> 0 - type (should be "VACINA") </li>
     *   <li> 1 - vaccineId </li>
     *   <li> 2 - vaccineName </li>
     *   <li> 3 - listOfSpeciesIds (optional, comma-separated) </li>
     * </ul>
     * 
     * <p>The type must be "VACINA", and valid species IDs must be provided in the list. If these conditions are not met, an exception will be thrown.</p>
     * 
     * @param fields an array of strings containing the vaccine's details
     * @throws UnrecognizedEntryException if the type provided is not "VACINA".
     * @throws DuplicatedVaccineIdException if a vaccine with the same ID is already registered.
     * @throws UnknownSpeciesIdException if any of the species IDs provided are not recognized.
     */
    public void registerVaccine(String... fields) throws UnrecognizedEntryException, DuplicatedVaccineIdException,
        UnknownSpeciesIdException{
        if (!fields[0].equals("VACINA"))
            throw new UnrecognizedEntryException(fields[0]);
        
        Map<String, Species> species = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        if (fields.length > 3) {
            String[] speciesIds = fields[3].split(",");
            for (String id : speciesIds) {
                species.put(id, getSpecies(id));
            }
        }
        Vaccine vaccine = new Vaccine(fields[1], fields[2], species);
        addVaccine(fields[1], vaccine);
        changed();
    }

    /**
     * Registers a tree using the provided fields.
     * 
     * <p><b>Fields format:</b></p>
     * <ul>
     *   <li> 0 - type (should be "ÁRVORE") </li>
     *   <li> 1 - treeId </li>
     *   <li> 2 - treeName </li>
     *   <li> 3 - age (in years) </li>
     *   <li> 4 - cleaning difficulty </li>
     *   <li> 5 - treeType (should be "PERENE" or "CADUCA") </li>
     * </ul>
     * 
     * <p>The type must be "ÁRVORE", and the treeType must be either "PERENE" or "CADUCA".
     * If these conditions are not met, an exception will be thrown.</p>
     * 
     * @param fields an array of strings containing the tree's details
     * @return the registered tree
     * @throws UnrecognizedEntryException if the type provided is not "ÁRVORE" or if the treeType is not "PERENE" or "CADUCA".
     * @throws DuplicatedTreeIdException if a tree with the same ID is already registered.
     */
    public Tree registerTree(String... fields) throws UnrecognizedEntryException, DuplicatedTreeIdException {
        Tree tree  = switch (fields[5]) {
            case "PERENE" -> {
                Tree newTree = new EvergreenTree(fields[1], fields[2], Integer.parseInt(fields[3]), Integer.parseInt(fields[4]));
                if (_trees.size() > 1) {newTree.setState(_rootEvergreenTree.getTreeState().createForNewTree(newTree));}
                else {newTree.setState(new EvergreenTreeStateSpring(newTree));}
                yield newTree;
            }
            case "CADUCA" -> {
                Tree newTree = new DeciduousTree(fields[1], fields[2], Integer.parseInt(fields[3]), Integer.parseInt(fields[4]));
                if (_trees.size() > 1) {newTree.setState(_rootDeciduousTree.getTreeState().createForNewTree(newTree));}
                else {newTree.setState(new DeciduousTreeStateSpring(newTree));}
                yield newTree;
            }
            default -> throw new UnrecognizedEntryException(fields[5]);
        };
        addTree(fields[1], tree);
        changed();
        return tree;
    }
    
    /**
     * Adds a species to the hotel.
     * 
     * @param key the unique identifier for the species being added.
     * @param species the species to be added.
     * @throws DuplicatedSpeciesIdException if a species with the same ID already exists.
     * @throws DuplicatedSpeciesNameException if a species with the same name already exists.
     */
    public void addSpecies(String key, Species species) throws DuplicatedSpeciesIdException, DuplicatedSpeciesNameException {
        assertNewSpecies(key, species.getName());
        _species.put(key, species);
        changed();
    }

    /** 
     * Checks if the species with the specified key/name is unique before adding.
     * 
     * @param key the unique identifier for the species being checked.
     * @param name the name of the species being checked.
     * @throws DuplicatedSpeciesIdException if a species with the same ID already exists.
     * @throws DuplicatedSpeciesNameException if a species with the same name already exists.
     */
    public void assertNewSpecies(String key, String name) throws DuplicatedSpeciesIdException, DuplicatedSpeciesNameException {
        if (_species.containsKey(key))
            throw new DuplicatedSpeciesIdException(key);
        for (Species species : _species.values()) {
            if (name.equals(species.getName()))
                throw new DuplicatedSpeciesNameException(name);
        }
    }

    /**
     * Adds a habitat to the hotel.
     * 
     * @param key the unique identifier for the habitat being added.
     * @param habitat the habitat to be added.
     * @throws DuplicatedHabitatIdException if a habitat with the same ID already exists.
     */
    public void addHabitat(String key, Habitat habitat) throws DuplicatedHabitatIdException {
        assertNewHabitat(key);
        _habitats.put(key, habitat);
        changed();
    }

    /** 
     * Checks if the habitat with the specified key is unique before adding.
     * 
     * @param key the unique identifier for the habitat being checked.
     * @throws DuplicatedHabitatIdException if a habitat with the same ID already exists.
     */
    public void assertNewHabitat(String key) throws DuplicatedHabitatIdException {
        if (_habitats.containsKey(key))
            throw new DuplicatedHabitatIdException(key);
    }

    /**
     * Adds an animal to the hotel.
     * 
     * @param key the unique identifier for the animal being added.
     * @param animal the animal to be added.
     * @throws DuplicatedAnimalIdException if an animal with the same ID already exists.
     */
    public void addAnimal(String key, Animal animal) throws DuplicatedAnimalIdException {
        assertNewAnimal(key);
        _animals.put(key, animal);
        changed();
    }

    /** 
     * Checks if the animal with the specified key is unique before adding.
     * 
     * @param key the unique identifier for the animal being checked.
     * @throws DuplicatedAnimalIdException if an animal with the same ID already exists.
     */
    public void assertNewAnimal(String key) throws DuplicatedAnimalIdException {
        if (_animals.containsKey(key))
            throw new DuplicatedAnimalIdException(key);
    }

    /**
     * Adds a new employee to the ho_speciesKeytel.
     * 
     * @param key the unique identifier for the employee being added.
     * @param employee the employee to be added.
     * @throws DuplicatedEmployeeIdException if an employee with the same ID already exists.
     */
    public void addEmployee(String key, Employee employee) throws DuplicatedEmployeeIdException {
        assertNewEmployee(key);
        _employees.put(key, employee);
        changed();
    }

    /** 
     * Checks if the employee with the specified key is unique before adding.
     * 
     * @param key the unique identifier for the employee being checked.
     * @throws DuplicatedEmployeeIdException if an employee with the same ID already exists.
     */
    public void assertNewEmployee(String key) throws DuplicatedEmployeeIdException {
        if (_employees.containsKey(key))
            throw new DuplicatedEmployeeIdException(key);
    }

    /**
     * Adds a new vaccine to the hotel.
     * 
     * @param key the unique identifier for the vaccine being added.
     * @param vaccine the vaccine to be added.
     * @throws DuplicatedVaccineIdException if a vaccine with the same ID already exists.
     */
    public void addVaccine(String key, Vaccine vaccine) throws DuplicatedVaccineIdException {
        assertNewVaccine(key);
        _vaccines.put(key, vaccine);
        changed();
    }

    /** 
     * Checks if the vaccine with the specified key is unique before adding.
     * 
     * @param key the unique identifier for the vaccine being checked.
     * @throws DuplicatedVaccineIdException if a vaccine with the same ID already exists.
     */
    public void assertNewVaccine(String key) throws DuplicatedVaccineIdException {
        if (_vaccines.containsKey(key))
            throw new DuplicatedVaccineIdException(key);
    }

    /**
     * Adds a new tree to the hotel.
     * 
     * @param key the unique identifier for the tree being added.
     * @param tree the tree to be added.
     * @throws DuplicatedTreeIdException if a tree with the same ID already exists.
     */
    public void addTree(String key, Tree tree) throws DuplicatedTreeIdException {
        assertNewTree(key);
        _trees.put(key, tree);
        changed();
    }

    /** 
     * Checks if the tree with the specified key is unique before adding.
     * 
     * @param key the unique identifier for the tree being checked.
     * @throws DuplicatedTreeIdException if a tree with the same ID already exists.
     */
    public void assertNewTree(String key) throws DuplicatedTreeIdException {
        if (_trees.containsKey(key))
            throw new DuplicatedTreeIdException(key);
    }

    public void addVaccination(Vaccination vaccination) {
        vaccination.getVeterinarian().addVaccination(vaccination);
        vaccination.getVaccine().addVaccination(vaccination);
        vaccination.getAnimal().addVaccination(vaccination);
        _vaccinations.add(vaccination);
    }

    public void addWrongVaccination(Vaccination wrongVaccination) {
        _wrongVaccinations.add(wrongVaccination);
    }

    /**
     * Retrieves all habitats in the hotel as an unmodifiable collection(changes cannot be made).
     * 
     * @return an unmodifiable collection of all habitats.
     */
    public Collection<Habitat> allHabitats() {
        Collection<Habitat> habitatCollection = Collections.unmodifiableCollection(_habitats.values());
        return habitatCollection;
    }

    /**
     * Retrieves all animals in the hotel as an unmodifiable collection(changes cannot be made).
     * 
     * @return an unmodifiable collection of all animals.
     */
    public Collection<Animal> allAnimals() {
        Collection<Animal> animalCollection = Collections.unmodifiableCollection(_animals.values());
        return animalCollection;
    }

    /**
     * Retrieves all employees in the hotel as an unmodifiable collection(changes cannot be made).
     * 
     * @return an unmodifiable collection of all employees.
     */
    public Collection<Employee> allEmployees() {
        Collection<Employee> employeeCollection = Collections.unmodifiableCollection(_employees.values());
        return employeeCollection;
    }

    /**
     * Retrieves all vaccines in the hotel as an unmodifiable collection(changes cannot be made).
     * 
     * @return an unmodifiable collection of all vaccines.
     */
    public Collection<Vaccine> allVaccines() {
        Collection<Vaccine> vaccineCollection = Collections.unmodifiableCollection(_vaccines.values());
        return vaccineCollection;
    }

    /**
     * Retrieves all the trees from a specified habitat as an unmodifiable collection(changes cannot be made).
     * 
     * @param habitatId the unique identifier for the habitat from which trees are to be retrieved.
     * @return an unmodifiable collection of all trees in the specified habitat.
     * @throws UnknownHabitatIdException if the habitat ID provided is not recognized.
     */

    public Collection<Tree> allTreesInHabitat(String habitatId) throws UnknownHabitatIdException {
        Habitat habitat = getHabitat(habitatId);
        return habitat.allTrees();
    }

    /**
     * Retrieves all vaccinations that happen in the hotel as an unmodifiable collection(changes cannot be made).
     * 
     * @return an unmodifiable collection of all vaccinations.
     */
    public Collection<Vaccination> allVaccinations() {
        return Collections.unmodifiableCollection(_vaccinations);
    }

    /**
     * Changes the area of a given habitat.
     * 
     * @param habitatId the unique identifier for the habitat whose area is to be changed.
     * @param area the new area value for the habitat.
     * @throws UnknownHabitatIdException if the habitat ID provided is not recognized.
     */
    public void changeHabitatArea(String habitatId, int area) throws UnknownHabitatIdException {
        Habitat habitat = getHabitat(habitatId);
        habitat.changeArea(area);
        changed();
    }

    /**
     * Adds a tree to a specified habitat.
     * 
     * <p>This method registers a new tree with the provided details and adds it to the specified habitat.</p>
     * 
     * @param habitatId The unique identifier of the habitat to which the tree will be added.
     * @param treeId The unique identifier of the tree being added.
     * @param treeName The name of the tree being added.
     * @param treeAge The age of the tree being added, typically represented as a string (e.g., "5 years").
     * @param treeDifficulty The difficulty level associated with maintaining the tree, represented as a string.
     * @param treeType The type of the tree (e.g., "deciduous", "evergreen") being added.
     * @return The newly created {@code Tree} object that has been added to the habitat.
     * @throws DuplicatedTreeIdException if a tree with the same ID already exists in the habitat.
     * @throws UnknownHabitatIdException if the habitat ID provided does not correspond to any existing habitat.
     */
    public Tree addTreeToHabitat(String habitatId, String treeId, String treeName, String treeAge, String treeDifficulty, String treeType)
        throws DuplicatedTreeIdException, UnknownHabitatIdException {
        try {
            Tree tree = registerTree("ÁRVORE", treeId, treeName, treeAge, treeDifficulty, treeType);
            Habitat habitat = getHabitat(habitatId);
            habitat.addTree(tree);
            changed();
            return tree;
        } catch (UnrecognizedEntryException e) {
            // Not suppose to ever happen
            return null;
        }
    }

    /**
     * Transfers an animal to a new habitat.
     * 
     * @param animalId the unique identifier for the animal to be transferred.
     * @param habitatId the unique identifier for the habitat to which the animal will be transferred to.
     * 
     * @throws UnknownAnimalIdException  if the animal ID provided does not correspond to any existing animal.
     * @throws UnknownHabitatIdException if the habitat ID provided does not correspond to any existing habitat.
     */
    public void transferToHabitat(String animalId, String habitatId) throws UnknownAnimalIdException,
        UnknownHabitatIdException {
        Animal animal = getAnimal(animalId);
        Habitat habitat = getHabitat(habitatId);
        animal.changeHabitat(habitat);
        changed();
    }

    /**
     * Retrieves all the animals in an habitat.
     * 
     * @param habitatId the unique identifier for the habitat to get the list of animals.
     * @return the {@code Collection} of animals in the habitat.
     * @throws UnknownHabitatIdException if the habitat ID provided does not correspond to any existing habitat.
     */
    public Collection<Animal> allAnimalsInHabitat(String habitatId) throws UnknownHabitatIdException{
        Habitat habitat = getHabitat(habitatId);
        return habitat.allAnimals();
    }

    /**
     * Calculates the satisfaction level of an animal based on its ID.
     *
     * @param animalId the ID of the animal whose satisfaction level is to be calculated
     * @return the satisfaction level of the animal, rounded to the nearest integer
     * @throws UnknownAnimalIdException if the animal ID is not found
     */
    public int animalSatisfaction(String animalId) throws UnknownAnimalIdException {
        Animal animal = getAnimal(animalId);
        double satisfaction = animal.satisfaction();
        return (int) Math.round(satisfaction);
    }

    /**
     * Shows the satisfaction of an {@code Employee}.
     * 
     * @param employeeId the unique identifier for the employee to calculate the satisfaction from.
     * @return the satisfaction of the employee.
     * @throws UnknownEmployeeIdException if the employee ID provided does not correspond to any existing employee.
     */
    public int employeeSatisfaction(String employeeId) throws UnknownEmployeeIdException {
        Employee employee = getEmployee(employeeId);
        double satisfaction = employee.satisfaction();
        return (int) Math.round(satisfaction);
    }

    /**
     * Shows the sum of all satisfactions: animals and employees.
     * 
     * @return the sum of all the animals' and employees' satisfactions.
     */
    public int globalSatisfaction() {
        double satisfaction = 0;
        for (Animal animal : _animals.values()) {
            satisfaction += animal.satisfaction();
        }
        for (Employee employee : _employees.values()) {
            satisfaction += employee.satisfaction();
        }
        return (int) Math.round(satisfaction);
    }

    /**
     * Changes the influence of a {@code Habitat} on a {@code Species}.
     * <p>The string representing the influence can be: POS, NEG or NEU.</p>
     *  
     * @param habitatId the unique identifier for the habitat to which the influence on a species is changed.
     * @param speciesId the unique identifier for the species that the habitat will influence.
     * @param influence the type of influence that the habitat will have on the species.
     * @throws UnknownHabitatIdException if the habitat ID provided does not correspond to any existing habitat.
     * @throws UnknownSpeciesIdException if the species ID provided does not correspond to any existing species.
     */
    public void changeInfluenceOfHabitat(String habitatId, String speciesId, String influence) throws UnknownHabitatIdException,
        UnknownSpeciesIdException {
        Habitat habitat = getHabitat(habitatId);
        Species species = getSpecies(speciesId);
        HabitatInfluence habitatInfluence = switch (influence) {
            case "POS" -> HabitatInfluence.POSITIVE;
            case "NEU" -> HabitatInfluence.NEUTRAL;
            case "NEG" -> HabitatInfluence.NEGATIVE;
            default -> null;
            };
        habitat.changeInfluenceOnSpecies(species, habitatInfluence);
        changed();
    }

    
    /**
     * Advances the season for all trees in the collection and updates the state.
     * 
     * This method iterates over all trees in the internal collection and calls their
     * advanceSeason method. After advancing the season for all trees, it marks the 
     * state as changed and returns the season value of the root evergreen tree,
     * that corresponds to the current season.
     * 
     * @return the season value after advancing the season.
     */
    public int advanceSeason() {
        for (Tree tree : _trees.values()) {
            tree.advanceSeason();
        }
        _rootDeciduousTree.advanceSeason();
        _rootEvergreenTree.advanceSeason();
        changed();
        return _rootEvergreenTree.getSeason().value();
    }

    /**
     * Adds a responsability to an {@code Employee}.
     * <p>A responsability can be an {@code Habitat} or a {@code Species}.</p>
     * 
     * @param employeeId the unique identifier for the employee to which the responsability is added.
     * @param responsabilityId the unique identifier for the habitat or species to add to the employee.
     * @throws NoSuchResponsabilityException if the responsability ID provided does not correspond to any existing species or habitat.
     * @throws UnknownEmployeeIdException if the employee ID provided does not correspond to any existing employee.
     */
    public void addResponsability(String employeeId, String responsabilityId) throws NoSuchResponsabilityException, UnknownEmployeeIdException {
        Employee employee = getEmployee(employeeId);
    
        try {
            if (employee instanceof Veterinarian veterinarian) {
                Species species = getSpecies(responsabilityId);
                veterinarian.assignSpecies(species);
            } 
            if (employee instanceof Zookeeper zookeeper) {
                Habitat habitat = getHabitat(responsabilityId);
                zookeeper.assignHabitat(habitat);
            }

        } catch (UnknownSpeciesIdException | UnknownHabitatIdException e) {
            throw new NoSuchResponsabilityException(employeeId, responsabilityId);
        }
        changed();
    }

    /**
     * Removes a responsability from an {@code Employee}.
     * <p>A responsability can be an {@code Habitat} or a {@code Species}.</p>
     * 
     * @param employeeId the unique identifier for the employee to which the responsability is removed.
     * @param responsabilityId the unique identifier for the habitat or species to remove from the employee.
     * @throws NoSuchResponsabilityException if the responsability ID provided does not correspond to any existing species or habitat.
     * @throws UnknownEmployeeIdException if the employee ID provided does not correspond to any existing employee.
     */
    public void removeResponsability(String employeeId, String responsabilityId) throws NoSuchResponsabilityException, UnknownEmployeeIdException {
        Employee employee = getEmployee(employeeId);
    
        try {
            if (employee instanceof Veterinarian veterinarian) {
                Species species = getSpecies(responsabilityId);
                veterinarian.unassignSpecies(species);
            }
            if (employee instanceof Zookeeper zookeeper) {
                Habitat habitat = getHabitat(responsabilityId);
                zookeeper.unassignHabitat(habitat);
            }

        } catch (UnknownSpeciesIdException | UnknownHabitatIdException e) {
            throw new NoSuchResponsabilityException(employeeId, responsabilityId);
        }
        changed();
    }

    
    /**
     * Retrieves all vaccinations for a specific animal identified by its ID.
     *
     * @param animalId the unique identifier of the animal
     * @return a collection of vaccinations associated with the specified animal
     * @throws UnknownAnimalIdException if the animal ID is not recognized
     */
    public Collection<Vaccination> animalVaccinations(String animalId) throws UnknownAnimalIdException {
        Animal animal = getAnimal(animalId);
        return animal.allVaccinations();
    }

    /**
     * Retrieves a collection of vaccinations administered by a veterinarian.
     *
     * @param veterinarianId The ID of the veterinarian whose vaccinations are to be retrieved.
     * @return A collection of vaccinations administered by the specified veterinarian.
     * @throws UnknownVeterinarianIdException If the veterinarian ID is not found.
     */
    public Collection<Vaccination> veterinarianVaccinations(String veterinarianId) throws UnknownVeterinarianIdException {
        try{
            Employee employee = getEmployee(veterinarianId);
            return employee.listAdministeredVaccinations();
        } catch (UnknownEmployeeIdException e) { 
            throw new UnknownVeterinarianIdException(veterinarianId); 
        }
        
    }

    
    /**
     * Returns an unmodifiable view of the collection of wrong vaccinations.
     * This collection contains vaccinations that were incorrectly administered
     * or recorded.
     *
     * @return an unmodifiable collection of wrong vaccinations
     */
    public Collection<Vaccination> wrongVaccinations() {
        return Collections.unmodifiableCollection(_wrongVaccinations);        
    }

    /**
     * Vaccinates an animal with a specified vaccine by a specified veterinarian.
     *
     * @param vaccineId The ID of the vaccine to be administered.
     * @param veterinarianId The ID of the veterinarian who will administer the vaccine.
     * @param animalId The ID of the animal to be vaccinated.
     * @throws UnknownVaccineIdException If the vaccine ID is not recognized.
     * @throws UnknownAnimalIdException If the animal ID is not recognized.
     * @throws UnknownVeterinarianIdException If the veterinarian ID is not recognized.
     * @throws UnauthorizedVeterinarianException If the veterinarian is not authorized to vaccinate the animal.
     * @throws WrongVaccineException If the vaccine is not adequate for the animal.
     */
    public void vaccinateAnimal(String vaccineId, String veterinarianId, String animalId) throws UnknownVaccineIdException,
        UnknownAnimalIdException, UnknownVeterinarianIdException, UnauthorizedVeterinarianException, WrongVaccineException {
        Vaccine vaccine = getVaccine(vaccineId);
        Animal animal = getAnimal(animalId);
        try{
            Employee employee = getEmployee(veterinarianId);
            Vaccination vaccination = employee.vaccinateAnimal(vaccine, animal);
            changed();
            addVaccination(vaccination);
            if (!vaccine.isVaccineAdequate(animal)) {
                addWrongVaccination(vaccination);
                throw new WrongVaccineException(vaccineId, animalId);
            }
        } catch (UnknownEmployeeIdException e) { 
            throw new UnknownVeterinarianIdException(veterinarianId); 
        }
    }

}
