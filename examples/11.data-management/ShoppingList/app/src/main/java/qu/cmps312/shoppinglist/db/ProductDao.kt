package qu.cmps312.shoppinglist.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.Query
import qu.cmps312.shoppinglist.entity.Category
import qu.cmps312.shoppinglist.entity.Product

@Dao
interface ProductDao {
    // Product related methods
    @Query("""
        Select p.id, p.name, p.icon, p.categoryId, c.categoryName category 
        From Product p join Category c on p.categoryId = c.categoryId 
        Where p.categoryId = :categoryId order by p.name 
        """)
    suspend fun getProducts(categoryId: Long): List<Product>

    // Returns a map needed to fill a Products Dropdown
    @MapInfo(keyColumn = "id", valueColumn = "name")
    @Query("""
        Select p.id, (p.name || ' ' || p.icon) as name From Product p
        Where p.categoryId = :categoryId order by p.name 
        """)
    suspend fun getProductsMap(categoryId: Long) : Map<Long, String>

    @Insert
    suspend fun insertProducts(products: List<Product>) : List<Long>

    // Category related methods
    @Query("select * from Category order by categoryName")
    fun getCategories() : LiveData<List<Category>>

    // Returns a map needed to fill a Categories Dropdown
    @MapInfo(keyColumn = "categoryId", valueColumn = "categoryName")
    @Query("select c.categoryId, c.categoryName from Category c order by categoryName")
    fun getCategoriesMap() : LiveData<Map<Long, String>>

    @Query("select * from Category where categoryName = :name")
    suspend fun getCategory(name: String) : Category?

    @Query("select count(*) from Category")
    suspend fun getCategoryCount() : Int

    @Insert
    suspend fun insertCategories(categories: List<Category>) : List<Long>

    @Query("""
       Select c.*, p.*
       From Category c join Product p on c.categoryId = p.categoryId
       """)
    suspend fun getCategoriesAndProducts(): Map<Category, List<Product>>

    @MapInfo(valueColumn = "productCount")
    @Query("""
       Select c.*, count(p.id) as productCount
       From Category c join Product p on c.categoryId = p.categoryId
       Group by c.categoryId
       """)
    suspend fun getCategoriesAndProductCounts(): Map<Category, Int>

    @MapInfo(keyColumn = "categoryName", valueColumn = "productCount")
    @Query("""
       Select c.categoryName, count(p.id) as productCount
       From Category c join Product p on c.categoryId = p.categoryId
       Group by c.categoryName
       """)
    suspend fun getCategoryNamesAndProductCounts(): Map<String, Int>
}