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
import travelu.travelu_backend.model.DiskonDTO;
import travelu.travelu_backend.service.DiskonService;
import travelu.travelu_backend.util.WebUtils;


@Controller
@RequestMapping("/diskons")
public class DiskonController {

    private final DiskonService diskonService;

    public DiskonController(final DiskonService diskonService) {
        this.diskonService = diskonService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("diskons", diskonService.findAll());
        return "diskon/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("diskon") final DiskonDTO diskonDTO) {
        return "diskon/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("diskon") @Valid final DiskonDTO diskonDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "diskon/add";
        }
        diskonService.create(diskonDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("diskon.create.success"));
        return "redirect:/diskons";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("diskon", diskonService.get(id));
        return "diskon/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("diskon") @Valid final DiskonDTO diskonDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "diskon/edit";
        }
        diskonService.update(id, diskonDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("diskon.update.success"));
        return "redirect:/diskons";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        diskonService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("diskon.delete.success"));
        return "redirect:/diskons";
    }

}
