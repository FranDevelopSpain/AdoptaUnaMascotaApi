package com.example.adoptaunamascotaapi.controller;

import com.example.adoptaunamascotaapi.model.Animal;
import com.example.adoptaunamascotaapi.model.SolicitudAdopcion;
import com.example.adoptaunamascotaapi.repository.SolicitudAdopcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://10.0.2.2:8080")
@RestController
@RequestMapping("/api/solicitud")
public class SolicitudAdopcionController {

    @Autowired
    private SolicitudAdopcionRepository solicitudAdopcionRepository;

    @PostMapping("/")
    public ResponseEntity<SolicitudAdopcion> createSolicitud(@RequestBody SolicitudAdopcion solicitud) {
        solicitudAdopcionRepository.save(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitud);
    }
    @GetMapping("/")
    public List<SolicitudAdopcion> getsolicitudes(){
        List<SolicitudAdopcion>solicitud = solicitudAdopcionRepository.findAll();
        return solicitud;
    }

}
