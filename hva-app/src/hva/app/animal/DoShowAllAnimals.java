package hva.app.animal;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;

/**
 * Command for listing all animals.
 */
class DoShowAllAnimals extends Command<Hotel> {

    /** @param receiver */
    DoShowAllAnimals(Hotel receiver) {
        super(Label.SHOW_ALL_ANIMALS, receiver);
    }

    @Override
    protected final void execute() {
        _display.popup(_receiver.allAnimals());
    }

}
