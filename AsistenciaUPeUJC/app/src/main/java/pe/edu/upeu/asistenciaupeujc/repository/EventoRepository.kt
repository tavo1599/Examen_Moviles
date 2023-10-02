package pe.edu.upeu.asistenciaupeujc.repository

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pe.edu.upeu.asistenciaupeujc.data.local.dao.EventoDao
import pe.edu.upeu.asistenciaupeujc.data.local.dao.MaterialesxDao
import pe.edu.upeu.asistenciaupeujc.data.remote.RestEvento
import pe.edu.upeu.asistenciaupeujc.data.remote.RestMaterialesx
import pe.edu.upeu.asistenciaupeujc.modelo.Evento
import pe.edu.upeu.asistenciaupeujc.modelo.EventoConActividad
import pe.edu.upeu.asistenciaupeujc.modelo.Materialesx
import pe.edu.upeu.asistenciaupeujc.modelo.MaterialesxConActividad
import pe.edu.upeu.asistenciaupeujc.utils.TokenUtils
import javax.inject.Inject

interface EventoRepository {

    suspend fun deleteEvento(evento: EventoConActividad)
    fun reportarEvento(): LiveData<List<EventoConActividad>>

    fun buscarEventoId(id:Long): LiveData<Evento>

    suspend fun insertarEvento(evento: Evento):Boolean

    suspend fun modificarRemoteEvento(evento: Evento):Boolean
}

class EventoRepositoryImp @Inject constructor(
    private val restEvento: RestEvento,
    private val eventoDao: EventoDao
): EventoRepository{
    override suspend fun deleteEvento(evento: EventoConActividad){
        CoroutineScope(Dispatchers.IO).launch {
            restEvento.deleteEvento(TokenUtils.TOKEN_CONTENT,evento.id)
        }
        eventoDao.eliminarEvento(evento.id)
    }


    override fun reportarEvento(): LiveData<List<EventoConActividad>> {
        try {
            CoroutineScope(Dispatchers.IO).launch{
                delay(3000)
                val data=restEvento.reportarEvento(TokenUtils.TOKEN_CONTENT).body()!!
                val dataxx = data.map {
                    Evento(it.id, it.cui, it.tipoCui,it.materEntre,it.fecha,it.horaReg, it.latituda,it.longituda, it.modFh, it.offlinex, it.actividadId.id, )
                }
                eventoDao.insertarEvento(dataxx)
            }

        }catch (e:Exception){
            Log.i("ERROR", "Error: ${e.message}")
        }
        return eventoDao.reportatEvento()
    }

    override fun buscarEventoId(id:Long): LiveData<Evento> {
        return  eventoDao.buscarEvento(id)
    }


    override suspend fun insertarEvento(evento: Evento):Boolean{
        //Log.i("DATAXXX", "${materialesx.actividadId}")
        return restEvento.insertarEvento(TokenUtils.TOKEN_CONTENT, evento).body()!=null
    }

    override suspend fun modificarRemoteEvento(evento: Evento):Boolean{

        CoroutineScope(Dispatchers.IO).launch {
            Log.i("VER", TokenUtils.TOKEN_CONTENT)
        }
        return restEvento.actualizarEvento(TokenUtils.TOKEN_CONTENT, evento.id, evento).body()!=null
    }
}