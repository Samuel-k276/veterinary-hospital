package hva.app.search;

import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.unknown.UnknownHabitatIdException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowAnimalsInHabitat extends Command<Hotel> {

    DoShowAnimalsInHabitat(Hotel receiver) {
        super(Label.ANIMALS_IN_HABITAT, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        try {
            _display.popup(_receiver.allAnimalsInHabitat(Form.requestString(hva.app.habitat.Prompt.habitatKey())));
        } catch (UnknownHabitatIdException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        }
    }

}
