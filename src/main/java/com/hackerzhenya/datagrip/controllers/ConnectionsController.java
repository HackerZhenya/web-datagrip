package com.hackerzhenya.datagrip.controllers;

import com.hackerzhenya.datagrip.requests.CreateOrUpdateConnectionRequest;
import com.hackerzhenya.datagrip.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("connections")
public class ConnectionsController {
    @Autowired
    private ConnectionService service;

    @GetMapping
    public String getIndex(Model model) {
        model.addAttribute("connections", service.getConnections());
        return "connections/index";
    }

    @GetMapping("/add")
    public String getAdd(@ModelAttribute("connection") CreateOrUpdateConnectionRequest request) {
        return "connections/form";
    }

    @PostMapping("/add")
    public String postAdd(@Valid @ModelAttribute("connection") CreateOrUpdateConnectionRequest request,
                          BindingResult result) {
        if (result.hasErrors()) {
            return "connections/form";
        }

        service.createConnection(request);

        return "redirect:/connections";
    }

    @GetMapping("/{connectionId}/edit")
    public String getEdit(@PathVariable UUID connectionId,
                          @ModelAttribute("connection") CreateOrUpdateConnectionRequest request) {
        request.mapFrom(service.getConnection(connectionId));
        return "connections/form";
    }

    @PostMapping("/{connectionId}/edit")
    public String postEdit(@PathVariable UUID connectionId,
                           @Valid @ModelAttribute("connection") CreateOrUpdateConnectionRequest request,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "connections/form";
        }

        request.setId(connectionId);
        service.updateConnection(request);

        return "redirect:/connections";
    }

    @GetMapping("/{connectionId}/delete")
    public String getDelete(@PathVariable UUID connectionId) {
        service.deleteConnection(connectionId);
        return "redirect:/connections";
    }
}
