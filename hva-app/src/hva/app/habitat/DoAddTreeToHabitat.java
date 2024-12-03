package hva.app.habitat;

import java.util.Arrays;
import java.util.List;

import hva.Hotel;
import hva.app.exceptions.DuplicateTreeKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.duplicated.DuplicatedTreeIdException;
import hva.exceptions.unknown.UnknownHabitatIdException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoAddTreeToHabitat extends Command<Hotel> {

    DoAddTreeToHabitat(Hotel receiver) {
        super(Label.ADD_TREE_TO_HABITAT, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        try {
            Form form = new Form();
            String habitatId = form.requestString(Prompt.habitatKey());
            String treeId = form.requestString(Prompt.treeKey());
            String treeName = form.requestString(Prompt.treeName());
            int treeAge = 0;
            int treeDifficulty = 0;
            do {
                treeAge = form.requestInteger(Prompt.treeAge());
            } while (!(treeAge >= 0));
            do {
                treeDifficulty = form.requestInteger(Prompt.treeDifficulty());
            } while (!(treeDifficulty > 0));
            String[] treeOption = {"CADUCA", "PERENE"};
            String treeType = form.requestOption(Prompt.treeType(), treeOption);
            _display.popup(_receiver.addTreeToHabitat(habitatId, treeId, treeName, Integer.toString(treeAge), Integer.toString(treeDifficulty), treeType));  
        } catch (DuplicatedTreeIdException e) {
            throw new DuplicateTreeKeyException(e.getKey());
        } catch (UnknownHabitatIdException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        }
    }

}
