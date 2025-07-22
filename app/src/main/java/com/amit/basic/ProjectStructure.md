## Clean Architecture based project structure
```text
/App
├── myapp/
│   ├── ui/
│   │   ├── MainActivity.kt
│   ├── MainApplication.kt
├── core/
│   ├── navigation/
│   │   ├── AppNavigation.kt
│   │   ├── Routes.kt
│   ├── network/
│   │   ├── NetworkModule.kt
│   ├── database/
│   │   ├── AppDatabase.kt
│   │   ├── DatabaseModule.kt
│   │   ├── dao/
│   │   │   ├── UserDao.kt
│   │   ├── entity/
│   │   │   ├── UserEntity.kt
├── feature/
│   ├── home/
│   │   ├── ui/
│   │   │   ├── HomeScreen.kt
│   │   │   ├── HomeViewModel.kt
│   │   │   ├── HomeEvent.kt
│   │   │   ├── HomeState.kt
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   ├── HomeUser.kt
│   │   │   ├── usecase/
│   │   │   │   ├── GetHomeUserUseCase.kt
│   │   │   ├── repository/
│   │   │   │   ├── HomeRepository.kt
│   │   ├── data/
│   │   │   ├── repository/
│   │   │   │   ├── HomeRepositoryImpl.kt
│   │   │   ├── remote/
│   │   │   │   ├── api/
│   │   │   │   │   ├── HomeApi.kt
│   │   │   ├── local/
│   │   │   │   ├── HomeLocalDataSource.kt
│   │   ├── di/
│   │   │   ├── HomeModule.kt
│   ├── profile/
│   │   ├── ui/
│   │   │   ├── ProfileScreen.kt
│   │   │   ├── ProfileViewModel.kt
│   │   │   ├── ProfileEvent.kt
│   │   │   ├── ProfileState.kt
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   ├── ProfileUser.kt
│   │   │   ├── usecase/
│   │   │   │   ├── GetProfileUserUseCase.kt
│   │   │   ├── repository/
│   │   │   │   ├── ProfileRepository.kt
│   │   ├── data/
│   │   │   ├── repository/
│   │   │   │   ├── ProfileRepositoryImpl.kt
│   │   │   ├── remote/
│   │   │   │   ├── api/
│   │   │   │   │   ├── ProfileApi.kt
│   │   │   ├── local/
│   │   │   │   ├── ProfileLocalDataSource.kt
│   │   ├── di/
│   │   │   ├── ProfileModule.kt
```
