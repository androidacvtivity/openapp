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
        maven("https://jitpack.io")
        maven(url = "https://oss.sonatype.org/content/groups/public/") // necesar pentru xdo

        jcenter()
        // Sau repo-ul direct de la OpenSAGRES (fallback)
        maven { url = uri("https://oss.sonatype.org/content/repositories/releases/") }
    }
}

rootProject.name = "PdfViewer"
include(":app")
include(":core")
include(":ui")
include(":compose")
include(":compose-ui")
