package g.sig.core_data.database_impl.dao

import androidx.room.*
import g.sig.core_data.DataInterface
import g.sig.core_data.DataResponse
import g.sig.core_data.database_impl.models.user.UserSettingsDb
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao : DataInterface.UserInterface {
    @Update(onConflict = OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun setUserSettings(userSettings: UserSettingsDb): Flow<DataResponse>

    @Transaction
    @Query("SELECT * FROM UserDb")
    override suspend fun getUserSettings(): Flow<DataResponse>
}