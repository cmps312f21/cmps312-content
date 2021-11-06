package qu.cmps312.shoppinglist.entity

import androidx.room.*
import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.Serializable

 /*(foreignKeys = [ForeignKey(entity = Category::class,
    parentColumns = ["id"],
    childColumns = ["categoryId"],
    onDelete = ForeignKey.CASCADE)],
    // Create an index on the categoryId column to speed-up query execution
    indices = [Index(value = ["categoryId"])]) */

@Serializable
@Entity
data class Product(
    @PrimaryKey
    @DocumentId
    val id: String = "",
    val name: String,
    val icon: String,
    val categoryId: String = "",
    /*
    Unfortunate limitation in Room:
      If category is annotated with @Ignore Room will ignore when writing 👍 but
      it will also ignore it when reading👎 even if category column is explicitly
      returned in a join query.
      So, as a workaround we store it as a null value but never read it
    */
    //@Ignore
    val category: String? = null) {
    // Required by Firebase deserializer otherwise you get exception 'does not define a no-argument constructor'
    constructor(): this("", "", "")

    constructor(name: String, icon: String, categoryId: String)
            : this("", name, icon, categoryId)

    override fun toString(): String {
        return "$name $icon"
    }
}