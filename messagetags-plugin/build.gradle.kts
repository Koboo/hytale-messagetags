dependencies {
    api(project(":messagetags-api"))
}

pluginManifest {
    //isServerPlugin = false

    manifestConfiguration {
        pluginGroup = "Koboo"
        pluginName = "MessageTags"
    }

    runtimeConfiguration {
        runtimeDirectory = "D:/Hytale/hytale-runtime/"
        isProjectRelative = false
    }
}
