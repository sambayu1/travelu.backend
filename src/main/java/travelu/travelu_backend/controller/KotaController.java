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
import travelu.travelu_backend.model.KotaDTO;
import travelu.travelu_backend.service.KotaService;
import travelu.travelu_backend.util.ReferencedWarning;
import travelu.travelu_backend.util.WebUtils;


@Controller
@RequestMapping("/kotas")
public class KotaController {

    private final KotaService kotaService;

    public KotaController(final KotaService kotaService) {
        this.kotaService = kotaService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("kotas", kotaService.findAll());
        return "kota/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("kota") final KotaDTO kotaDTO) {
        return "kota/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("kota") @Valid final KotaDTO kotaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "kota/add";
        }
        kotaService.create(kotaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("kota.create.success"));
        return "redirect:/kotas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("kota", kotaService.get(id));
        return "kota/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("kota") @Valid final KotaDTO kotaDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "kota/edit";
        }
        kotaService.update(id, kotaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("kota.update.success"));
        return "redirect:/kotas";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = kotaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            kotaService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("kota.delete.success"));
        }
        return "redirect:/kotas";
    }

}
