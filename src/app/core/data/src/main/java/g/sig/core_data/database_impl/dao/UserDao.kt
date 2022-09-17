package g.sig.core_data.database_impl.dao

import androidx.room.*
import g.sig.core_data.models.user.User
import g.sig.core_data.models.user.UserSettings
import g.sig.core_data.models.user.UserWithSettings

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setUser(user: User): Long

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM User WHERE userId == :userId")
    suspend fun getUser(userId: Int): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setUserSettings(userSettings: UserSettings)

    @Transaction
    @Query("SELECT * FROM User WHERE userId == :userId")
    suspend fun getUserSettings(userId: Int): UserWithSettings?
}