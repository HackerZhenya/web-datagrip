package com.hackerzhenya.datagrip.controllers;

import com.hackerzhenya.datagrip.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.UUID;

@Controller
@RequestMapping("connections/{connectionId}/tables")
public class TablesController {
    @Autowired
    private TableService service;

    @GetMapping
    public String getTables(@PathVariable UUID connectionId, Model model) {

        model.addAttribute("tables", service.getTables(connectionId));
        return "tables/index";
    }

    @GetMapping("{table}/rows")
    public String getTable(@PathVariable UUID connectionId,
                           @PathVariable String table,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "limit", defaultValue = "100") int limit,
                           Model model) {
        var pair = service.getRows(connectionId, table, page, limit);

        model.addAttribute("page", page);
        model.addAttribute("limit", limit);
        model.addAttribute("table", pair.getFirst());
        model.addAttribute("rows", pair.getSecond());
        return "tables/inspector";
    }
}
