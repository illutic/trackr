package g.sig.core_data.database_impl.models.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import g.sig.core_data.models.user.User

@Entity
data class UserDb(
    @PrimaryKey override val userId: Int
) : User

data class UserWithSettings(
    @Embedded val user: UserDb,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val userSettings: UserSettingsDb
)