package com.portfolio.travelAgency.controller;


import com.portfolio.travelAgency.config.HttpSessionConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@AllArgsConstructor
public class AdminController {

    private final HttpSessionConfig httpSessionConfig;


    @GetMapping("/admin")
    public String managementPage() {
        /*System.out.println(bookingService.archivedOldBookings());*/
        return "adminPages/admin";
    }

    @GetMapping("/admin/sessions")
    public String showSessions (Model model){
        log.info(httpSessionConfig.getActiveSessions().toString());

        model.addAttribute("sessionList", httpSessionConfig.getActiveSessions());

        return "adminPages/sessions";
    }
}
