package travelu.travelu_backend.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import travelu.travelu_backend.domain.Jadwal;
import travelu.travelu_backend.model.PelangganDTO;
import travelu.travelu_backend.repos.JadwalRepository;
import travelu.travelu_backend.service.PelangganService;
import travelu.travelu_backend.util.CustomCollectors;
import travelu.travelu_backend.util.ReferencedWarning;
import travelu.travelu_backend.util.WebUtils;


@Controller
@RequestMapping("/pelanggans")
public class PelangganController {

    private final PelangganService pelangganService;
    private final JadwalRepository jadwalRepository;

    public PelangganController(final PelangganService pelangganService,
            final JadwalRepository jadwalRepository) {
        this.pelangganService = pelangganService;
        this.jadwalRepository = jadwalRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("tanggalJadwalValues", jadwalRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Jadwal::getId, Jadwal::getHari)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("pelanggans", pelangganService.findAll());
        return "pelanggan/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("pelanggan") final PelangganDTO pelangganDTO) {
        return "pelanggan/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("pelanggan") @Valid final PelangganDTO pelangganDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "pelanggan/add";
        }
        pelangganService.create(pelangganDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("pelanggan.create.success"));
        return "redirect:/pelanggans";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("pelanggan", pelangganService.get(id));
        return "pelanggan/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("pelanggan") @Valid final PelangganDTO pelangganDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "pelanggan/edit";
        }
        pelangganService.update(id, pelangganDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("pelanggan.update.success"));
        return "redirect:/pelanggans";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = pelangganService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            pelangganService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("pelanggan.delete.success"));
        }
        return "redirect:/pelanggans";
    }

}
