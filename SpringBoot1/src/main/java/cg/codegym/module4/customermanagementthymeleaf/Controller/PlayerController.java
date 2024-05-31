package cg.codegym.module4.customermanagementthymeleaf.Controller;

import cg.codegym.module4.customermanagementthymeleaf.Exception.DuplicateEmailException;
import cg.codegym.module4.customermanagementthymeleaf.Model.Player;
import cg.codegym.module4.customermanagementthymeleaf.Model.PlayerForm;
import cg.codegym.module4.customermanagementthymeleaf.Model.Positions;
import cg.codegym.module4.customermanagementthymeleaf.Service.iml.IPlayerService;
import cg.codegym.module4.customermanagementthymeleaf.Service.iml.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class PlayerController {

    @Value("${file-upload}")
    private String upload;

    @Autowired
    private IPlayerService customerService;

    @Autowired
    private IPositionService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Positions> listProvinces() {
        return provinceService.findAll();
    }

    /*
    @GetMapping
    public ModelAndView listCustomer(@RequestParam(defaultValue = "0") int page,Pageable pageable) {
        pageable = PageRequest.of(page, 4);
        Page<Player> customers = customerService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

     */

    /*
    @GetMapping
    public ModelAndView listCustomers(@RequestParam(defaultValue = "0") int page){
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        try {
            Pageable pageable = PageRequest.of(page,4);
            Page<Player> customersPlayer = customerService.findAll(pageable);
            modelAndView.addObject("customers",customersPlayer);
        } catch (Exception e){
            modelAndView.addObject("error","An error occurred while retrieving the list of players.");
        }
        return modelAndView;
    }

     */

    // khi them upload anh
/*
    @GetMapping
    public ModelAndView listCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam("img") MultipartFile img) {
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        try {
            // Process the uploaded image file
            if (img != null && !img.isEmpty()) {
                // Here you can add the logic to handle the image file, e.g., save it to the file system or database
                String fileName = img.getOriginalFilename();
                // Save the file to a specified directory
                img.transferTo(new java.io.File("path/to/save/directory/" + fileName));
                modelAndView.addObject("message", "File uploaded successfully: " + fileName);
            }
            // Pagination logic
            Pageable pageable = PageRequest.of(page, 4);
            Page<Player> customersPlayer = customerService.findAll(pageable);
            modelAndView.addObject("customers", customersPlayer);

        } catch (Exception e) {
            modelAndView.addObject("error", "An error occurred while retrieving the list of players or uploading the file.");
        }

        return modelAndView;
    }

 */




    @GetMapping
    public ModelAndView showListCustomer(@RequestParam(defaultValue = "0") int page) {
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        try {
            Pageable pageable = PageRequest.of(page, 3);
            Page<Player> playerList = customerService.findAll1(pageable);
            modelAndView.addObject("customers", playerList);
            return modelAndView;
        } catch (Exception e){
            return new ModelAndView("redirect:/customers");
        }
    }


    @GetMapping("/{id}")
    public ModelAndView showInformation(@PathVariable Long id) {
        try {
            ModelAndView modelAndView = new ModelAndView("/customer/info");
            Player customer = customerService.findOne(id);
            modelAndView.addObject("customer", customer);
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView("redirect:/customers");
        }
    }

    @GetMapping("/search")
    public ModelAndView listCustomersSearch(@RequestParam("search") Optional<String> search, Pageable pageable){
        Page<Player> customers;
        if(search.isPresent()){
            customers = customerService.findAllByFirstNameContaining(pageable, search.get());
        } else {
            customers = customerService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Player());
        return modelAndView;
    }

    /*
    @PostMapping("/create")
    public String create(@ModelAttribute("customer") Player customer,
                         RedirectAttributes redirectAttributes) throws DuplicateEmailException {
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("message", "Create new customer successfully");
        return "redirect:/customers";
    }

     */
    @PostMapping("/create")
    public String create(@ModelAttribute("playerForm") PlayerForm playerForm,
                         RedirectAttributes redirectAttributes) throws DuplicateEmailException {
        try {
            Player customer = new Player();
            customer.setFirstName(playerForm.getFirstName());
            customer.setLastName(playerForm.getLastName());
            customer.setProvince(playerForm.getProvince());
            // Set other fields from playerForm to customer as needed

            // Process the uploaded image file
            MultipartFile img = playerForm.getImg();
            if (img != null && !img.isEmpty()) {
                String fileName = img.getOriginalFilename();
                String uploadDir = "C:\\images\\";
                //Ensure the directory exists
                File directory = new File(uploadDir);
                if(!directory.exists()){
                    directory.mkdirs();
                }
                //Save the file
                File uploadFile = new File(uploadDir + fileName);
                img.transferTo(uploadFile);
                // Set the image path in the customer object
                customer.setImagePath(fileName);
            }
            customerService.save(customer);
            redirectAttributes.addFlashAttribute("message", "Create new customer successfully");

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred while creating the customer or uploading the file.");
            return "redirect:/customers";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred while creating the customer.");
            return "redirect:/customers";
        }
        return "redirect:/customers";
    }


    @PostMapping("/save")
    public String save(Player customer) throws DuplicateEmailException {
        customerService.save(customer);
        return "redirect:/customers";
    }



    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Player> customer = customerService.findById(id);
        if (customer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/customer/update");
            modelAndView.addObject("customer", customer.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error_404");
        }
    }

    /*
    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("customer") Player customer,
                         RedirectAttributes redirect) throws DuplicateEmailException {
        customerService.save(customer);
        redirect.addFlashAttribute("message", "Update customer successfully");
        return "redirect:/customers";
    }

     */
    /*
    @PostMapping("/update/{id}")
    public ModelAndView updateForm(@Valid @ModelAttribute("customer") Player customer,
                                   BindingResult bindingResult, RedirectAttributes redirect) throws DuplicateEmailException {
        if(bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("/customer/update");
            modelAndView.addObject("customer", customer);
            return modelAndView;
        }
        customerService.save(customer);
        redirect.addFlashAttribute("message","Update players successfully...");
        return new ModelAndView("redirect:/customers");
    }

     */

    @PostMapping("/update/{id}")
    public ModelAndView updateForm(@Valid @ModelAttribute("customer") PlayerForm playerForm,
                                   BindingResult bindingResult,

                                   RedirectAttributes redirect) throws DuplicateEmailException {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/customer/update");
            modelAndView.addObject("customer", playerForm);
            return modelAndView;
        }

        try {
            Player customer = new Player();
            customer.setFirstName(playerForm.getFirstName());
            customer.setLastName(playerForm.getLastName());
            customer.setProvince(playerForm.getProvince());
            // Process the uploaded image file
            MultipartFile imgs = playerForm.getImg();
            if (imgs != null && !imgs.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + imgs.getOriginalFilename(); // Ensure unique filename
                String uploadDir = "C:\\images\\";

                // Ensure the directory exists
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Save the uploaded file
                File uploadFile = new File(uploadDir + fileName);
                imgs.transferTo(uploadFile);
                customer.setImagePath(fileName);
            }

            customerService.save(customer);
            redirect.addFlashAttribute("message", "Update player successfully...");

        } catch (IOException e) {
            ModelAndView modelAndView = new ModelAndView("/customer/update");
            modelAndView.addObject("customer", playerForm);
            modelAndView.addObject("error", "An error occurred while uploading the file.");
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("/customer/update");
            modelAndView.addObject("customer", playerForm);
            modelAndView.addObject("error", "An error occurred while updating the player.");
            return modelAndView;
        }

        return new ModelAndView("redirect:/customers");
    }



    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirect) {
        customerService.remove(id);
        redirect.addFlashAttribute("message", "Delete customer successfully");
        return "redirect:/customers";
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ModelAndView showInputNotAcceptable() {
        return new ModelAndView("/inputs-not-acceptable");
    }
}
