import earth.terrarium.cloche.api.metadata.ModMetadata

plugins {
    id("earth.terrarium.cloche") version "0.10.4"
}

group = properties["group"]!!
version = properties["version"]!!

repositories {
    cloche.librariesMinecraft()

    mavenCentral()

    cloche {
        main()

        mavenFabric()
        mavenNeoforged()
        mavenNeoforgedMeta()
    }
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.0.5")
}

cloche {
    minecraftVersion = properties["minecraftVersion"] as String

    mappings {
        official()
        parchment("2024.11.17")
    }

    metadata {
        modId = "common_storage_lib_data"
        name = "Common Storage Lib: Data"
        description = "Abstraction for Data Attachments, as well as providing a wrapper around Components to make working with all data easier."
        author("CodexAdrian")
        license = "MIT"
        issues = "https://github.com/terrarium-earth/Common-Storage-Lib/issues"
        sources = "https://github.com/terrarium-earth/Common-Storage-Lib"
    }

    neoforge {
        loaderVersion = properties["neoforgeVersion"] as String

        mixins.from("src/main/common_storage_lib_data.json")

        metadata {
            modLoader = "javafml"
            loaderVersion("[1,)")

            dependency {
                modId = "neoforge"
                required = true
                version("[20,)")
            }
            dependency {
                modId = "minecraft"
                required = true
                version("[1.20.6)")
            }
        }

        runs {
            server()
            client()
            data()
        }
    }

    fabric {
        metadata {
            entrypoint("main", "earth.terrarium.common_storage_lib.data.FabricDataLib")
            entrypoint("client", "earth.terrarium.common_storage_lib.data.FabricDataLibClient")
            dependency {
                modId = "fabric"
                version("*")
            }
            dependency {
                modId = "fabric"
                version(">=1.20.6")
            }
        }

        loaderVersion = properties["fabricLoaderVersion"] as String

        mixins.from("src/main/common_storage_lib_data.json")

        dependencies {
            fabricApi(properties["fabricApiVersion"] as String) // Optional
        }

        runs {
            server()
            client()
            data()
        }
    }
}

//dependencies {
//    if (System.getProperty("idea.sync.active", false.toString()).toBoolean()) {
//        compileOnly(projects.commonStorageLibDataCommon)
//        compileOnly(projects.commonStorageLibLookupCommon)
//        compileOnly(projects.commonStorageLibResourcesCommon)
//    }
//    include(api(projects.commonStorageLibDataNeoforge)!!)
//    include(api(projects.commonStorageLibLookupNeoforge)!!)
//    include(api(projects.commonStorageLibResourcesNeoforge)!!)
//}