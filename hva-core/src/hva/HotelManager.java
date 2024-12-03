package hva;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import hva.exceptions.MissingFileAssociationException;
import hva.exceptions.ImportFileException;
import hva.exceptions.UnavailableFileException;


/**
 * The {@code HotelManager} class represents the main controller of the hotel management system.
 * It is responsible for managing the hotel's state, including saving and loading the hotel's data
 * from disk, advancing the season, and calculating the global satisfaction score.
 * 
 * <p>
 * The hotel manager is responsible for managing the hotel's state, including saving and loading the hotel's data
 * from disk, advancing the season, and calculating the global satisfaction score.
 * </p>
 * 
 * <p><b>Attributes:</b></p>
 * <ul>
 *   <li>_filename: The name of the file associated with the current hotel.</li>
 *   <li>_hotel: The current hotel instance.</li>
 * </ul>
 */
public class HotelManager {

    /** The name of the file associated with the current hotel. */
    private String _filename = "";

    /** This is the current hotel. */
    private Hotel _hotel = new Hotel();

    
    /**
     * Saves the serialized application's state into the file associated to the current hotel.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current hotel does not have a file.
     * @throws IOException if there is some error while serializing the state of the hotel to disk.
     */
    
    public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
        if (!changed()) return;

        if(_filename == null || _filename.equals(""))
            throw new MissingFileAssociationException();
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)))) {
            oos.writeObject(_hotel);
            _hotel.setChanged(false);
        } 
    }

    /**
     * Saves the serialized application's state into the file associated to the current hotel.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current hotel does not have a file.
     * @throws IOException if there is some error while serializing the state of the hotel to disk.
     */
    public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
        _filename = filename;
        save();
    }

    /**
     * Loads the hotel data from the specified file.
     * 
     * @param filename name of the file containing the serialized application's state
     *        to load.
     * @throws UnavailableFileException if the specified file does not exist or there is
     *         an error while processing this file.
     */
    public void load(String filename) throws UnavailableFileException {
        _filename = filename;
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(_filename)))) {
            _hotel = (Hotel) ois.readObject();
            _hotel.setChanged(false);
        } catch (IOException | ClassNotFoundException e) {
            throw new UnavailableFileException(_filename);
        } 
    }

    /**
     * Read text input file.
     *
     * @param filename name of the text input file
     * @throws ImportFileException
     */
    public void importFile(String filename) throws ImportFileException {
        _hotel.importFile(filename);
    }

    /**
     * @return filename
     */
    public String getFilename() {
        return _filename;
    }

    /**
     * @param filename
     */
    public void setFilename(String filename) {
        _filename = filename;
    }

    /**
     * @return changed?
     */
    public boolean changed() {
        return _hotel.hasChanged();
    }

    /**
     * @return hotel
     */
    public Hotel getHotel() {
        return _hotel;
    }

    /**
     * Reset the hotel.
     */
    public void reset() {
        _hotel = new Hotel();
        _filename = null;
    }

    /**
     * Advances the season in the hotel management system.
     * 
     * @return an integer representing the new season state after advancement.
     */
    public int advanceSeason() {
        return _hotel.advanceSeason();
    }

    /**
     * Retrieves the global satisfaction score of the hotel.
     *
     * @return the global satisfaction score as an integer.
     */
    public int showGlobalSatisfaction() {
        return _hotel.globalSatisfaction();
    }
}
