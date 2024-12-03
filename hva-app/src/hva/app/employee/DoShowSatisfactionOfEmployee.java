package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.exceptions.unknown.UnknownEmployeeIdException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowSatisfactionOfEmployee extends Command<Hotel> {

    DoShowSatisfactionOfEmployee(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_EMPLOYEE, receiver);
        addStringField("employeeId", Prompt.employeeKey());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            _display.popup(_receiver.employeeSatisfaction(stringField("employeeId")));
        } catch (UnknownEmployeeIdException e) {
            throw new UnknownEmployeeKeyException(e.getKey());
        }
    }

}
