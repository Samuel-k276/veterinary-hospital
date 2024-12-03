package hva.app.vaccine;

import hva.Hotel;
import hva.exceptions.UnrecognizedEntryException;
import hva.exceptions.duplicated.DuplicatedVaccineIdException;
import hva.exceptions.unknown.UnknownSpeciesIdException;

import hva.app.exceptions.DuplicateVaccineKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRegisterVaccine extends Command<Hotel> {

    DoRegisterVaccine(Hotel receiver) {
        super(Label.REGISTER_VACCINE, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        try {          
            Form form = new Form();
            String vaccineId = form.requestString(Prompt.vaccineKey());
            String vaccineName = form.requestString(Prompt.vaccineName());
            String vaccineSpecies = form.requestString(Prompt.listOfSpeciesKeys());
            _receiver.registerVaccine("VACINA", vaccineId, vaccineName, vaccineSpecies);
            
        } catch (UnrecognizedEntryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DuplicatedVaccineIdException e) {
            throw new DuplicateVaccineKeyException(e.getKey());
        } catch (UnknownSpeciesIdException e) {
            throw new UnknownSpeciesKeyException(e.getKey());
        }
    }

}
