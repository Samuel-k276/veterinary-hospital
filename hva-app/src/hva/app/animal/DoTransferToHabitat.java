package hva.app.animal;

import hva.Hotel;
import hva.exceptions.unknown.UnknownAnimalIdException;
import hva.exceptions.unknown.UnknownHabitatIdException;

import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoTransferToHabitat extends Command<Hotel> {

    DoTransferToHabitat(Hotel hotel) {
        super(Label.TRANSFER_ANIMAL_TO_HABITAT, hotel);
        addStringField("animalId", Prompt.animalKey());
        addStringField("habitatId", hva.app.habitat.Prompt.habitatKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
        _receiver.transferToHabitat(stringField("animalId"), stringField("habitatId"));
        } catch (UnknownAnimalIdException e) {
            throw new UnknownAnimalKeyException(e.getKey());
        } catch (UnknownHabitatIdException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        }
    }
}