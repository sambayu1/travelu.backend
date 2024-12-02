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
import travelu.travelu_backend.domain.DestinasiItinerari;
import travelu.travelu_backend.domain.Jadwal;
import travelu.travelu_backend.model.ItinerariDTO;
import travelu.travelu_backend.repos.DestinasiItinerariRepository;
import travelu.travelu_backend.repos.JadwalRepository;
import travelu.travelu_backend.service.ItinerariService;
import travelu.travelu_backend.util.CustomCollectors;
import travelu.travelu_backend.util.WebUtils;


@Controller
@RequestMapping("/itineraris")
public class ItinerariController {

    private final ItinerariService itinerariService;
    private final DestinasiItinerariRepository destinasiItinerariRepository;
    private final JadwalRepository jadwalRepository;

    public ItinerariController(final ItinerariService itinerariService,
            final DestinasiItinerariRepository destinasiItinerariRepository,
            final JadwalRepository jadwalRepository) {
        this.itinerariService = itinerariService;
        this.destinasiItinerariRepository = destinasiItinerariRepository;
        this.jadwalRepository = jadwalRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("listDestinasiValues", destinasiItinerariRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(DestinasiItinerari::getId, DestinasiItinerari::getName)));
        model.addAttribute("listJadwalValues", jadwalRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Jadwal::getId, Jadwal::getAsal)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("itineraris", itinerariService.findAll());
        return "itinerari/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("itinerari") final ItinerariDTO itinerariDTO) {
        return "itinerari/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("itinerari") @Valid final ItinerariDTO itinerariDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "itinerari/add";
        }
        itinerariService.create(itinerariDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("itinerari.create.success"));
        return "redirect:/itineraris";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("itinerari", itinerariService.get(id));
        return "itinerari/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("itinerari") @Valid final ItinerariDTO itinerariDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "itinerari/edit";
        }
        itinerariService.update(id, itinerariDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("itinerari.update.success"));
        return "redirect:/itineraris";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        itinerariService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("itinerari.delete.success"));
        return "redirect:/itineraris";
    }

}
