//package com.golubovich.project_trpo_tofi.restcontroller;
//
//import com.golubovich.project_trpo_tofi.model.Credit;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@RestController
//@RequestMapping("/api/v1/credits")
//public class CreditsRestControllerV1 {
//
//    private List<Credit> CREDITS;
//            = Stream.of(
//            new Credit(1L, "For car", 12.5, null, null, null),
//            new Credit(2L, "For home", 13, null, null, null),
//            new Credit(3L, "For sea", 20, null, null, null)
//    ).collect(Collectors.toList());
//
//    @GetMapping
//    public List<Credit> getAll() {
//        return CREDITS;
//    }
//
//    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('credits:read')")
//    public Credit getById(@PathVariable Long id) {
//        return CREDITS.stream().filter(credit -> credit.getId().equals(id))
//                .findFirst()
//                .orElse(null);
//    }
//
//    @PostMapping
//    @PreAuthorize("hasAuthority('credits:write')")
//    public Credit addNew(@RequestBody Credit credit) {
//        this.CREDITS.add(credit);
//        return credit;
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('credits:write')")
//    public void deleteById(@PathVariable Long id) {
//        this.CREDITS.removeIf(credit -> credit.getId().equals(id));
//    }
//}
