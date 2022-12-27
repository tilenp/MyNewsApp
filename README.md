# MyNewsApp

## Instructions
- add NEWS_API_KEY in local.properties file: `NEWS_API_KEY=your_key`

## Architecture
The architecture is MVVM. 

## Modules
- app: entry point
- core: contains common components and classes
- data: contains dtos, data sources and repositories
- domain: contains domain models and use cases
- news: feature module, contains articles screen and article details screen

## Main components
- views: dispatch user events, observes data flow
- view models: handle events and delegate tasks to use cases, expose data flows
- use cases: execute common business logic and delegate mapping into domain models to mappers
- repository: synchronizes data between data sources, exposes data flows
- data sources: handles responses
- mappers: map objects

## Object types
- dto: represents data structure on the server
- domain: represents data on the screen

## Libraries
- Compose
- Coroutines and Flows
- Hilt
- JUnit5
- MockK
- MockWebServer
- Paging 3
- Retrofit
- Turbine


## Testing
The project contains unit tests
