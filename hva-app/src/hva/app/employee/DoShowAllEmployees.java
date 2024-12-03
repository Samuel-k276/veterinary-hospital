package hva.app.employee;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for listing all employees.
 */
class DoShowAllEmployees extends Command<Hotel> {

    /** @param receiver */
    DoShowAllEmployees(Hotel receiver) {
        super(Label.SHOW_ALL_EMPLOYEES, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        _display.popup(_receiver.allEmployees());
    }

}
