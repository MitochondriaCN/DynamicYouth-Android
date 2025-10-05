pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        //以下腾讯地图SDK
        maven {
            url = uri("https://repo1.maven.org/maven2")
        }
        maven {
            url = uri("https://oss.sonatype.org/content/groups/public")
        }
        maven {
            url = uri("https://mirrors.tencent.com/repository/maven/tencent_public/")
        }
        maven {
            url = uri("https://mirrors.tencent.com/repository/maven/tencent_public_snapshots")
        }
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
        }
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/staging/")
        }
    }
}

rootProject.name = "Dynamic Youth"
include(":app")
