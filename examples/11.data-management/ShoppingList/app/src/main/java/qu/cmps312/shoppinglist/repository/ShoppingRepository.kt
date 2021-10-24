package qu.cmps312.shoppinglist.repository

import android.content.Context
import qu.cmps312.shoppinglist.db.ShoppingDB
import qu.cmps312.shoppinglist.entity.ShoppingItem

// Repository, abstracts access to multiple data sources
class ShoppingRepository(private val context: Context) {
    private val shoppingDB by lazy {
        ShoppingDB.getInstance(context)
    }

    private val shoppingItemDao by lazy {
        shoppingDB.getShoppingItemDao()
    }

    private val productDao by lazy {
        shoppingDB.getProductDao()
    }

    fun getItems() = shoppingItemDao.getAll()

    // If item already exists just increase the quantity otherwise insert a new Item
    suspend fun addItem(item: ShoppingItem) : Long {
        val dbItem = shoppingItemDao.getItem(item.productId)
        return if (dbItem == null) {
            // Ensure that the productName is always null
            item.productName = null
            shoppingItemDao.insert(item)
        } else {
            val quantity = dbItem.quantity + item.quantity
            shoppingItemDao.updateQuantity(dbItem.id, quantity)
            dbItem.id
        }
    }

    suspend fun updateQuantity(id: Long, quantity: Int) = shoppingItemDao.updateQuantity(id,quantity)
    suspend fun updateItem(item: ShoppingItem) = shoppingItemDao.update(item)
    suspend fun deleteItem(item: ShoppingItem) = shoppingItemDao.delete(item)

    suspend fun getProducts(categoryId: Long) = productDao.getProducts(categoryId)
    fun getCategories() = productDao.getCategories()
}