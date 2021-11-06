package qu.cmps312.shoppinglist.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude

data class Address(val buildingNo: String, val street: String, val city: String) {
    constructor(): this("", "", "")
}

@Entity
data class User (
    @PrimaryKey
    @DocumentId
    var uid: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    // Marks property as excluded from saving to Firestore
    @get:Exclude val password: String,
    val role: String,
    @Embedded val address: Address?) {
    // Required by Firebase deserializer otherwise you get exception 'does not define a no-argument constructor'
    constructor(): this("", "", "", "",  "", "",null)
    constructor(uid: String, displayName: String, email: String): this(uid, displayName, "", email,  "", "",null)

    override fun toString()
            = "${firstName.trim()} - ${email.trim()}".trim()
}