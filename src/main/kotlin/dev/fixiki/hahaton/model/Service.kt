package dev.fixiki.hahaton.model

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

@Entity
class Category(
    @Id @GeneratedValue var id: Long?,

    @Column(nullable = false) var name:     String?,
    @OneToMany                var Services: Set<Service>?,
)

interface CategoryRepo : JpaRepository<Category, Long>{

}

@Entity
class Service (
    @Id @GeneratedValue var id: Long?,

    @Column(nullable = false) var name:     String?,
    @ManyToMany               var filials:  Set<Filial>?,
    @ManyToOne                var category: Category?,
)

interface ServiceRepo : JpaRepository<Service, Long>{
    @Query("select s from Service s where s.category.id = ?1")
    fun findByCategory_Id(id: Long): List<Service>
}