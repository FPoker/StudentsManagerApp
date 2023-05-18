package projects.kotlin.cesaestudent.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room

import androidx.room.RoomDatabase
import projects.kotlin.cesaestudent.dao.AppDao
import projects.kotlin.cesaestudent.model.CourseModel

import projects.kotlin.cesaestudent.model.StudentModel
import projects.kotlin.cesaestudent.model.UserModel

@Database(entities = [StudentModel::class, CourseModel::class, UserModel::class], version = 1)
abstract class DatabaseHelper : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        private lateinit var INSTANCE: DatabaseHelper

        fun getDatabase(context: Context): DatabaseHelper {
            if (!::INSTANCE.isInitialized) {
                synchronized(DatabaseHelper::class.java) {
                    INSTANCE =
                        Room.databaseBuilder(context, DatabaseHelper::class.java, "studentDB")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE
        }
    }
}