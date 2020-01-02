package pl.zablocki.petvet.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.zablocki.petvet.entity.Pet;
import pl.zablocki.petvet.entity.PetType;
import pl.zablocki.petvet.services.PetService;

import java.security.Principal;
import java.util.List;

@RequestMapping("/pets")
@Controller
public class PetsController {

    private final PetService petService;

    private static final Logger log = LoggerFactory.getLogger(PetsController.class);


    public PetsController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping()
    public String showPetsList(Model model, Principal principal) {
        model.addAttribute("myPets", petService.getUserPets(principal.getName()));
        return "pets";
    }

    @GetMapping("add")
    public String showPetForm(Model model) {
        model.addAttribute(new Pet());
        return "petForm";
    }

    @PostMapping("add")
    public String addPet(@ModelAttribute("pet") Pet pet, BindingResult errors, Principal principal){
        if(errors.hasErrors()){
            return "petForm";
        }
        petService.addPet(pet, principal.getName());
        return "redirect:/pets";
    }


    @ModelAttribute(name = "petTypes")
    public List<PetType> getPetTypesAndAddToModel() {
        return petService.getPetTypes();
    }
}
