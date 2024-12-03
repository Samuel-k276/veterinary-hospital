package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.NoResponsibilityException;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.exceptions.NoSuchResponsabilityException;
import hva.exceptions.unknown.UnknownEmployeeIdException;
import hva.exceptions.unknown.UnknownHabitatIdException;
import hva.exceptions.unknown.UnknownSpeciesIdException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoRemoveResponsibility extends Command<Hotel> {

    DoRemoveResponsibility(Hotel receiver) {
        super(Label.REMOVE_RESPONSABILITY, receiver);
        addStringField("employeeId", Prompt.employeeKey());
        addStringField("responsabilityId", Prompt.responsibilityKey());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            _receiver.removeResponsability(stringField("employeeId"), stringField("responsabilityId"));
        } catch (NoSuchResponsabilityException e) {
            throw new NoResponsibilityException(e.getEmployeeKey(), e.getResponsabilityKey());
        } catch (UnknownEmployeeIdException e) {
            throw new UnknownEmployeeKeyException(e.getKey());
        } 
    }

}
