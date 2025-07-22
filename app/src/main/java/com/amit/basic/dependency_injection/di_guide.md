# Dependency Injection

## Install Dagger Hilt
1. Add package in Project level build.gradle
```kotlin
plugins {
    id("com.google.devtools.ksp") version "2.1.21-2.0.1" apply false
    id("com.google.dagger.hilt.android") version "2.56.1" apply false
}
```
2. Add Package in Module level build.gradle
```kotlin
plugins {
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

dependencies {
    implementation("com.google.dagger:hilt-android:2.56.1")
    ksp("com.google.dagger:hilt-android-compiler:2.56.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
}
```

## Setup In Project

1. Setup in Application level
```kotlin
@HiltAndroidApp
class MyAndroidApp : Application()
```
then add application name in manifest under application:name

2. Setup on Activity level
```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {}
```

3. Add Dependencies for injection in DI
```kotlin
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideClassInstance(): ClassInstance {
        return ClassInstance()
    }
}
```

4. Define ViewModel
```kotlin
@HiltViewModel
class MyViewModel @Inject constructor(
    private val getClassInstance: ClassInstance
) :ViewModel() {}
```

5. Get ViewModel in view
```kotlin
@Composable
fun MyView(
    viewModel: MyViewModel = hiltViewModel()
    ) {}
```