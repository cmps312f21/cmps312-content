package qu.cmps312.shoppinglist.repository

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import qu.cmps312.shoppinglist.db.ShoppingDB
import qu.cmps312.shoppinglist.entity.Category
import qu.cmps312.shoppinglist.entity.Product
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
    suspend fun getItem(itemId: Long) = shoppingItemDao.getItem(itemId)

    // If item already exists just increase the quantity otherwise insert a new Item
    suspend fun addItem(item: ShoppingItem) : Long {
        val dbItem = shoppingItemDao.getItemByProductId(item.productId)
        return if (dbItem == null) {
            // Ensure that the productName and categoryId are set to null
            item.productName = null
            item.categoryId = null
            shoppingItemDao.insert(item)
        } else {
            val quantity = dbItem.quantity + item.quantity
            shoppingItemDao.updateQuantity(dbItem.id, quantity)
            dbItem.id
        }
    }

    suspend fun updateQuantity(id: Long, quantity: Int) = shoppingItemDao.updateQuantity(id,quantity)
    suspend fun updateItem(item: ShoppingItem) {
        // Ensure that the productName and categoryId are set to null
        item.productName = null
        item.categoryId = null
        shoppingItemDao.update(item)
    }
    suspend fun deleteItem(item: ShoppingItem) = shoppingItemDao.delete(item)
    fun getCount() = shoppingItemDao.getCount()

    suspend fun getProducts(categoryId: Long) = productDao.getProducts(categoryId)
    fun getCategories() = productDao.getCategories()

    // Used for database initialization
    companion object {
        private fun readData(context: Context, fileName: String) =
                context.assets.open(fileName)
                       .bufferedReader().use { it.readText() }

        suspend fun initDB(shoppingDB: ShoppingDB?, context: Context) {
            if (shoppingDB == null) return

            val productDao = shoppingDB.getProductDao()
            val categoryCount = productDao.getCategoryCount()
            // If categoryCount = 0 then means the DB is empty
            if (categoryCount == 0) {
                val json = Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }
                // Read from json file and write to db
                // 1. Insert categories
                var data = readData( context,"product_categories.json")

                val categories = json.decodeFromString<List<Category>>(data)
                val categoryIds = productDao.insertCategories(categories)
                println(">> Debug: categoryIds = productDao.insertCategories(categories) $categoryIds")

                // 2. Insert products
                data = readData( context,"products.json")
                var products = json.decodeFromString<List<Product>>(data)
                println(">> Debug: initDB products $products")

                products = products.map {
                    // Lookup the category id
                    val category = productDao.getCategory(it.category!!)
                    Product(it.name, it.image, category!!.id)
                }
                val productIds = productDao.insertProducts(products)
                println(">> Debug: productIds = productDao.insertProducts(products) $productIds")
            }
        }
    }
}