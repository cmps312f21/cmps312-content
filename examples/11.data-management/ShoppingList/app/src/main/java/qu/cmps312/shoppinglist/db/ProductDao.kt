package qu.cmps312.shoppinglist.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import qu.cmps312.shoppinglist.entity.Category
import qu.cmps312.shoppinglist.entity.Product

@Dao
interface ProductDao {
    // Product related methods
    @Query("select p.id, p.name, p.image, p.categoryId, c.name category from Product p join Category c on p.categoryId = c.id where p.categoryId = :categoryId order by p.name")
    suspend fun getProducts(categoryId: Long): List<Product>

    @Insert
    suspend fun insertProducts(products: List<Product>) : List<Long>

    // Category related methods
    @Query("select * from Category order by name")
    fun getCategories() : LiveData<List<Category>>

    @Query("select * from Category where name = :name")
    suspend fun getCategory(name: String) : Category?

    @Query("select count(*) from Category")
    suspend fun getCategoryCount() : Int

    @Insert
    suspend fun insertCategories(categories: List<Category>) : List<Long>
}