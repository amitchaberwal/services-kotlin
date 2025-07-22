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

2. Setup on Activity level/Service Level

```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {}
```

3. Add Dependencies for injection in DI

```kotlin
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideUatClassInstance(
        @Named("uat") url: String
    ): ClassInstance {
        return ClassInstance()
    }

    @Provides
    @Singleton
    @Named("uat")
    fun provideUatUrl(): String {
        return "https://uedutech.com"
    }

    @Provides
    @Singleton
    @Named("prod")
    fun provideProdUrl(): String {
        return "https://edutech.com"
    }
}
```

In the above segment(@InstallIn), we're injecting all dependencies as SingletonComponent, so they're
active as long app is active.
but there are multiple type:

    3.1 ActivityComponent -> it lives til the active is not destroyed
    3.2 ViewModelComponent -> it live till the viewmodel is alive.
    3.3 ActivityRetainedComponent -> same as activity but it will not destroy on orientation change.
    3.4 SingletonComponent -> live as long as app is active.
    3.5 ServiceComponent -> for service.

    NOTE:
    a. This is applicable on all the dependencies defined in the module(AppModule).
    b. If there are multiple dependencies of same type then we can get exact dependency using @Named parameter

4. Define ViewModel

```kotlin
@HiltViewModel
class MyViewModel @Inject constructor(
    private val getClassInstance: ClassInstance
) : ViewModel() {}
```

5. Get ViewModel in view

```kotlin
@Composable
fun MyView(
    viewModel: MyViewModel = hiltViewModel()
) {
    // viewmodel can also be defined as 
    //val viewModel = hiltViewModel<MyViewModel>()
}
```

## Additional Information

1. Injection in services (we can't inject in constructure. so, inject dependency using field
   injection)

```kotlin
@AndroidEntryPoint
class MyService : Service() {
    @Inject
    lateinit var repo: MyRepository
}
```

2. Lazy Injection -> dependency doesn't get created on injection but it will get created when we use
   it.

```kotlin 
@HiltViewModel
class MyViewModel @Inject constructor(
    private val getClassInstance: Lazy<ClassInstance>
) : ViewModel() {
    init {
        // dependency will get created here when we're using it
        getClassInstance.callApi()
    }
}
```