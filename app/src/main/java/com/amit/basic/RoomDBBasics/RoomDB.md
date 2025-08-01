# Room DB Basics

## Install RoomDB Dependency
1. Add KSP in Project level build.gradle

```kotlin
plugins {
    id("com.google.devtools.ksp") version "2.1.21-2.0.1" apply false
}
```

2. Add KSP and RoomDB dependency in Module level build.gradle

```kotlin
plugins {
    id("com.google.devtools.ksp")
}

dependencies {
    implementation("androidx.room:room-runtime:2.7.2")
    ksp("androidx.room:room-compiler:2.7.2")
    implementation("androidx.room:room-ktx:2.7.2")
}
```

## Create Table

1. Create Entity aka Table.(Data class)
```kotlin
@Entity
data class Contact(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
```

2. Create DAO (Data access Object) which will be used to CRUD operation on database.(Interface Class)
```kotlin
@Dao
interface ContactDao {
    //upsert => Insert with replace data if id exists.
    @Upsert
    suspend fun insertContact(contact: ContactEntity)
    
    @Delete
    suspend fun deleteContact(contact: ContactEntity)

    @Query("SELECT * FROM contactentity ORDER BY firstName ASC")
    suspend fun getContactByFirstName(): Flow<List<ContactEntity>>
}
```

3. Create Database.(Abstract Class)
```kotlin
@Database(
    // define all entities
    entities = [ContactEntity::class],
    version = 1,
)
abstract class ContactDatabase: RoomDatabase() {
    //Define all Dao
    abstract val contactDao: ContactDao
}
```
