package pe.edu.upeu.asistenciaupeujc.ui.presentation.screens.evento

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pe.edu.upeu.asistenciaupeujc.modelo.Actividad
import pe.edu.upeu.asistenciaupeujc.modelo.ComboModel
import pe.edu.upeu.asistenciaupeujc.modelo.Evento
import pe.edu.upeu.asistenciaupeujc.modelo.Materialesx
import pe.edu.upeu.asistenciaupeujc.modelo.MaterialesxReport
import pe.edu.upeu.asistenciaupeujc.repository.ActividadRepository
import pe.edu.upeu.asistenciaupeujc.repository.EventoRepository
import pe.edu.upeu.asistenciaupeujc.repository.MaterialesxRepository
import javax.inject.Inject

@HiltViewModel
class EventoFormViewModel @Inject constructor(
    private val materRepo: EventoRepository,
    private val activRepo: ActividadRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    fun getEvento(idX: Long): LiveData<Evento> {
        return materRepo.buscarEventoId(idX)
    }
    val isLoading: LiveData<Boolean> get() = _isLoading

    val activ: LiveData<List<Actividad>> by lazy { activRepo.reportarActividades()}
    var listE = mutableListOf<ComboModel>(ComboModel(0.toString(), "Seleccione"))

    init {
        viewModelScope.launch {
            _isLoading.postValue(true)
            delay(1500)
            activ.value?.forEach {
                listE.add(ComboModel(code = it.id.toString(), name = it.nombreActividad))
            }
            //listE.removeAt(0)
            _isLoading.postValue(false)
        }
    }

    fun addEvento(evento: Evento){
        viewModelScope.launch (Dispatchers.IO){
            try {
                materRepo.insertarEvento(evento)
            }catch (e:Exception){
                Log.i("ERRRRR", "${e.message}")
            }
        }
    }
    fun editEvento(evento: Evento){
        viewModelScope.launch(Dispatchers.IO){
            try {
                materRepo.modificarRemoteEvento(evento)
            }catch (e:Exception){
                Log.i("ERRRRREDI", "${e.message}")
            }
        }
    }
}