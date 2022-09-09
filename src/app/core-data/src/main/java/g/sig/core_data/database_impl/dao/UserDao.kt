package g.sig.core_data.database_impl.dao

import androidx.room.*
import g.sig.core_data.models.user.UserSettings
import g.sig.core_data.models.user.UserWithSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Update(onConflict = OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setUserSettings(userSettings: UserSettings): Flow<Int>

    @Transaction
    @Query("SELECT * FROM User")
    suspend fun getUserSettings(): Flow<UserWithSettings>
}