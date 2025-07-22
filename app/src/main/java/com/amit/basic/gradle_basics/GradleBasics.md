# Gradle Basics

## Run Gradle Commands from root of project
```bash
./gradlew clean
```

## 1. Add custom plugin in module app

```kotlin
class HelloWorldPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        print("Hello World")
    }
}

apply<HelloWorldPlugin>()
```

## Why use apply in project level gradle plugins
apply false show that just fetch information about plugin but don't run it at application level. 
and when we add them in module level gradle file under plugins than it means, now we are applying it.

## Gradle file
we can get all config option by writing "this." then it will suggest all parameters that we can add or modify.

## Library
1. Create (right click on projectName in files then create New -> Module -> Select Template).
2. In our case, we will create amit_module
3. it will automatically added to settings.gradle file. if not then add at the bottom of the file (include(":amit_library"))
4. add module in dependency to access in app module
```kotlin
implementation(project(":amit_library"))
```

## Build types
1. create new build type or flavour
```kotlin
create("staging"){
    isMinifyEnabled = true
    buildConfigField("String","BASE_URL", "https://uedutect.com/")
    proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}
```

2. Add in build features
```kotlin
buildFeatures {
    compose = true
    buildConfig = true
}
```

3. Get buildConfig field using this in project
```kotlin
val baseUrl = BuildConfig.BASE_URL
```

## Product Flavours
Total Flavours = sum(number of flavour in each dimension) * (total build Config)
```kotlin
//flavorDimensions += "default" // if using single dimension
flavorDimensions += listOf("default","style")
productFlavors {
    create("uat"){
        dimension = "default"
        applicationIdSuffix = ".uat"
        buildConfigField("String","BASE_URL", "https://uedutect.com/")
    }
    create("prod"){
        dimension = "default"
        buildConfigField("String","BASE_URL", "https://edutect.com/")
    }

    create("red"){
        dimension = "style"
        buildConfigField("String","Style", "Red")
    }
    create("green"){
        dimension = "style"
        buildConfigField("String","Style", "Green")
    }
}
```
in the above config, it will produce flavour : uatGreenRelease, uatGreenStaging ....etc

## Source Sets
these are used to create code in different build variant.



