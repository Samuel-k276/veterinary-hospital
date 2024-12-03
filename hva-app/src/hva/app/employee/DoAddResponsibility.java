package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.NoResponsibilityException;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.exceptions.NoSuchResponsabilityException;
import hva.exceptions.unknown.UnknownEmployeeIdException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoAddResponsibility extends Command<Hotel> {

    DoAddResponsibility(Hotel receiver) {
        super(Label.ADD_RESPONSABILITY, receiver);
        addStringField("employeeId", Prompt.employeeKey());
        addStringField("responsabilityId", Prompt.responsibilityKey());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            _receiver.addResponsability(stringField("employeeId"), stringField("responsabilityId"));
        } catch (NoSuchResponsabilityException e) {
            throw new NoResponsibilityException(e.getEmployeeKey(), e.getResponsabilityKey());
        } catch (UnknownEmployeeIdException e) {
            throw new UnknownEmployeeKeyException(e.getKey());
        } 
    }

}
