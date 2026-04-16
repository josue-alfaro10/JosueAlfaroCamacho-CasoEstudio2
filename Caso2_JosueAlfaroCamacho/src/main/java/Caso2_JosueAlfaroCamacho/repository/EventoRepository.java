/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Caso2_JosueAlfaroCamacho.repository;

import Caso2_JosueAlfaroCamacho.domain.Evento;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Alfaro
 */
public interface EventoRepository extends JpaRepository<Evento, Long> {

    // Consulta activo o inactivoi
    List<Evento> findByActivo(boolean activo);

    // Por fechas
    List<Evento> findByFechaBetween(LocalDate inicio, LocalDate fin);

    //Por nombre
    List<Evento> findByNombreContainingIgnoreCase(String nombre);

    // Eventos activos
    @Query("SELECT COUNT(e) FROM Evento e WHERE e.activo = true")
    long contarEventosActivos();
}
