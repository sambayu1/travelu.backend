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
import travelu.travelu_backend.model.BannerImageDTO;
import travelu.travelu_backend.service.BannerImageService;
import travelu.travelu_backend.util.WebUtils;


@Controller
@RequestMapping("/bannerImages")
public class BannerImageController {

    private final BannerImageService bannerImageService;

    public BannerImageController(final BannerImageService bannerImageService) {
        this.bannerImageService = bannerImageService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("bannerImages", bannerImageService.findAll());
        return "bannerImage/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("bannerImage") final BannerImageDTO bannerImageDTO) {
        return "bannerImage/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("bannerImage") @Valid final BannerImageDTO bannerImageDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bannerImage/add";
        }
        bannerImageService.create(bannerImageDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bannerImage.create.success"));
        return "redirect:/bannerImages";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("bannerImage", bannerImageService.get(id));
        return "bannerImage/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("bannerImage") @Valid final BannerImageDTO bannerImageDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bannerImage/edit";
        }
        bannerImageService.update(id, bannerImageDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bannerImage.update.success"));
        return "redirect:/bannerImages";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        bannerImageService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("bannerImage.delete.success"));
        return "redirect:/bannerImages";
    }

}
