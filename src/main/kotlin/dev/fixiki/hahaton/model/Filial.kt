package dev.fixiki.hahaton.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import jakarta.persistence.*
import org.hibernate.annotations.CollectionId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.transaction.annotation.Transactional
import java.sql.Time
import java.time.LocalTime


class OpenSchedule(
    var mon : Pair<LocalTime, LocalTime>?,
    var tue : Pair<LocalTime, LocalTime>?,
    var wed : Pair<LocalTime, LocalTime>?,
    var thu : Pair<LocalTime, LocalTime>?,
    var fri : Pair<LocalTime, LocalTime>?,
    var sat : Pair<LocalTime, LocalTime>?,
    var sun : Pair<LocalTime, LocalTime>?,
){
    constructor():this(null,null,null,null,null,null,null)
}


@Entity class Filial (
    @Id @GeneratedValue var id: Long?,

    @Column var schedule: String?,

    @Column(nullable = false) var name:             String?,
    @Column(nullable = false) var address:          String?,

    @Column(nullable = false) var isWorking:        Boolean?,

    @Column(nullable = false) var latitude:         Float?,
    @Column(nullable = false) var longitude:        Float?,

    /* обслуживание физ.лиц */
    @Column(nullable = false) var servesIndividual: Boolean?,
    /* обслуживание юр.лиц */
    @Column(nullable = false) var servesLegal:      Boolean?,
    /* привелегия */
    @Column(nullable = false) var serverPrivilege:  Boolean?,
    /* прайм */
    @Column(nullable = false) var servesPrime:      Boolean?,
    /* обслуживание маломобильных */
    @Column(nullable = false) var servesDisabled:   Boolean?,

    @ManyToMany               var services:         Set<Service>?,

    @Column var windows: Int?,
    @Column var openWindows: Int?,
    @Column var queue: Int?
){
    constructor() : this(null, null,null,null,null,null,null,null,null,null,null,null,null,null,null,null)

    fun loadPercent() : Int? {
        return if( openWindows!=null && queue !=null) 100*queue!!/openWindows!! else null
    }
}

interface FilialRepo : JpaRepository<Filial, Long> {
    fun findByLatitudeBetweenAndLongitudeBetween(left: Float, right: Float, top: Float, bottom: Float ): List<Filial>
    fun findByLatitudeBetweenAndLongitudeBetweenAndServices_NameIn(l: Float, r: Float, t: Float, b: Float,
                                                                    s: List<String> ): List<Filial>
    fun findByAddressLike(address: String): List<Filial>
    fun findByNameLike(name: String): List<Filial>


    @Transactional
    @Modifying
    @Query("update Filial f set f.openWindows = ?1 where f.id = ?2")
    fun updateOpenWindowsById(openWindows: Int, id: Long): Int


    @Transactional
    @Modifying
    @Query("update Filial f set f.queue = ?1 where f.id = ?2")
    fun updateQueueById(queue: Int, id: Long): Int
}