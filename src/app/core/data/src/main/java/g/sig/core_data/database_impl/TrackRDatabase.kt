package g.sig.core_data.database_impl

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import g.sig.core_data.converters.CurrencyConverter
import g.sig.core_data.converters.DateConverter
import g.sig.core_data.database_impl.dao.CategoryDao
import g.sig.core_data.database_impl.dao.MonthDao
import g.sig.core_data.database_impl.dao.TransactionsDao
import g.sig.core_data.database_impl.dao.UserDao
import g.sig.core_data.models.transaction.Category
import g.sig.core_data.models.transaction.LoggedTransaction
import g.sig.core_data.models.transaction.Month
import g.sig.core_data.models.transaction.MonthCategoriesCrossRef
import g.sig.core_data.models.user.User
import g.sig.core_data.models.user.UserSettings

@Database(
    entities = [
        Category::class,
        Month::class,
        MonthCategoriesCrossRef::class,
        LoggedTransaction::class,
        User::class,
        UserSettings::class
    ],
    version = 1
)
@TypeConverters(DateConverter::class, CurrencyConverter::class)
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