/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.upeu.asistencia.services;

import java.util.List;
import java.util.Map;

import pe.edu.upeu.asistencia.dtos.EventoDto;

import pe.edu.upeu.asistencia.models.Evento;


/**
 *
 * @author DELL
 */
public interface EventoService {
    Evento save(EventoDto.EventoCrearDto evento);

    List<Evento> findAll();

    Map<String, Boolean> delete(Long id);

    Evento getEventoById(Long id);

    Evento update(EventoDto.EventoCrearDto evento, Long id);
}
