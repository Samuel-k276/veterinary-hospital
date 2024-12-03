package hva.app.animal;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.exceptions.unknown.UnknownAnimalIdException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowSatisfactionOfAnimal extends Command<Hotel> {

    DoShowSatisfactionOfAnimal(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_ANIMAL, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _display.popup(_receiver.animalSatisfaction(Form.requestString(Prompt.animalKey())));
        } catch (UnknownAnimalIdException e) {
            throw new UnknownAnimalKeyException(e.getKey());
        }
    }

}
