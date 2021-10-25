package qu.cmps312.shoppinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import qu.cmps312.shoppinglist.entity.Category
import qu.cmps312.shoppinglist.entity.ShoppingItem
import qu.cmps312.shoppinglist.entity.Product
import qu.cmps312.shoppinglist.entity.User
import qu.cmps312.shoppinglist.repository.ShoppingRepository

/* Every time you change your entity classes, you must change the DB version
   otherwise you will get an exception.
   When the version changes the DB will be dropped and recreated
 */
@Database(entities = [Product::class, Category::class, ShoppingItem::class, User::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class ShoppingDB : RoomDatabase() {
    abstract fun getShoppingItemDao(): ShoppingItemDao
    abstract fun getProductDao(): ProductDao

    // Create a singleton dbInstance
    companion object {
        @Volatile // meaning that writes to this field are immediately made visible to other threads
        private var dbInstance: ShoppingDB? = null

        /* Protected from concurrent execution by multiple threads */
        @Synchronized
        fun getInstance(context: Context): ShoppingDB {
            if (dbInstance == null) {
                // Use Room.databaseBuilder to open( or create) the database
                dbInstance = Room.databaseBuilder(
                    context,
                    ShoppingDB::class.java, "shopping.db"
                ).fallbackToDestructiveMigration().build()
            }
            // After the DB instance is created load the data from json files
            // into the Category and Product tables if the Category table is empty
            CoroutineScope(Dispatchers.IO).launch {
                 ShoppingRepository.initDB(dbInstance, context)
            }
            return dbInstance as ShoppingDB
        }
    }
}
