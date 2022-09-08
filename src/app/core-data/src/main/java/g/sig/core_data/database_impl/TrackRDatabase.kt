package g.sig.core_data.database_impl

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import g.sig.core_data.database_impl.dao.CategoryDao
import g.sig.core_data.database_impl.dao.MonthDao
import g.sig.core_data.database_impl.dao.TransactionsDao
import g.sig.core_data.database_impl.dao.UserDao
import g.sig.core_data.database_impl.models.transaction.CategoryDb
import g.sig.core_data.database_impl.models.transaction.LoggedTransactionDb
import g.sig.core_data.database_impl.models.transaction.MonthCategoriesCrossRef
import g.sig.core_data.database_impl.models.transaction.MonthDb
import g.sig.core_data.database_impl.models.user.CurrencyDb
import g.sig.core_data.database_impl.models.user.UserDb
import g.sig.core_data.database_impl.models.user.UserSettingsDb

@Database(
    entities = [
        CategoryDb::class,
        MonthDb::class,
        MonthCategoriesCrossRef::class,
        LoggedTransactionDb::class,
        CurrencyDb::class,
        UserDb::class,
        UserSettingsDb::class
    ],
    version = 1
)
abstract class TrackRDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun monthDao(): MonthDao
    abstract fun transactionsDao(): TransactionsDao
    abstract fun userDao(): UserDao
}

fun buildDatabase(context: Context) =
    Room.databaseBuilder(
        context.applicationContext,
        TrackRDatabase::class.java,
        "trackr_database"
    ).build()