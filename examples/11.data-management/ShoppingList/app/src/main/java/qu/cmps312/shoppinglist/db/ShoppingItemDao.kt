package qu.cmps312.shoppinglist.db

import androidx.lifecycle.LiveData
import androidx.room.*
import qu.cmps312.shoppinglist.entity.ShoppingItem

@Dao
interface ShoppingItemDao {
    /* App will be notified of any changes of the Item table data
       Whenever Room detects Item table data change our LiveData
       observer will be called with the new list of items
       No need for suspend function as LiveData is already asynchronous
    */
    // suspend fun getAll() : List<Item>
    // p.name || ' ' || p.image : means concatenate name and image
    @Query("""
        Select i.id, i.quantity, i.updatedDate, i.productId, p.categoryId, 
            (p.name || ' ' || p.icon) as productName 
        From ShoppingItem i join Product p on i.productId = p.id
        """)
    fun getAll() : LiveData<List<ShoppingItem>>

    @Query("""
        Select i.id, i.quantity, i.updatedDate, i.productId, p.categoryId, 
            (p.name || ' ' || p.icon) as productName 
        From ShoppingItem i join Product p on i.productId = p.id 
        Where i.id = :itemId
        """)
    suspend fun getItem(itemId: Long) : ShoppingItem?

    @Query("""
        Select i.id, i.quantity, i.updatedDate, i.productId, p.categoryId, 
            (p.name || ' ' || p.icon) as productName 
        From ShoppingItem i join Product p on i.productId = p.id 
        Where i.productId = :productId
        """)
    suspend fun getItemByProductId(productId: Long) : ShoppingItem?

    @Query("select count(*) from ShoppingItem")
    fun getCount() : LiveData<Long>

    @Query("update ShoppingItem set quantity = :quantity where id = :id")
    suspend fun updateQuantity(id: Long, quantity: Int)

    // Returns id of newly added item
    @Insert
    suspend fun insert(item: ShoppingItem): Long

    // Return ids of newly added item
    @Insert
    suspend fun insertList(items: List<ShoppingItem>) : List<Long>

    @Update
    suspend fun update(item: ShoppingItem)

    @Delete
    suspend fun delete(item: ShoppingItem)
}