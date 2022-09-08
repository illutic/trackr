package g.sig.core_data.database_impl.models.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import g.sig.core_data.models.user.UserSettings

@Entity
data class UserSettingsDb(
    @PrimaryKey override val userSettingsId: Int,
    override val userId: Int,
    @Embedded override val currency: CurrencyDb
) : UserSettings
