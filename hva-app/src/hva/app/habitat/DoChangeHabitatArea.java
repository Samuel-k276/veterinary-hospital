package hva.app.habitat;

import hva.Hotel;

import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.unknown.UnknownHabitatIdException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoChangeHabitatArea extends Command<Hotel> {

    DoChangeHabitatArea(Hotel receiver) {
        super(Label.CHANGE_HABITAT_AREA, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        try {          
            Form form = new Form();
            String habitatId = form.requestString(Prompt.habitatKey());
            int area = 0;
            do {
                area = form.requestInteger(Prompt.habitatArea());
            } while (!(area > 0));

            _receiver.changeHabitatArea(habitatId, area);
        } catch (UnknownHabitatIdException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        } 
    }

}
