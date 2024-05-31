package cg.codegym.minitest.springboot2.Controller;

import cg.codegym.minitest.springboot2.Exception.DuplicateProductCodeException;
import cg.codegym.minitest.springboot2.Model.Computer;
import cg.codegym.minitest.springboot2.Model.Type;
import cg.codegym.minitest.springboot2.Service.iml.ComputerService;
import cg.codegym.minitest.springboot2.Service.iml.IComputerService;
import cg.codegym.minitest.springboot2.Service.iml.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/computers")
public class ComputerController {

    @Autowired
    private IComputerService computerService;

    @Autowired
    private ITypeService typeService;

    public ComputerController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @ModelAttribute("types")
    public Iterable<Type> listTypes() {
        return typeService.findAll();
    }

    /*
    @GetMapping
    public ModelAndView listComputer(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page,3);
        Page<Computer> customers = computerService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/computer/list");
        modelAndView.addObject("computers", customers);
        return modelAndView;
    }

     */
    @GetMapping
    public ModelAndView listComputer(@RequestParam(defaultValue = "0") int page) {
        ModelAndView modelAndView = new ModelAndView("/computer/list");
        try {
            Pageable pageable = PageRequest.of(page, 3);
            Page<Computer> customers = computerService.findAll(pageable);
            modelAndView.addObject("computers", customers);
        } catch (Exception e) {
            modelAndView.addObject("error", "An error occurred while retrieving the list of computers.");
            // You can also log the error if needed
            // logger.error("Error occurred while listing computers", e);
        }
        return modelAndView;
    }
    @GetMapping("/{id}")
    public ModelAndView showInformation(@PathVariable Long id){
        try {
            ModelAndView modelAndView = new ModelAndView("/computer/info");
            Computer computer = computerService.findOne(id);
            return modelAndView;
        }
        catch(Exception e){
            return new ModelAndView("redirect:/computers");
        }
    }
// add aop 2:50 PM 24 - 05 - 2024


    @GetMapping("/search")
    public ModelAndView listComputersSearch(@RequestParam("search") Optional<String> search, Pageable pageable){
        Page<Computer> customers;
        if(search.isPresent()){
            customers = computerService.findAllByNameContaining(pageable, search.get());
        } else {
            customers = computerService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/computer/list");
        modelAndView.addObject("computers", customers);
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/computer/create");
        modelAndView.addObject("computer", new Computer());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(@Valid @ModelAttribute("computer") Computer computer,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/computer/create");
            modelAndView.addObject("computer", computer);
            return modelAndView;
        }

        computerService.save(computer);
        redirectAttributes.addFlashAttribute("message", "Create new computer successfully");
        return new ModelAndView("redirect:/computers");
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Computer> computer = computerService.findById(id);
        if (computer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/computer/update");
            modelAndView.addObject("computer", computer.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error_404");
        }
    }

    @PostMapping("/update/{id}")
    public ModelAndView update(@Valid @ModelAttribute("computer") Computer computer,
                               BindingResult bindingResult,
                               RedirectAttributes redirect) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/computer/update");
            modelAndView.addObject("computer", computer);
            return modelAndView;
        }

        computerService.save(computer);
        redirect.addFlashAttribute("message", "Update computer successfully");
        return new ModelAndView("redirect:/computers");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirect) {
        computerService.remove(id);
        redirect.addFlashAttribute("message", "Delete computer successfully");
        return "redirect:/computers";
    }
    @ExceptionHandler(DuplicateProductCodeException.class)
    public ModelAndView showInputNotAcceptable() {
        return new ModelAndView("/duplicate-code");
    }
}
