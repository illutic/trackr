package g.sig.core_data.models.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Currency(
    @PrimaryKey(autoGenerate = true) val currencyId: Int,
    val currencyName: String
)
