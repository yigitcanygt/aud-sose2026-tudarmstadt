dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://s01.oss.sonatype.org/content/repositories")
        mavenCentral()
    }
}

pluginManagement {
    repositories {
        maven("https://s01.oss.sonatype.org/content/repositories")
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "P2-Student"
