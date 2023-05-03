package com.example.adoptaunamascotaapi.controller;

import com.example.adoptaunamascotaapi.model.SolicitudAdopcion;
import com.example.adoptaunamascotaapi.services.SolicitudAdopcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitud")
public class SolicitudAdopcionController {

    private final SolicitudAdopcionService solicitudAdopcionService;

    @Autowired
    public SolicitudAdopcionController(SolicitudAdopcionService solicitudAdopcionService) {
        this.solicitudAdopcionService = solicitudAdopcionService;
    }

    /**
     * Listar solicitudes o por id del animal o por id de usuario.
     * @param idAnimal -> Id del animal recibido.
     * @param idUsuario -> Id del usuario recibido.
     * @return
     */
    @GetMapping()
    public List<SolicitudAdopcion> getSolicitud(@RequestParam(required = false) Long idAnimal,@RequestParam(required = false) Long idUsuario) {
        return solicitudAdopcionService.listarSolicitudAdopcion(idAnimal, idUsuario);
    }

    /**
     * Añadir solicitud para el id del animal seleccionado y el usuario que lo ha seleccionado.
     * @param solicitudAdopcion -> solicitud recibida por el frontend.
     * @return -> Solicitud guardada con exito.
     */
    @PutMapping
    public SolicitudAdopcion putSolicitud(@RequestBody SolicitudAdopcion solicitudAdopcion){
        return solicitudAdopcionService.guardarSolicitudAdopcion(solicitudAdopcion);
    }

    /**
     * Eliminar -> solicitud por Id.
     * @param id -> id recibido por el frontend.
     */
    @DeleteMapping("/{id}")
    public void deleteSolicitud(@PathVariable Long id){
        solicitudAdopcionService.eliminarSolicitudAdopcion(id);
    }
}
