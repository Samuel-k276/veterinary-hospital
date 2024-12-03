package hva.app.search;

import hva.Hotel;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import hva.exceptions.unknown.UnknownVeterinarianIdException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowMedicalActsByVeterinarian extends Command<Hotel> {

    DoShowMedicalActsByVeterinarian(Hotel receiver) {
        super(Label.MEDICAL_ACTS_BY_VET, receiver);
	    addStringField("employeeId", hva.app.employee.Prompt.employeeKey());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            _display.popup(_receiver.veterinarianVaccinations(stringField("employeeId")));
        } catch (UnknownVeterinarianIdException e) {
            throw new UnknownVeterinarianKeyException(e.getKey());
        }
    }

}
