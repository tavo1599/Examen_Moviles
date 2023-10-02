package pe.edu.upeu.asistenciaupeujc.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import pe.edu.upeu.asistenciaupeujc.modelo.Evento
import pe.edu.upeu.asistenciaupeujc.modelo.EventoConActividad


@Dao
interface EventoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEvento(evento: Evento)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEvento(evento: List<Evento>)

    @Update
    suspend fun modificarEvento(evento: Evento)

    @Query("delete  from evento where id=:evento")
    suspend fun eliminarEvento(evento: Long)
    @Delete
    suspend fun eliminarEvento(evento: Evento)
    @Transaction
    @Query("select m.*, a.nombreActividad from evento m, actividad a where m.actividadId=a.id")
    fun reportatEvento():LiveData<List<EventoConActividad>>



    @Query("select * from evento where id=:idx")
    fun buscarEvento(idx: Long): LiveData<Evento>
}