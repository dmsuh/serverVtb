package dev.fixiki.hahaton

import dev.fixiki.hahaton.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*


@Controller
@RequestMapping("/admin")
class AdminController {
    @Autowired
    lateinit var filials     : FilialRepo
    @Autowired
    lateinit var services    : ServiceRepo
    @Autowired
    lateinit var categories  : CategoryRepo

    @GetMapping
    fun index(model: Model): String{
        model.addAttribute("filials",       filials.count() )
        model.addAttribute("services",     services.count() )
        model.addAttribute("categories", categories.count() )
        return "index"
    }

    @GetMapping("/filials")
    fun filials(model: Model, @PathVariable(required = false) n: Int?): String{

        val p = Pageable.ofSize(50)
        model.addAttribute("filials", filials.findAll( p.withPage( n ?: 0) ) )

        val week = DayOfWeek.values().map {
            it.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) to it.getDisplayName(TextStyle.SHORT, Locale("ru"))
        }
        model.addAttribute("week", week)

        model.addAttribute("filial", Filial())
        model.addAttribute("schedule", OpenSchedule())
        return "filials"
    }

    @PostMapping("/create_filial")
    fun newFilial(model: Model, @ModelAttribute f: Filial) : String{
        filials.save(f)
        return "redirect:filials"
    }

    @GetMapping("/edit_filial/{id}")
    fun editFilial(model: Model, @PathVariable id: Long) : String {
        val f = filials.findById(id)
        model.addAttribute("filial", f)
        return "edit_filial"
    }

    @PostMapping("/save_filial")
    fun editFilial(model: Model, @ModelAttribute filial: Filial) : String{
        filials.save(filial)
        return "redirect:filials"
    }


    @GetMapping("/services")
    fun getServices(model: Model):String{
        model.addAttribute("categories", categories.findAll())
        model.addAttribute("services", services.findAll())
        return "services"
    }

    @GetMapping("/services/{categoryId}")
    fun getServicesByCategory(model: Model, @PathVariable categoryId: Long):String{
        model.addAttribute("category", categories.findById(categoryId))
        model.addAttribute("services", services.findAll())
        return "services_by_category"
    }

    @PostMapping("/create_service")
    fun addService(model: Model, @ModelAttribute service: Service):String{
        services.save(service)
        return "redirect:services"
    }


    @PostMapping("/create_category")
    fun addCategory(model: Model, @ModelAttribute category: Category):String{
        categories.save(category)
        return "redirect:services"
    }

}
