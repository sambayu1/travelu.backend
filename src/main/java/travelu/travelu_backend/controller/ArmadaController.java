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
import travelu.travelu_backend.model.ArmadaDTO;
import travelu.travelu_backend.service.ArmadaService;
import travelu.travelu_backend.util.ReferencedWarning;
import travelu.travelu_backend.util.WebUtils;


@Controller
@RequestMapping("/armadas")
public class ArmadaController {

    private final ArmadaService armadaService;

    public ArmadaController(final ArmadaService armadaService) {
        this.armadaService = armadaService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("armadas", armadaService.findAll());
        return "armada/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("armada") final ArmadaDTO armadaDTO) {
        return "armada/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("armada") @Valid final ArmadaDTO armadaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "armada/add";
        }
        armadaService.create(armadaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("armada.create.success"));
        return "redirect:/armadas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("armada", armadaService.get(id));
        return "armada/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("armada") @Valid final ArmadaDTO armadaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "armada/edit";
        }
        armadaService.update(id, armadaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("armada.update.success"));
        return "redirect:/armadas";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = armadaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            armadaService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("armada.delete.success"));
        }
        return "redirect:/armadas";
    }

}
