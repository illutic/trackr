package g.sig.core_data

import android.content.Context
import g.sig.core_data.database_impl.TrackRDatabase
import g.sig.core_data.database_impl.buildDatabase

object Singletons {
    private var database: TrackRDatabase? = null

    fun getDatabase(context: Context): TrackRDatabase {
        return database ?: buildDatabase(context)
            .also {
                database = it
            }
    }
}