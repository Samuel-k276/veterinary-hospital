package hva.app.vaccine;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.UnknownVaccineKeyException;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import hva.app.exceptions.VeterinarianNotAuthorizedException;
import hva.exceptions.UnauthorizedVeterinarianException;
import hva.exceptions.WrongVaccineException;
import hva.exceptions.unknown.UnknownAnimalIdException;
import hva.exceptions.unknown.UnknownVaccineIdException;
import hva.exceptions.unknown.UnknownVeterinarianIdException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoVaccinateAnimal extends Command<Hotel> {

    DoVaccinateAnimal(Hotel receiver) {
        super(Label.VACCINATE_ANIMAL, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            Form form = new Form();
            String vaccineId = form.requestString(Prompt.vaccineKey());
            String veterinarianId = form.requestString(Prompt.veterinarianKey());
            String animalId = form.requestString(hva.app.animal.Prompt.animalKey());
            _receiver.vaccinateAnimal(vaccineId, veterinarianId, animalId);
        } catch(WrongVaccineException e) {
            _display.popup(Message.wrongVaccine(e.getVaccineKey(), e.getAnimalKey()));
        } catch (UnknownVaccineIdException e) {
            throw new UnknownVaccineKeyException(e.getKey());
        } catch (UnknownVeterinarianIdException e) {
            throw new UnknownVeterinarianKeyException(e.getKey());
        } catch (UnknownAnimalIdException e) {
            throw new UnknownAnimalKeyException(e.getKey());
        } catch (UnauthorizedVeterinarianException e) {
            throw new VeterinarianNotAuthorizedException(e.getVeterinarianKey(), e.getSpeciesKey());
        }
    }

}
