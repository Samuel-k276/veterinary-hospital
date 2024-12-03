package hva.app.habitat;

import hva.Hotel;

import hva.app.exceptions.DuplicateHabitatKeyException;
import hva.exceptions.UnrecognizedEntryException;
import hva.exceptions.duplicated.DuplicatedHabitatIdException;
import hva.exceptions.duplicated.DuplicatedTreeIdException;
import hva.exceptions.unknown.UnknownTreeIdException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRegisterHabitat extends Command<Hotel> {

    DoRegisterHabitat(Hotel receiver) {
        super(Label.REGISTER_HABITAT, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        try {          
            Form form = new Form();
            String habitatId = form.requestString(Prompt.habitatKey());
            String habitatName = form.requestString(Prompt.habitatName());
            int area = 0;
            do {
                area = form.requestInteger(Prompt.habitatArea());
            } while (!(area > 0));

            _receiver.registerHabitat("HABITAT", habitatId, habitatName, Integer.toString(area));

        } catch (DuplicatedHabitatIdException e) {
            throw new DuplicateHabitatKeyException(e.getKey());
        } catch (UnrecognizedEntryException | UnknownTreeIdException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
