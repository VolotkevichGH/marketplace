package com.example.MarketplaceItaly.controllers;


import com.example.MarketplaceItaly.models.Product;
import com.example.MarketplaceItaly.models.Role;
import com.example.MarketplaceItaly.models.User;
import com.example.MarketplaceItaly.repositories.ProductRepository;
import com.example.MarketplaceItaly.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Set;


@Controller
@RequiredArgsConstructor
public class HomeController {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;

    @GetMapping("/register")
    public String regPage(Model model) {
        return "create";
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
            user.setRoles(Set.of(Role.ROLE_ADMIN));
            userRepository.save(user);
            return "redirect:/";
        } else {
            return "redirect:/register";
        }
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("products", productRepository.findAll());
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if(userIsActive){
            User user = (User) userRepository.findByUsername(name).get();
            System.out.println(user.getUsername() + "зашел в систему");
        } else {
            System.out.println("Пользователь не авторизован");
        }
        return "D&G";
    }

    @PostMapping("/cartaddproduct-{product}")
    public String addProductToCart(Model model, @PathVariable Product product) {
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
        return "Accessories";
    }

    @GetMapping("/accessories-and-baby-carriers")
    public String getAccessoriesAndBabyCarriers(Model model) {
        return "Accessories and Baby carriers";
    }

    @GetMapping("/accessories-and-baby-carriers1")
    public String getAccessoriesAndBabyCarriers1(Model model) {
        return "Accessories and Baby carriers1";
    }

    @GetMapping("/accessories-and-baby-carriers2")
    public String getAccessoriesAndBabyCarriers2(Model model) {
        return "Accessories and Baby carriers2";
    }

    @GetMapping("/accessories1")
    public String getAccessories1(Model model) {
        return "Accessories1";
    }

    @GetMapping("/account")
    public String getAccount(Model model) {
        return "Account";
    }

    @GetMapping("/activewear-and-beachwear")
    public String getActivewearAndBeachwear(Model model) {
        return "Activewear and Beachwear";
    }

    @GetMapping("/admincart")
    public String getAdminCart(Model model) {
        return "AdminCart";
    }

    @GetMapping("/backpacks-and-fanny-packs")
    public String getBackPacksAndFannyPacks(Model model) {
        return "Backpacks and Fanny packs";
    }

    @GetMapping("/beachwear")
    public String getBeachWear(Model model) {
        return "Beachwear";
    }

    @GetMapping("/beachwear1")
    public String getBeachWear1(Model model) {
        return "Beachwear1";
    }

    @GetMapping("/belts-and-bijoux")
    public String getBeltsAndBijoux(Model model) {
        return "Belts and Bijoux";
    }

    @GetMapping("/bijoux-and-belts")
    public String getBijouxAndBelts(Model model) {
        return "Bijoux and Belts";
    }

    @GetMapping("/bok")
    public String getBok(Model model) {
        return "Bok";
    }

    @GetMapping("/boots")
    public String getBoots(Model model) {
        return "Boots";
    }

    @GetMapping("/boots-and-booties")
    public String getBootsAndBooTies(Model model) {
        return "Boots and Booties";
    }

    @GetMapping("/briefcase-and-cluthces")
    public String getBriefCaseAndCluthces(Model model) {
        return "Briefcase and cluthces";
    }

    @GetMapping("/brooches&tiepins")
    public String getBroochesTiepins(Model model) {
        return "Brooches&Tiepins";
    }

    @GetMapping("/charms-and-watches")
    public String getCharmsAndWatches(Model model) {
        return "Charms and watches";
    }

    @GetMapping("/clutches-mini-and-micro-bags")
    public String getClutchesMiniAndMicroBags(Model model) {
        return "Clutches mini and Micro Bags";
    }

    @GetMapping("/coats-and-jackets")
    public String getCoatsAndJackets(Model model) {
        return "Coats and Jackets";
    }

    @GetMapping("/coats-and-jackets1")
    public String getCoatsAndJackets1(Model model) {
        return "Coats and Jackets1";
    }

    @GetMapping("/coats-and-jackets2")
    public String getCoatsAndJackets2(Model model) {
        return "Coats and Jackets2";
    }

    @GetMapping("/coats-and-jackets3")
    public String getCoatsAndJackets3(Model model) {
        return "Coats and Jackets3";
    }

    @GetMapping("/crossbody-bags")
    public String getCrossBodyBags(Model model) {
        return "Crossbody Bags";
    }

    @GetMapping("/cufflinks-and-necklaces")
    public String getCufflinksAndNecklaces(Model model) {
        return "Cufflinks and necklaces";
    }

    @GetMapping("/denim-and-skirts")
    public String getDemimAndSkirts(Model model) {
        return "Denim and Skirts";
    }

    @GetMapping("/denim-and-sweatshirts")
    public String getDemimAndSweatshirts(Model model) {
        return "Denim and Sweatshirts";
    }

    @GetMapping("/dresses&blazers")
    public String getDressesBlazers(Model model) {
        return "Dresses&Blazers";
    }

    @GetMapping("/dresses")
    public String getDresses(Model model) {
        return "Dresses";
    }

    @GetMapping("/dresses-and-sweaters")
    public String getDressesAndSweaters(Model model) {
        return "Dresses and Sweaters";
    }

    @GetMapping("/dresses1")
    public String getDresses1(Model model) {
        return "Dresses1";
    }

    @GetMapping("/dresses2")
    public String getDresses2(Model model) {
        return "Dresses2";
    }

    @GetMapping("/earrings-and-rings")
    public String getEarringsAndRings(Model model) {
        return "Earrings and Rings";
    }

    @GetMapping("/forgot")
    public String getForgot(Model model) {
        return "forgot";
    }

    @GetMapping("/handbags-and-totes")
    public String getHandbagsAndTotes(Model model) {
        return "handbags and Totes";
    }

    @GetMapping("/hats-and-gloves")
    public String getHatsAndGloves(Model model) {
        return "Hats and Gloves";
    }

    @GetMapping("/hats-and-gloves1")
    public String getHatsAndGloves1(Model model) {
        return "Hats and Gloves1";
    }

    @GetMapping("/icons")
    public String getIcons(Model model) {
        return "Icons";
    }

    @GetMapping("/inspection")
    public String getInspection(Model model) {
        return "inspection";
    }

    @GetMapping("/loafers-and-moccasins")
    public String getLoafersAndMoccasins(Model model) {
        return "Loafers and Moccasins";
    }

    @GetMapping("/men")
    public String getMen(Model model) {
        return "men";
    }

    @GetMapping("/neacklaces-and-charms")
    public String getNecklacesAndCharms(Model model) {
        return "Neacklaces and Charms";
    }

    @GetMapping("/new-arrivals")
    public String getNewArrivals(Model model) {
        return "New arrivals";
    }

    @GetMapping("/new-arrivals-and-icons")
    public String getNewArrivalsAndIcons(Model model) {
        return "New arrivals and icons";
    }

    @GetMapping("/newborn-shoes")
    public String getNewbornShoes(Model model) {
        return "Newborn Shoes";
    }

    @GetMapping("/newborn-shoes1")
    public String getNewbornShoes1(Model model) {
        return "Newborn Shoes1";
    }

    @GetMapping("/newborn-shoes2")
    public String getNewbornShoes2(Model model) {
        return "Newborn Shoes2";
    }

    @GetMapping("/nightwear-and-underwear")
    public String getNightwearAndUnderwear(Model model) {
        return "Nightwear and Underwear";
    }

    @GetMapping("/pink")
    public String getPink(Model model) {
        return "pink";
    }

    @GetMapping("/pumps-and-skingback")
    public String getPumpsAndSkingback(Model model) {
        return "Pumps and Skingback";
    }

    @GetMapping("/rings-and-bracelets")
    public String getRingsAndBracelets(Model model) {
        return "Rings and bracelets";
    }

    @GetMapping("/sandals-and-slides")
    public String getSandalsAndSlides(Model model) {
        return "Sandals and Slides";
    }

    @GetMapping("/sandals-and-wedges")
    public String getSandalsAndWedges(Model model) {
        return "Sandals and Wedges";
    }

    @GetMapping("/scarves-and-silks")
    public String getScarvesAndSilks(Model model) {
        return "Scarves and Silks";
    }

    @GetMapping("/scarves-and-silks1")
    public String getScarvesAndSilks1(Model model) {
        return "Scarves and Silks1";
    }

    @GetMapping("/settings")
    public String getSettings(Model model) {
        return "settings";
    }

    @GetMapping("/sheakers-and-flat")
    public String getSheakersAndFlat(Model model) {
        return "Sheakers and Flat";
    }

    @GetMapping("/sheakers-and-lace-ups")
    public String getSheakersAndLaceUps(Model model) {
        return "Sheakers and Lace-ups";
    }

    @GetMapping("/shirts")
    public String getShirts(Model model) {
        return "Shirts";
    }

    @GetMapping("/shirts-and-tops")
    public String getShirtsAndTops(Model model) {
        return "Shirts and Tops";
    }

    @GetMapping("/shirts-and-tops1")
    public String getShirtsAndTops1(Model model) {
        return "Shirts and Tops1";
    }

    @GetMapping("/shoulder-and-crossbody-bags")
    public String getShoulderAndCrossbodyBags(Model model) {
        return "Shoulder and Crossbody Bags";
    }

    @GetMapping("/sicily-and-devotion")
    public String getSicilyAndDevotion(Model model) {
        return "Sicily and Devotion";
    }

    @GetMapping("/slaid")
    public String getSlaid(Model model) {
        return "slaid";
    }

    @GetMapping("/slides-and-mules")
    public String getSlidesAndMules(Model model) {
        return "Slides and Mules";
    }

    @GetMapping("/socks")
    public String getSocks(Model model) {
        return "Socks";
    }

    @GetMapping("/socks-and-technology")
    public String getSocksAndTechnology(Model model) {
        return "Socks and Technology";
    }

    @GetMapping("/special-occasions")
    public String getSpecialOccasions(Model model) {
        return "Special occasions";
    }

    @GetMapping("/suits-and-blazers")
    public String getSuitsAndBlazers(Model model) {
        return "Suits and Blazers";
    }

    @GetMapping("/suits-and-shirts")
    public String getSuitsAndShirts(Model model) {
        return "Suits and Shirts";
    }

    @GetMapping("/sweaters")
    public String getSweaters(Model model) {
        return "Sweaters";
    }

    @GetMapping("/sweaters-and-cardigans")
    public String getSweatersAndCardigans(Model model) {
        return "Sweaters and Cardigans";
    }

    @GetMapping("/tshirts-and-polos")
    public String getTShirtsAndPolos(Model model) {
        return "T-Shirts and Polos";
    }

    @GetMapping("/tshirts-and-sweatshirts")
    public String getTShirtsAndSweatshirts(Model model) {
        return "T-Shirts and Sweatshirts";
    }

    @GetMapping("/tshirts-and-sweatshirts1")
    public String getTShirtsAndSweatshirts1(Model model) {
        return "T-Shirts and Sweatshirts1";
    }

    @GetMapping("/tshirts-and-sweatshirts2")
    public String getTShirtsAndSweatshirts2(Model model) {
        return "T-Shirts and Sweatshirts2";
    }

    @GetMapping("/ties-and-pocket-squares")
    public String getTiesAndPocketSquares(Model model) {
        return "Ties and pocket squares";
    }

    @GetMapping("/topwear")
    public String getTopwear(Model model) {
        return "Topwear";
    }

    @GetMapping("/topwear-and-beachwear")
    public String getTopwearAndBeachwear(Model model) {
        return "Topwear and Beachwear";
    }

    @GetMapping("/topwear-and-beachwear1")
    public String getTopwearAndBeachwear1(Model model) {
        return "Topwear and Beachwear1";
    }

    @GetMapping("/travel-bags-and-shoppers")
    public String getTravelBagsAndShoppers(Model model) {
        return "Travel bags and Shoppers";
    }

    @GetMapping("/trousers-and-shorts")
    public String getTrousersAndShorts(Model model) {
        return "Trousers and Shorts";
    }

    @GetMapping("/trousers-and-shorts1")
    public String getTrousersAndShorts1(Model model) {
        return "Trousers and Shorts1";
    }

    @GetMapping("/trousers-and-shorts2")
    public String getTrousersAndShorts2(Model model) {
        return "Trousers and Shorts2";
    }

    @GetMapping("/trousers-and-shorts3")
    public String getTrousersAndShorts3(Model model) {
        return "Trousers and Shorts3";
    }

    @GetMapping("/trousers-and-shorts4")
    public String getTrousersAndShorts4(Model model) {
        return "Trousers and Shorts4";
    }

    @GetMapping("/trousers-and-shorts5")
    public String getTrousersAndShorts5(Model model) {
        return "Trousers and Shorts5";
    }

    @GetMapping("/trousers-and-skirts")
    public String getTrousersAndSkirts(Model model) {
        return "Trousers and Skirts";
    }

    @GetMapping("/underwear-and-beachwear")
    public String getUnderwearAndBeachwear(Model model) {
        return "Underwear and Beachwear";
    }

    @GetMapping("/underwear-and-loungewear")
    public String getUnderwearAndLoungewear(Model model) {
        return "Underwear and Loungewear";
    }

    @GetMapping("/wallets-and-small-leather-goods")
    public String getWalletsAndSmallLeatherGoods(Model model) {
        return "Wallets and small leather goods";
    }

    @GetMapping("/wallets-and-small-leather-goods1")
    public String getWalletsAndSmallLeatherGoods1(Model model) {
        return "Wallets and small leather goods1";
    }

    @GetMapping("/watches&straps")
    public String getWatchesStraps(Model model) {
        return "Watches&Straps";
    }

    @GetMapping("/women")
    public String getWomen(Model model) {
        return "women";
    }


}

