package hva.app.employee;

import java.util.Arrays;
import java.util.List;

import hva.Hotel;
import hva.exceptions.UnrecognizedEntryException;
import hva.exceptions.duplicated.DuplicatedEmployeeIdException;
import hva.exceptions.unknown.UnknownHabitatIdException;
import hva.exceptions.unknown.UnknownSpeciesIdException;
import hva.app.exceptions.DuplicateEmployeeKeyException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoRegisterEmployee extends Command<Hotel> {

    DoRegisterEmployee(Hotel receiver) {
        super(Label.REGISTER_EMPLOYEE, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        try {
            Form form = new Form();
            String employeeId = form.requestString(Prompt.employeeKey());
            String employeeName = form.requestString(Prompt.employeeName());
            String[] typeOption = {"TRT", "VET"};
            String employeeType = form.requestOption(Prompt.employeeType(), typeOption);
            _receiver.registerEmployee(employeeType, employeeId, employeeName);

        } catch (UnrecognizedEntryException | UnknownHabitatIdException | UnknownSpeciesIdException e) {
            //Not suppose to ever happen
            e.printStackTrace();
        } catch (DuplicatedEmployeeIdException e) {
            throw new DuplicateEmployeeKeyException(e.getKey());
        }
    }

}
