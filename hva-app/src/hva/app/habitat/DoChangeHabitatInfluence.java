package hva.app.habitat;

import java.util.Arrays;
import java.util.List;

import hva.Hotel;
import hva.exceptions.unknown.UnknownHabitatIdException;
import hva.exceptions.unknown.UnknownSpeciesIdException;

import hva.app.exceptions.UnknownHabitatKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoChangeHabitatInfluence extends Command<Hotel> {

    DoChangeHabitatInfluence(Hotel receiver) {
        super(Label.CHANGE_HABITAT_INFLUENCE, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        try {          
            Form form = new Form();
            String habitatId = form.requestString(Prompt.habitatKey());
            String speciesId = form.requestString(hva.app.animal.Prompt.speciesKey());
            String[] influenceOption = {"POS", "NEG", "NEU"};
            String influence = form.requestOption(Prompt.habitatInfluence(), influenceOption);
            _receiver.changeInfluenceOfHabitat(habitatId, speciesId, influence);
        } catch (UnknownHabitatIdException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        } catch (UnknownSpeciesIdException e) {
            throw new UnknownSpeciesKeyException(e.getKey());
        }
    }

}
