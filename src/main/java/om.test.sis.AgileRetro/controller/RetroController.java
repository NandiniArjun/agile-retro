package com.test.sis.AgileRetro.controller;

import com.test.sis.AgileRetro.model.Retro;
import com.test.sis.AgileRetro.repository.RetroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class RetroController {

    @Autowired
    RetroRepository retroRepository;

    @PostMapping("/retros")
    public ResponseEntity<Retro> createRetro(@RequestBody Retro retro) {
        log.info("RetroController :: createRetro : start");
        try {
            if (retro.getDate() == null ^ retro.getParticipants() == null) {
                Retro _retro = retroRepository
                        .save(new Retro(retro.getName(), retro.getSummary(), retro.getDate(), retro.getParticipants()
                                , retro.getFeedback()));
                log.info("RetroController :: createRetro : end");
                return new ResponseEntity<>(_retro, HttpStatus.CREATED);
            } else {
                log.info("RetroController :: createRetro : Invalid inputs");
                throw new IllegalArgumentException("Invalid Inputs");
            }
        } catch (Exception e) {
            log.error("RetroController :: createRetro : Exception -> "+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/retros/{name}")
    public Retro retrieveRetro(@PathVariable String name, @RequestBody Map<String, Map<String, String>> feedback) {
        log.info("RetroController :: retrieveRetro : start");
        try {
            Optional<Retro> retro = retroRepository.findById(name);

            if (retro.isEmpty()) {
                log.info("RetroController :: retrieveRetro : Empty Retro");
                throw new IllegalArgumentException("Empty Retro!");
            }

            Retro _retro = retro.get();
            if (feedback.size() == 1) {
                Map<String, Map<String, String>> _feedback = _retro.getFeedback();
                if (_feedback == null) {
                    _feedback = new HashMap<>();
                }
                _feedback.putAll(feedback);
                _retro.setFeedback(_feedback);
                log.info("RetroController :: retrieveRetro : end");
                return retroRepository.save(_retro);
            } else {
                log.info("RetroController :: retrieveRetro : Multiple feedbacks");
                throw new IllegalArgumentException("Invalid Feedbacks");
            }
        }catch (Exception e) {
            log.error("RetroController :: retrieveRetro : Exception -> "+e.getMessage());
            throw e;
        }
    }

    @PutMapping("/retros/{name}/{iName}")
    public ResponseEntity<Object> updateRetro(@PathVariable String name, @PathVariable String iName, @RequestBody Map<String, String> item) {
        log.info("RetroController :: updateRetro : start");
        try {
            Optional<Retro> retro = retroRepository.findById(name);
            if (retro.isEmpty()) {
                log.info("RetroController :: retrieveRetro : Empty Retro");
                throw new IllegalArgumentException("Empty Retro!");
            }

            Retro _retro = retro.get();
            Map<String, Map<String, String>> map =  _retro.getFeedback();
            retroRepository.updateRetroFeedback(name, iName, map, item);
            log.info("RetroController :: updateRetro : end");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("RetroController :: updateRetro : Exception -> "+e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/retros")
    public ResponseEntity<List<Retro>> getAllRetros() {
        log.info("RetroController :: getAllRetros : start");
        try {
            Page<Retro> retros = retroRepository.findAll(PageRequest.of(0, 5));
            log.info("RetroController :: getAllRetros : end");
            return new ResponseEntity<>(retros.stream().toList(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("RetroController :: getAllRetros : Exception -> "+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
