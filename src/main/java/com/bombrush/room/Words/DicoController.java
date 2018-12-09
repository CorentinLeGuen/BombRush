/*
 * Copyright © BNP PARIBAS - All rights reserved.
 */
package com.bombrush.room.Words;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/dico")
public class DicoController {

    @Autowired
    private
    WordChecker wordChecker;

    @GetMapping("/french")
    public ResponseEntity checkIfFrenchWordExists(@RequestParam(name = "word") String word) {
        return wordChecker.frenchWordExists(word) ? new ResponseEntity(OK) : new ResponseEntity(NOT_FOUND);
    }
}
