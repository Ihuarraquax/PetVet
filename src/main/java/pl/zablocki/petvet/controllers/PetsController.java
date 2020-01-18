package pl.zablocki.petvet.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.zablocki.petvet.entity.Pet;
import pl.zablocki.petvet.entity.PetType;
import pl.zablocki.petvet.services.PetService;
import pl.zablocki.petvet.services.UploadService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequestMapping("/pets")
@Controller
public class PetsController {

    private final PetService petService;
    private final UploadService uploadService;
    private static final Logger log = LoggerFactory.getLogger(PetsController.class);

    public PetsController(PetService petService, UploadService uploadService) {
        this.petService = petService;
        this.uploadService = uploadService;
    }

    @GetMapping()
    public String showMyPetsList(Model model, Principal principal) {
        model.addAttribute("pets", petService.getOwnerPets(principal.getName()));
        return "pets";
    }

    @GetMapping("/all")
    public String showAllPetsList(Model model, Pageable pageable) {
        model.addAttribute("pets", petService.getAllPets(pageable));
        return "pets";
    }


    @PostMapping("form")
    public String savePet(@ModelAttribute("pet") Pet pet, @RequestParam("imageFile") Optional<MultipartFile> image, BindingResult errors, Principal principal) {
        if (errors.hasErrors()) {
            return "petForm";
        }
        if(!image.get().getName().equals("")){
            String imageName = uploadService.saveImage(image.get());
            pet.setImage(imageName);
        }
        petService.savePet(pet, principal.getName());
        return "redirect:/pets";
    }

    @GetMapping("form")
    public String showPetForm(Optional<Long> id, Model model) {
        model.addAttribute(
                "pet",
                id.isPresent() ? petService.getPet(id.get()).get() : new Pet());
        return "petForm";
    }

    @ModelAttribute(name = "petTypes")
    public List<PetType> getPetTypesAndAddToModel() {
        return petService.getPetTypes();
    }


}
