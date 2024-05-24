import build.plugin.Common

plugins {
    id("java")
    id("java-library")
    id("maven-publish")
}

publishing {
    publications.create<MavenPublication>("maven") {

        artifactId = Common.getArchiveName(project, rootProject)
        from(components["java"])
    }

    if (project.hasProperty("pubUrl")) {

        var url: String = project.properties["pubUrl"] as String
        url += if((version as String).endsWith("-SNAPSHOT")) {
            "snapshots"
        } else {
            "releases"
        }

        repositories.maven(url) {
            name = "pub"
            credentials(PasswordCredentials::class.java)
        }
    }
}