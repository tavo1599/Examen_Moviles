package pe.edu.upeu.asistenciaupeujc.data.remote

import pe.edu.upeu.asistenciaupeujc.modelo.Evento
import pe.edu.upeu.asistenciaupeujc.modelo.EventoReport
import pe.edu.upeu.asistenciaupeujc.modelo.Materialesx
import pe.edu.upeu.asistenciaupeujc.modelo.MaterialesxReport
import pe.edu.upeu.asistenciaupeujc.modelo.MsgGeneric
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RestEvento {
    @GET("/asis/evento/list")
    suspend fun reportarEvento(@Header("Authorization") token:String):Response<List<EventoReport>>


    @GET("/asis/evento/buscar/{id}")
    suspend fun getEventoId(@Header("Authorization") token:String , @Query("id") id:Long):Response<Evento>


    @DELETE("/asis/evento/eliminar/{id}")
    suspend fun deleteEvento(@Header("Authorization") token:String , @Path("id") id:Long):Response<MsgGeneric>


    @PUT("/asis/evento/editar/{id}")
    suspend fun actualizarEvento(@Header("Authorization") token:String , @Path("id") id:Long , @Body evento :Evento):Response<Evento>

    @POST("/asis/evento/crear")
    suspend fun insertarEvento(@Header("Authorization") token:String , @Body evento :Evento):Response<Evento>


}