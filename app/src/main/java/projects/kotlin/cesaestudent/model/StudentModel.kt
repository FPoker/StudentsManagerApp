package projects.kotlin.cesaestudent.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "student_table",
    foreignKeys = [ForeignKey(
        entity = CourseModel::class,
        parentColumns = arrayOf("courseId"),
        childColumns = arrayOf("courseId"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class StudentModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "courseId")
    var courseId: Int = 0,
    @ColumnInfo(name = "nameStudent")
    var nameStudent: String = "",
    @ColumnInfo(name = "addressStudent")
    var addressStudent: String = "",
    @ColumnInfo(name = "emailStudent")
    var emailStudent: String = "",
    @ColumnInfo(name = "ageStudent")
    var ageStudent: Int = 0,
    @ColumnInfo(name = "phoneStudent")
    var phoneStudent: Int = 0
)

@Entity(
    tableName = "course_table"
)
data class CourseModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "courseId")
    var courseId: Int = 0,
    @ColumnInfo(name = "courseYear")
    var courseYear: Int = 0,
    @ColumnInfo(name = "courseDesignation")
    var courseDesignation: String = ""
)

@Entity(tableName = "user_table")
data class UserModel(
    @PrimaryKey(autoGenerate = true) val id_user: Int = 0,
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val token_password: String = "",
    var token_auth: String = "",
    var token_date: String = LocalDate.now().plusDays(1).toString()
)


