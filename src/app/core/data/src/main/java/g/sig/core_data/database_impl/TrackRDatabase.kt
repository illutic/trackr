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
import g.sig.core_data.models.transaction.*

@Database(
    entities = [
        Category::class,
        Month::class,
        MonthCategoriesCrossRef::class,
        LoggedTransaction::class,
        CategoryTransactionsCrossRef::class
    ],
    exportSchema = false,
    version = 1
)
@TypeConverters(DateConverter::class, CurrencyConverter::class)
abstract class TrackRDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun monthDao(): MonthDao
    abstract fun transactionsDao(): TransactionsDao
}

fun buildDatabase(context: Context) =
    Room.databaseBuilder(
        context.applicationContext,
        TrackRDatabase::class.java,
        "trackr_database"
    ).build()