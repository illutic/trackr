package g.sig.core_data

import android.content.Context
import android.content.SharedPreferences
import g.sig.core_data.database_impl.TrackRDatabase
import g.sig.core_data.database_impl.buildDatabase
import g.sig.core_data.shared_prefs.createSharedPrefs

object Singletons {
    private var database: TrackRDatabase? = null
    private var sharedPrefs: SharedPreferences? = null

    fun getDatabase(context: Context): TrackRDatabase {
        return database ?: buildDatabase(context).also { database = it }
    }

    fun getSharedPrefs(context: Context): SharedPreferences {
        return sharedPrefs ?: context.createSharedPrefs().also { sharedPrefs = it }
    }
}