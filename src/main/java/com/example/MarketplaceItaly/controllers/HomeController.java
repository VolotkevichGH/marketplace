package com.example.MarketplaceItaly.controllers;


import com.example.MarketplaceItaly.models.Category;
import com.example.MarketplaceItaly.models.Product;
import com.example.MarketplaceItaly.models.Role;
import com.example.MarketplaceItaly.models.User;
import com.example.MarketplaceItaly.repositories.ProductRepository;
import com.example.MarketplaceItaly.repositories.UserRepository;
import com.google.api.services.sheets.v4.Sheets;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private static Sheets sheetsService;
    final static String SPREADSHEET_ID = "1tPxy-tj2a3J6ODzZRXiK2JTQd6usdHDvMJpPxWI6c_Y";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;

    Image image1;

    @GetMapping("/register")
    public String regPage(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if (userIsActive) {
            return "create A";
        } else {
            return "create";
        }
    }

    @PostMapping("/register")
    public String regPost(Model model, @RequestParam String email, @RequestParam String password, @RequestParam String firstname, @RequestParam String lastname) {

        boolean testUser = userRepository.findByUsername(email).isPresent();
        if (!testUser) {
            User user = new User();
            user.setName(firstname);
            user.setSurname(lastname);
            user.setPassword(passwordEncoder.encode(password));
            user.setUsername(email);
            user.setRoles(Set.of(Role.ROLE_USER));
            userRepository.save(user);
            return "redirect:/";
        } else {
            return "redirect:/register";
        }
    }

    @GetMapping("/")
    public String homePage(Model model) throws IOException, GeneralSecurityException {
        model.addAttribute("products", productRepository.findAll());
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if (userIsActive) {
            User user =  userRepository.findByUsername(name).get();
            System.out.println(user.getUsername() + "зашел в систему");
            model.addAttribute("myprod", user.getCart());
            return "D&G A";
        } else {
            System.out.println("Пользователь не авторизован");
            return "D&G";
        }
    }

    @PostMapping("/cartaddproduct-{product}")
    public String addProductToCart(@PathVariable Product product) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            ArrayList<Product> hashSet;
            try {
                if (user.getCart().isEmpty()) {
                    hashSet = new ArrayList<>();
                } else {
                    hashSet = new ArrayList<>(user.getCart());
                }
                hashSet.add(product);
                user.setCart(hashSet);
                userRepository.save(user);
            } catch (DataIntegrityViolationException e) {
                System.out.println(e.getMessage());
            }
        }
        return "redirect:/";
    }

    @GetMapping("/accessories")
    public String getAccessories(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Girl_2_to_13) && product.getCategory().contains(Category.accessories)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        model.addAttribute("all", productRepository.findAll());
        HashSet<String> categories = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men)) {
                categories.add(Category.men.name());
            }
            if (product.getCategory().contains(Category.women)) {
                categories.add(Category.women.name());
            }
            if (product.getCategory().contains(Category.child)) {
                categories.add(Category.child.name());
            }
        }
        model.addAttribute("categories", categories);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Accessories A";
        } else {
            return "Accessories";
        }

    }

    @GetMapping("/accessories-and-baby-carriers")
    public String getAccessoriesAndBabyCarriers(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Newborn_0_to_12_months) && product.getCategory().contains(Category.AccessoriesAndBabyCarriers)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Accessories and Baby carriers A";
        } else {
            return "Accessories and Baby carriers";
        }
    }

    @GetMapping("/accessories-and-baby-carriers1")
    public String getAccessoriesAndBabyCarriers1(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.NewbornGirl_0_to_30_months) && product.getCategory().contains(Category.AccessoriesAndBabyCarriers)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Accessories and Baby carriers1 A";
        } else {
            return "Accessories and Baby carriers1";
        }
    }

    @GetMapping("/accessories-and-baby-carriers2")
    public String getAccessoriesAndBabyCarriers2(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.NewbornBoy_0_to_30_months) && product.getCategory().contains(Category.AccessoriesAndBabyCarriers)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Accessories and Baby carriers2 A";
        } else {
            return "Accessories and Baby carriers2 ";
        }
    }

    @GetMapping("/accessories1")
    public String getAccessories1(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Boy_2_to_13) && product.getCategory().contains(Category.accessories)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Accessories1 A";
        } else {
            return "Accessories1";
        }
    }

    @GetMapping("/account")
    public String getAccount(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        return "Account";
    }

    @GetMapping("/activewear-and-beachwear")
    public String getActivewearAndBeachwear(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.clothing) && product.getCategory().contains(Category.ActivewearAndBeachwear)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Activewear and Beachwear A";
        } else {
            return "Activewear and Beachwear";
        }
    }

    @GetMapping("/admincart")
    public String getAdminCart(Model model) {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "AdminCart A";
        } else {
            return "AdminCart";
        }
    }

    @GetMapping("/backpacks-and-fanny-packs")
    public String getBackPacksAndFannyPacks(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.bags) && product.getCategory().contains(Category.BackpacksAndFannyPacks)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);

        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Backpacks and Fanny packs A";
        } else {
            return "Backpacks and Fanny packs";
        }
    }

    @GetMapping("/beachwear")
    public String getBeachWear(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Girl_2_to_13) && product.getCategory().contains(Category.Beachwear)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Beachwear A";
        } else {
            return "Beachwear";
        }
    }

    @GetMapping("/beachwear1")
    public String getBeachWear1(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Boy_2_to_13) && product.getCategory().contains(Category.Beachwear)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Beachwear1 A";
        } else {
            return "Beachwear1";
        }
    }

    @GetMapping("/belts-and-bijoux")
    public String getBeltsAndBijoux(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.accessories) && product.getCategory().contains(Category.BeltsAndBijoux)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Belts and Bijoux A";
        } else {
            return "Belts and Bijoux";
        }
    }

    @GetMapping("/bijoux-and-belts")
    public String getBijouxAndBelts(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.accessories) && product.getCategory().contains(Category.BijouxAndBelts)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Bijoux and Belts A";
        } else {
            return "Bijoux and Belts";
        }
    }

    @GetMapping("/bok")
    public String getBok(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if (userIsActive) {
            return "Bok A";
        } else {
            return "Bok";
        }
    }

    @GetMapping("/boots")
    public String getBoots(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.shoes) && product.getCategory().contains(Category.Boots)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Boots A";
        } else {
            return "Boots";
        }
    }

    @GetMapping("/boots-and-booties")
    public String getBootsAndBooTies(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.shoes) && product.getCategory().contains(Category.BootsAndBooties)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Boots and Booties A";
        }
        return "Boots and Booties";
    }

    @GetMapping("/briefcase-and-cluthces")
    public String getBriefCaseAndCluthces(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.bags) && product.getCategory().contains(Category.BriefcaseAndcluthces)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Briefcase and cluthces A";
        }
        return "Briefcase and cluthces";
    }

    @GetMapping("/brooches&tiepins")
    public String getBroochesTiepins(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.watches_jewelry) && product.getCategory().contains(Category.BroochesTiepins)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Brooches&Tiepins A";
        }
        return "Brooches&Tiepins";
    }

    @GetMapping("/charms-and-watches")
    public String getCharmsAndWatches(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.watches_jewelry) && product.getCategory().contains(Category.CharmsAndWatches)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Charms and watches A";
        }
        return "Charms and watches";
    }

    @GetMapping("/clutches-mini-and-micro-bags")
    public String getClutchesMiniAndMicroBags(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.bags) && product.getCategory().contains(Category.ClutchesMiniAndMicroBags)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Clutches mini and Micro Bags A";
        }
        return "Clutches mini and Micro Bags";
    }

    @GetMapping("/coats-and-jackets")
    public String getCoatsAndJackets(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.clothing) && product.getCategory().contains(Category.CoatsAndJackets)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);

        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Coats and Jackets A";
        }
        return "Coats and Jackets";
    }

    @GetMapping("/coats-and-jackets1")
    public String getCoatsAndJackets1(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.clothing) && product.getCategory().contains(Category.CoatsAndJackets)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Coats and Jackets1 A";
        }
        return "Coats and Jackets1";
    }

    @GetMapping("/coats-and-jackets2")
    public String getCoatsAndJackets2(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Girl_2_to_13) && product.getCategory().contains(Category.CoatsAndJackets)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Coats and Jackets2 A";
        }
        return "Coats and Jackets2";
    }

    @GetMapping("/coats-and-jackets3")
    public String getCoatsAndJackets3(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Boy_2_to_13) && product.getCategory().contains(Category.CoatsAndJackets)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Coats and Jackets3 A";
        }
        return "Coats and Jackets3";
    }

    @GetMapping("/crossbody-bags")
    public String getCrossBodyBags(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.bags) && product.getCategory().contains(Category.CrossbodyBags)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Crossbody Bags A";
        }
        return "Crossbody Bags";
    }

    @GetMapping("/cufflinks-and-necklaces")
    public String getCufflinksAndNecklaces(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.watches_jewelry) && product.getCategory().contains(Category.CufflinksAndNecklaces)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Cufflinks and necklaces A";
        }
        return "Cufflinks and necklaces";
    }

    @GetMapping("/denim-and-skirts")
    public String getDemimAndSkirts(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.clothing) && product.getCategory().contains(Category.DenimAndSkirts)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Denim and Skirts A";
        }
        return "Denim and Skirts";
    }

    @GetMapping("/denim-and-sweatshirts")
    public String getDemimAndSweatshirts(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.clothing) && product.getCategory().contains(Category.DenimAndSweatshirts)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Denim and Sweatshirts A";
        }
        return "Denim and Sweatshirts";
    }

    @GetMapping("/dresses&blazers")
    public String getDressesBlazers(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.clothing) && product.getCategory().contains(Category.Dresses_Blazers)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Dresses&Blazers A";
        }
        return "Dresses&Blazers";
    }

    @GetMapping("/dresses")
    public String getDresses(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Newborn_0_to_12_months) && product.getCategory().contains(Category.Dresses)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Dresses A";
        }
        return "Dresses";
    }

    @GetMapping("/dresses-and-sweaters")
    public String getDressesAndSweaters(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Girl_2_to_13) && product.getCategory().contains(Category.DressesAndSweaters)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Dresses and Sweaters A";
        }
        return "Dresses and Sweaters";
    }

    @GetMapping("/dresses1")
    public String getDresses1(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.NewbornGirl_0_to_30_months) && product.getCategory().contains(Category.Dresses)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Dresses1 A";
        }
        return "Dresses1";
    }

    @GetMapping("/dresses2")
    public String getDresses2(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.NewbornBoy_0_to_30_months) && product.getCategory().contains(Category.Dresses)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Dresses2 A";
        }
        return "Dresses2";
    }

    @GetMapping("/earrings-and-rings")
    public String getEarringsAndRings(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.watches_jewelry) && product.getCategory().contains(Category.EarringsAndRings)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Earrings and Rings A";
        }
        return "Earrings and Rings";
    }

    @GetMapping("/forgot")
    public String getForgot(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "forgot A";
        }
        return "forgot";
    }

    @GetMapping("/handbags-and-totes")
    public String getHandbagsAndTotes(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.bags) && product.getCategory().contains(Category.HandbagsAndTotes)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "handbags and Totes A";
        }
        return "handbags and Totes";
    }

    @GetMapping("/hats-and-gloves")
    public String getHatsAndGloves(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.accessories) && product.getCategory().contains(Category.HatsAndGloves)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Hats and Gloves A";
        }
        return "Hats and Gloves";
    }

    @GetMapping("/hats-and-gloves1")
    public String getHatsAndGloves1(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.accessories) && product.getCategory().contains(Category.HatsAndGloves)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Hats and Gloves1 A";
        }
        return "Hats and Gloves1";
    }

    @GetMapping("/icons")
    public String getIcons(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.sunglasses) && product.getCategory().contains(Category.Icons)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Icons A";
        }
        return "Icons";
    }

    @GetMapping("/inspection-{product}")
    public String getInspection(Model model, @PathVariable Product product) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImage());
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "inspection A";
        }
        return "inspection";
    }

    @GetMapping("/loafers-and-moccasins")
    public String getLoafersAndMoccasins(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.shoes) && product.getCategory().contains(Category.LoafersAndMoccasins)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Loafers and Moccasins A";
        }
        return "Loafers and Moccasins";
    }

    @GetMapping("/men")
    public String getMen(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "men A";
        }
        return "men";
    }

    @GetMapping("/neacklaces-and-charms")
    public String getNecklacesAndCharms(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.watches_jewelry) && product.getCategory().contains(Category.NeacklacesAndCharms)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Neacklaces and Charms A";
        }
        return "Neacklaces and Charms";
    }

    @GetMapping("/new-arrivals")
    public String getNewArrivals(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.sunglasses) && product.getCategory().contains(Category.NewArrivals)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "New arrivals A";
        }
        return "New arrivals";
    }

    @GetMapping("/new-arrivals-and-icons")
    public String getNewArrivalsAndIcons(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.sunglasses) && product.getCategory().contains(Category.NewArrivalsAndIcons)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "New arrivals and icons A";
        }
        return "New arrivals and icons";
    }

    @GetMapping("/newborn-shoes")
    public String getNewbornShoes(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Newborn_0_to_12_months) && product.getCategory().contains(Category.NewbornShoes)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Newborn Shoes A";
        }
        return "Newborn Shoes";
    }

    @GetMapping("/newborn-shoes1")
    public String getNewbornShoes1(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.NewbornGirl_0_to_30_months) && product.getCategory().contains(Category.NewbornShoes)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Newborn Shoes1 A";
        }
        return "Newborn Shoes1";
    }

    @GetMapping("/newborn-shoes2")
    public String getNewbornShoes2(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.NewbornBoy_0_to_30_months) && product.getCategory().contains(Category.NewbornShoes)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Newborn Shoes2 A";
        }
        return "Newborn Shoes2";
    }

    @GetMapping("/nightwear-and-underwear")
    public String getNightwearAndUnderwear(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Boy_2_to_13) && product.getCategory().contains(Category.NightwearAndUnderwear)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Nightwear and Underwear A";
        }
        return "Nightwear and Underwear";
    }

    @GetMapping("/pink")
    public String getPink(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "pink A";
        }
        return "pink";
    }

    @GetMapping("/pumps-and-skingback")
    public String getPumpsAndSkingback(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.shoes) && product.getCategory().contains(Category.PumpsAndSkingback)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Pumps and Skingback A";
        }
        return "Pumps and Skingback";
    }

    @GetMapping("/rings-and-bracelets")
    public String getRingsAndBracelets(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.watches_jewelry) && product.getCategory().contains(Category.RingsAndBracelets)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Rings and bracelets A";
        }
        return "Rings and bracelets";
    }

    @GetMapping("/sandals-and-slides")
    public String getSandalsAndSlides(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.shoes) && product.getCategory().contains(Category.SandalsAndSlides)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Sandals and Slides A";
        }
        return "Sandals and Slides";
    }

    @GetMapping("/sandals-and-wedges")
    public String getSandalsAndWedges(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.shoes) && product.getCategory().contains(Category.SandalsAndWedges)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Sandals and Wedges A";
        }
        return "Sandals and Wedges";
    }

    @GetMapping("/scarves-and-silks")
    public String getScarvesAndSilks(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.accessories) && product.getCategory().contains(Category.ScarvesAndSilks)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Scarves and Silks A";
        }
        return "Scarves and Silks";
    }

    @GetMapping("/scarves-and-silks1")
    public String getScarvesAndSilks1(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.accessories) && product.getCategory().contains(Category.ScarvesAndSilks)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Scarves and Silks1 A";
        }
        return "Scarves and Silks1";
    }

    @GetMapping("/settings")
    public String getSettings(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        User user = (User) userRepository.findByUsername(name).get();
        model.addAttribute("user", user);
        model.addAttribute("products", user.getCart());
        return "settings";
    }

    @GetMapping("/sheakers-and-flat")
    public String getSheakersAndFlat(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.shoes) && product.getCategory().contains(Category.SheakersAndFlat)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Sheakers and Flat A";
        }
        return "Sheakers and Flat";
    }

    @GetMapping("/sheakers-and-lace-ups")
    public String getSheakersAndLaceUps(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.shoes) && product.getCategory().contains(Category.SheakersAndLace_ups)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Sheakers and Lace-ups A";
        }
        return "Sheakers and Lace-ups";
    }

    @GetMapping("/shirts")
    public String getShirts(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.clothing) && product.getCategory().contains(Category.Shirts)) {
                products.add(product);
                String hasSale = "$" + product.getPrice();
                if (product.getHasSale()) {
                    String backPrice = "$" + product.getBackPrice();
                    String utxt = String.join("\u0336", backPrice.split(""));
                    hasSale += " " + utxt;
                }
                model.addAttribute("hasSale", hasSale);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Shirts A";
        }
        return "Shirts";
    }

    @GetMapping("/shirts-and-tops")
    public String getShirtsAndTops(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.clothing) && product.getCategory().contains(Category.ShirtsAndTops)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Shirts and Tops A";
        }
        return "Shirts and Tops";
    }

    @GetMapping("/shirts-and-tops1")
    public String getShirtsAndTops1(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Girl_2_to_13) && product.getCategory().contains(Category.ShirtsAndTops)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Shirts and Tops1 A";
        }
        return "Shirts and Tops1";
    }

    @GetMapping("/shoulder-and-crossbody-bags")
    public String getShoulderAndCrossbodyBags(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.bags) && product.getCategory().contains(Category.ShoulderAndCrossbodyBags)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Shoulder and Crossbody Bags A";
        }
        return "Shoulder and Crossbody Bags";
    }

    @GetMapping("/sicily-and-devotion")
    public String getSicilyAndDevotion(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.bags) && product.getCategory().contains(Category.SicilyAndDevotion)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Sicily and Devotion A";
        }
        return "Sicily and Devotion";
    }

    @GetMapping("/slaid")
    public String getSlaid(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "slaid A";
        }
        return "slaid";
    }

    @GetMapping("/slides-and-mules")
    public String getSlidesAndMules(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.shoes) && product.getCategory().contains(Category.SlidesAndMules)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Slides and Mules A";
        }
        return "Slides and Mules";
    }

    @GetMapping("/socks")
    public String getSocks(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.accessories) && product.getCategory().contains(Category.Socks)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Socks A";
        }
        return "Socks";
    }

    @GetMapping("/socks-and-technology")
    public String getSocksAndTechnology(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.accessories) && product.getCategory().contains(Category.SocksAndTechnology)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Socks and Technology A";
        }
        return "Socks and Technology";
    }

    @GetMapping("/special-occasions")
    public String getSpecialOccasions(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Newborn_0_to_12_months) && product.getCategory().contains(Category.SpecialOccasions)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Special occasions A";
        }
        return "Special occasions";
    }

    @GetMapping("/suits-and-blazers")
    public String getSuitsAndBlazers(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.clothing) && product.getCategory().contains(Category.SuitsAndBlazers)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Suits and Blazers A";
        }
        return "Suits and Blazers";
    }

    @GetMapping("/suits-and-shirts")
    public String getSuitsAndShirts(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Boy_2_to_13) && product.getCategory().contains(Category.SuitsAndShirts)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Suits and Shirts A";
        }
        return "Suits and Shirts";
    }

    @GetMapping("/sweaters")
    public String getSweaters(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Boy_2_to_13) && product.getCategory().contains(Category.Sweaters)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Sweaters A";
        }
        return "Sweaters";
    }

    @GetMapping("/sweaters-and-cardigans")
    public String getSweatersAndCardigans(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.clothing) && product.getCategory().contains(Category.SweatersAndCardigans)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Sweaters and Cardigans A";
        }
        return "Sweaters and Cardigans";
    }

    @GetMapping("/tshirts-and-polos")
    public String getTShirtsAndPolos(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.clothing) && product.getCategory().contains(Category.T_ShirtsAndPolos)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "T-Shirts and Polos A";
        }
        return "T-Shirts and Polos";
    }

    @GetMapping("/tshirts-and-sweatshirts")
    public String getTShirtsAndSweatshirts(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.clothing) && product.getCategory().contains(Category.T_ShirtsAndSweatshirts)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "T-Shirts and Sweatshirts A";
        }
        return "T-Shirts and Sweatshirts";
    }

    @GetMapping("/tshirts-and-sweatshirts1")
    public String getTShirtsAndSweatshirts1(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Boy_2_to_13) && product.getCategory().contains(Category.T_ShirtsAndSweatshirts)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "T-Shirts and Sweatshirts1 A";
        }
        return "T-Shirts and Sweatshirts1";
    }

    @GetMapping("/tshirts-and-sweatshirts2")
    public String getTShirtsAndSweatshirts2(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Girl_2_to_13) && product.getCategory().contains(Category.CoatsAndJackets)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "T-Shirts and Sweatshirts2 A";
        }
        return "T-Shirts and Sweatshirts2";
    }

    @GetMapping("/ties-and-pocket-squares")
    public String getTiesAndPocketSquares(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.accessories) && product.getCategory().contains(Category.TiesAndPocketSquares)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Ties and pocket squares A";
        }
        return "Ties and pocket squares";
    }

    @GetMapping("/topwear")
    public String getTopwear(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Topwear A";
        }
        return "Topwear";
    }

    @GetMapping("/topwear-and-beachwear")
    public String getTopwearAndBeachwear(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.NewbornGirl_0_to_30_months) && product.getCategory().contains(Category.TopwearAndBeachwear)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Topwear and Beachwear A";
        }
        return "Topwear and Beachwear";
    }

    @GetMapping("/topwear-and-beachwear1")
    public String getTopwearAndBeachwear1(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.NewbornBoy_0_to_30_months) && product.getCategory().contains(Category.TopwearAndBeachwear)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Topwear and Beachwear1 A";
        }
        return "Topwear and Beachwear1";
    }

    @GetMapping("/travel-bags-and-shoppers")
    public String getTravelBagsAndShoppers(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.bags) && product.getCategory().contains(Category.TravelBagsAndShoppers)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Travel bags and Shoppers A";
        }
        return "Travel bags and Shoppers";
    }

    @GetMapping("/trousers-and-shorts")
    public String getTrousersAndShorts(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.clothing) && product.getCategory().contains(Category.TrousersAndShorts)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Trousers and Shorts A";
        }
        return "Trousers and Shorts";
    }

    @GetMapping("/trousers-and-shorts1")
    public String getTrousersAndShorts1(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.bags) && product.getCategory().contains(Category.TrousersAndShorts)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Trousers and Shorts1 A";
        }
        return "Trousers and Shorts1";
    }

    @GetMapping("/trousers-and-shorts2")
    public String getTrousersAndShorts2(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Boy_2_to_13) && product.getCategory().contains(Category.TrousersAndShorts)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Trousers and Shorts2 A";
        }
        return "Trousers and Shorts2";
    }

    @GetMapping("/trousers-and-shorts3")
    public String getTrousersAndShorts3(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Newborn_0_to_12_months) && product.getCategory().contains(Category.TrousersAndShorts)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Trousers and Shorts3 A";
        }
        return "Trousers and Shorts3";
    }

    @GetMapping("/trousers-and-shorts4")
    public String getTrousersAndShorts4(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.NewbornGirl_0_to_30_months) && product.getCategory().contains(Category.NewbornShoes)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Trousers and Shorts4 A";
        }
        return "Trousers and Shorts4";
    }

    @GetMapping("/trousers-and-shorts5")
    public String getTrousersAndShorts5(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.NewbornBoy_0_to_30_months) && product.getCategory().contains(Category.AccessoriesAndBabyCarriers)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Trousers and Shorts5 A";
        }
        return "Trousers and Shorts5";
    }

    @GetMapping("/trousers-and-skirts")
    public String getTrousersAndSkirts(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.child) && product.getCategory().contains(Category.Girl_2_to_13) && product.getCategory().contains(Category.TrousersAndSkirts)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Trousers and Skirts A";
        }
        return "Trousers and Skirts";
    }

    @GetMapping("/underwear-and-beachwear")
    public String getUnderwearAndBeachwear(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.clothing) && product.getCategory().contains(Category.UnderwearAndBeachwear)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Underwear and Beachwear A";
        }
        return "Underwear and Beachwear";
    }

    @GetMapping("/underwear-and-loungewear")
    public String getUnderwearAndLoungewear(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.bags) && product.getCategory().contains(Category.UnderwearAndLoungewear)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Underwear and Loungewear A";
        }
        return "Underwear and Loungewear";
    }

    @GetMapping("/wallets-and-small-leather-goods")
    public String getWalletsAndSmallLeatherGoods(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.accessories) && product.getCategory().contains(Category.WalletsAndSmallLeatherGoods)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Wallets and small leather goods A";
        }
        return "Wallets and small leather goods";
    }

    @GetMapping("/wallets-and-small-leather-goods1")
    public String getWalletsAndSmallLeatherGoods1(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.men) && product.getCategory().contains(Category.accessories) && product.getCategory().contains(Category.WalletsAndSmallLeatherGoods)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Wallets and small leather goods1 A";
        }
        return "Wallets and small leather goods1";
    }

    @GetMapping("/watches&straps")
    public String getWatchesStraps(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        HashSet<Product> products = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getCategory().contains(Category.women) && product.getCategory().contains(Category.watches_jewelry) && product.getCategory().contains(Category.Watches_Straps)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "Watches&Straps A";
        }
        return "Watches&Straps";
    }

    @GetMapping("/women")
    public String getWomen(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            model.addAttribute("myprod", user.getCart());
            return "women A";
        }
        return "women";
    }




}

