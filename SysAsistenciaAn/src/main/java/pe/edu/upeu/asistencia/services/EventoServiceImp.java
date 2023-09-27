/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.asistencia.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upeu.asistencia.dtos.EventoDto;
import pe.edu.upeu.asistencia.dtos.MaterialesxDto;
import pe.edu.upeu.asistencia.exceptions.AppException;

import pe.edu.upeu.asistencia.exceptions.ResourceNotFoundException;
import pe.edu.upeu.asistencia.mappers.EventoMapper;
import pe.edu.upeu.asistencia.mappers.MaterialesxMapper;
import pe.edu.upeu.asistencia.models.Evento;
import pe.edu.upeu.asistencia.models.Materialesx;
import pe.edu.upeu.asistencia.repositories.EventoRepository;
import pe.edu.upeu.asistencia.repositories.MaterialesxRepository;

/**
 *
 * @author DELL
 */

@RequiredArgsConstructor
@Service
@Transactional
public class EventoServiceImp implements EventoService {

    @Autowired
    private EventoRepository eventoRepo;

    @Autowired
    private ActividadService actividadService;

    private final EventoMapper eventoMapper;

    @Override
    public Evento save(EventoDto.EventoCrearDto evento) {

        Evento matEnt=eventoMapper.eventoCrearDtoToEvento(evento);
        matEnt.setActividadId(actividadService.getActividadById(evento.actividadId()));
        //matEnt.setModFh(null);
        System.out.println(evento.fecha());
        System.out.println(evento.horaReg());
        try {
            return eventoRepo.save(matEnt);
        } catch (Exception e) {
            throw new AppException("Error-" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<Evento> findAll() {
        try {
            return eventoRepo.findAll();
        } catch (Exception e) {
            throw new AppException("Error-" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Map<String, Boolean> delete(Long id) {
        Evento eventoo = eventoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento not exist with id :" + id));

        eventoRepo.delete(eventoo);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);

        return response;
    }

    @Override
    public Evento getEventoById(Long id) {
        Evento findEvento = eventoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento not exist with id :" + id));
        return findEvento;
    }

    @Override
    public Evento update(EventoDto.EventoCrearDto evento, Long id) {
        Evento eventoo = eventoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Periodo not exist with id :" + id));
            System.out.println("IMPRIME:"+evento.modFh());
        eventoo.setFecha(evento.fecha());
        eventoo.setHoraReg(evento.horaReg());
        eventoo.setOfflinex(evento.offlinex());
        return eventoRepo.save(eventoo);
    }

}
