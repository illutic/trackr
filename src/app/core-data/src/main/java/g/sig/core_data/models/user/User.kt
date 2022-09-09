package g.sig.core_data.models.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int
)

data class UserWithSettings(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val userSettings: UserSettings
)