package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.unknown.UnknownHabitatIdException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoShowAllTreesInHabitat extends Command<Hotel> {

    DoShowAllTreesInHabitat(Hotel receiver) {
        super(Label.SHOW_TREES_IN_HABITAT, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        try {
            Form form = new Form();
            _display.popup(_receiver.allTreesInHabitat(form.requestString(Prompt.habitatKey())));
        } catch (UnknownHabitatIdException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        }
    }

}
