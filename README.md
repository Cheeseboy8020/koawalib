## koawalib
koawalib is a general purpose FTC library written in Kotlin. Shoutout to [wpilib](https://github.com/wpilibsuite/allwpilib)
and [technolib](https://github.com/technototes/TechnoLib) for inspiration.

## Installation
- Add the following lines before the ```dependencies``` block in ```/TeamCode/build.gradle```
```
repositories {
  maven { url = 'https://maven.brott.dev/' }
  maven { url = 'https://jitpack.io' }
}
```
- Then add this line to your ```dependencies``` block, where "0.0.5" is the newest release version number
```
implementation 'com.github.AsianKoala:koawalib:0.0.5'
```

- ### Snapshot
  - If you wish to have the most recent commit of the repository rather than a release, consider using a snapshot.
     Snapshots are not guaranteed to work or even be stable, but they are guaranteed to be the most up to date.
  - To use the snapshot version, first add this code block. This changes the default gradle caching time settings.
    ```
    configurations.all {
        resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
        resolutionStrategy.cacheDynamicVersionsFor 10, 'minutes'
    }
    ```
  - Then add this line to the ```dependencies``` block, instead of the one used above.
    ```
    implementation 'com.github.AsianKoala:koawalib:master-SNAPSHOT'
    ```
  - If you want to use a specific commit that isn't the newest or in a release, use this syntax for the dependency, where COMMIT-HASH is the hash of the commit you want to use.
    ```
    implementation 'com.github.AsianKoala:koawalib:COMMIT-HASH"
    ```


## Example usage
koawalib-quickstart is coming (eventually)
In the mean time here is how I used it in my [codebase](https://github.com/14607/FF-Private/tree/master/TeamCode/src/main/java/asiankoala/ftc21/v3)

## Documentation
documentation is also coming (eventually)