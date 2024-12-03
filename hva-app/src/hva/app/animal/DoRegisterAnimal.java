package hva.app.animal;

import hva.Hotel;
import hva.exceptions.UnrecognizedEntryException;
import hva.exceptions.unknown.UnknownSpeciesIdException;
import hva.exceptions.unknown.UnknownHabitatIdException;
import hva.exceptions.duplicated.DuplicatedAnimalIdException;
import hva.exceptions.duplicated.DuplicatedSpeciesIdException;
import hva.exceptions.duplicated.DuplicatedSpeciesNameException;
import hva.app.exceptions.DuplicateAnimalKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRegisterAnimal extends Command<Hotel> {

    DoRegisterAnimal(Hotel receiver) {
        super(Label.REGISTER_ANIMAL, receiver);
        addStringField("animalId", Prompt.animalKey());
        addStringField("animalName", Prompt.animalName());
        addStringField("speciesId", Prompt.speciesKey());
        addStringField("habitatId", hva.app.habitat.Prompt.habitatKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.registerAnimal("ANIMAL", stringField("animalId"), stringField("animalName"), stringField("speciesId"), stringField("habitatId"));
        } catch (UnrecognizedEntryException e) {
            //Not suppose to ever happen
            e.printStackTrace();
        } catch (UnknownSpeciesIdException e) {
            boolean success = false;
            do {
                try {
                    Form form = new Form();
                    String speciesName = form.requestString(Prompt.speciesName());
                    _receiver.registerSpecies("ESPÉCIE", stringField("speciesId"), speciesName);
                    success = true;
                    _receiver.registerAnimal("ANIMAL", stringField("animalId"), stringField("animalName"), stringField("speciesId"), stringField("habitatId"));
                } catch (UnrecognizedEntryException | UnknownSpeciesIdException | DuplicatedSpeciesIdException e1) {
                    //Not suppose to ever happen
                    e.printStackTrace();
                } catch (DuplicatedSpeciesNameException e1) {
                    
                } catch (UnknownHabitatIdException e1){
                    throw new UnknownHabitatKeyException(e1.getKey());
                } catch (DuplicatedAnimalIdException e1) {
                    throw new DuplicateAnimalKeyException(e1.getKey());
                }
            } while (!success);
        } catch (UnknownHabitatIdException e){
            throw new UnknownHabitatKeyException(e.getKey());
        } catch (DuplicatedAnimalIdException e) {
            throw new DuplicateAnimalKeyException(e.getKey());
        }
    }
}
