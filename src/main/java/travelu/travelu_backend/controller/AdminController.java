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
import travelu.travelu_backend.domain.Armada;
import travelu.travelu_backend.domain.Csticket;
import travelu.travelu_backend.domain.Jadwal;
import travelu.travelu_backend.model.AdminDTO;
import travelu.travelu_backend.repos.ArmadaRepository;
import travelu.travelu_backend.repos.CsticketRepository;
import travelu.travelu_backend.repos.JadwalRepository;
import travelu.travelu_backend.service.AdminService;
import travelu.travelu_backend.util.CustomCollectors;
import travelu.travelu_backend.util.WebUtils;


@Controller
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;
    private final ArmadaRepository armadaRepository;
    private final CsticketRepository csticketRepository;
    private final JadwalRepository jadwalRepository;

    public AdminController(final AdminService adminService, final ArmadaRepository armadaRepository,
            final CsticketRepository csticketRepository, final JadwalRepository jadwalRepository) {
        this.adminService = adminService;
        this.armadaRepository = armadaRepository;
        this.csticketRepository = csticketRepository;
        this.jadwalRepository = jadwalRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("listArmadaValues", armadaRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Armada::getId, Armada::getPlatNom)));
        model.addAttribute("csTicketValues", csticketRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Csticket::getId, Csticket::getId)));
        model.addAttribute("tanggalJadwalValues", jadwalRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Jadwal::getId, Jadwal::getHari)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("admins", adminService.findAll());
        return "admin/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("admin") final AdminDTO adminDTO) {
        return "admin/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("admin") @Valid final AdminDTO adminDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/add";
        }
        adminService.create(adminDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("admin.create.success"));
        return "redirect:/admins";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("admin", adminService.get(id));
        return "admin/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("admin") @Valid final AdminDTO adminDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
        adminService.update(id, adminDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("admin.update.success"));
        return "redirect:/admins";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        adminService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("admin.delete.success"));
        return "redirect:/admins";
    }

}
