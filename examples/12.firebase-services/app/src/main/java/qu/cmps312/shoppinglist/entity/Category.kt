package qu.cmps312.shoppinglist.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Category(
    @PrimaryKey
    @DocumentId
    val id: String = "",
    val name: String) {

    // Required by Firebase deserializer
    // otherwise you get exception 'does not define a no-argument constructor'
    constructor(): this("", "")

    override fun toString(): String {
        return name
    }
}