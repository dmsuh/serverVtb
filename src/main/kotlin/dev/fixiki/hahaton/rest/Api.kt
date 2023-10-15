package dev.fixiki.hahaton.rest

import dev.fixiki.hahaton.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

class Rect(
    var l: Float,
    var r: Float,
    var t: Float,
    var b: Float,
)

class FilialRequestParams(
    var rect: Rect,
    var serviceNames: List<String>?
)

@RestController @RequestMapping("/api") class Locations {

    @Autowired lateinit var filials     : FilialRepo
    @Autowired lateinit var services    : ServiceRepo
    @Autowired lateinit var categories  : CategoryRepo

    @CrossOrigin("http://localhost:9000")
    @GetMapping("/locations") fun getLocations(@RequestBody p: FilialRequestParams) : List<Filial> {
        val r = p.rect
        return if( p.serviceNames.isNullOrEmpty() )
            filials.findByLatitudeBetweenAndLongitudeBetween(r.l, r.r, r.t, r.b)
        else
            filials.findByLatitudeBetweenAndLongitudeBetweenAndServices_NameIn(r.l, r.r, r.t, r.b, p.serviceNames!!)
    }

    @CrossOrigin("http://localhost:9000")
    @GetMapping("/all")         fun getLocations()  : List<Filial>   { return    filials.findAll() }
    @CrossOrigin("http://localhost:9000")
    @GetMapping("/services")    fun getServices()   : List<Service>  { return   services.findAll() }
    @CrossOrigin("http://localhost:9000")
    @GetMapping("/categories")  fun getCategories() : List<Category> { return categories.findAll() }

    @CrossOrigin("http://localhost:9000")
    @GetMapping("/search/address")
    fun byAddress(@RequestBody s: String) : List<Filial> {return filials.findByAddressLike(s)}

    @CrossOrigin("http://localhost:9000")
    @GetMapping("/search/name")
    fun byName(@RequestBody s: String) : List<Filial> { return filials.findByNameLike(s) }


}

/**
 * endpoint для обновления статуса отделения. Предполагается что в отделенни работает система талонов очереди.
 */
@RestController @RequestMapping("/update_status") class Status{
    @Autowired lateinit var filials     : FilialRepo

    @PostMapping("/windows") fun setWindows(@PathVariable id:Long, @RequestBody count: Int){
        filials.updateOpenWindowsById(count, id)
    }

    @PostMapping("/queue") fun setQueue(@PathVariable id:Long,  @RequestBody count: Int){
        filials.updateQueueById(count, id)
    }
}