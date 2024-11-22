package travelu.travelu_backend.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import travelu.travelu_backend.model.UsersDTO;
import travelu.travelu_backend.service.UsersService;
import travelu.travelu_backend.util.WebUtils;


@Controller
@RequestMapping("/userss")
public class UsersController {

    private final UsersService usersService;

    public UsersController(final UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("userses", usersService.findAll());
        return "users/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("users") final UsersDTO usersDTO) {
        return "users/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("users") @Valid final UsersDTO usersDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "users/add";
        }
        usersService.create(usersDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("users.create.success"));
        return "redirect:/userss";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("users", usersService.get(id));
        return "users/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("users") @Valid final UsersDTO usersDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }
        usersService.update(id, usersDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("users.update.success"));
        return "redirect:/userss";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        usersService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("users.delete.success"));
        return "redirect:/userss";
    }

}
