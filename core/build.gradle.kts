import earth.terrarium.cloche.api.metadata.ModMetadata

plugins {
    id("earth.terrarium.cloche") version "0.10.4"
}

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

    metadata {
        modId = "common_storage_lib"
        name = "Common Storage Lib: Core"
        description = "Abstraction of the mod loader's resource storage system, allowing for easy access to items, fluids and energy. This library also includes abstractions for Data Attachments, Api Lookups, and Transfer Variants and ingredients"
        author("CodexAdrian")
        license = "MIT"
        issues = "https://github.com/terrarium-earth/Common-Storage-Lib/issues"
        sources = "https://github.com/terrarium-earth/Common-Storage-Lib"
    }

    common {
        dependencies {
            api(project(":data"))
        }
    }

    neoforge {
        loaderVersion = properties["neoforgeVersion"] as String

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

        dependencies {
            include(project(":data"))
            api(project(":data"))
        }

        runs {
            server()
            client()
            data()
        }
    }

    fabric {
        metadata {
            entrypoint("main", "earth.terrarium.common_storage_lib.FabricCommonStorageLib")
            dependency {
                modId = "fabric"
                version("*")
            }
            dependency {
                modId = "fabric"
                modId = ">=1.20.6"
            }
        }

        loaderVersion = properties["fabricLoaderVersion"] as String

        dependencies {
            fabricApi(properties["fabricApiVersion"] as String)

            dependencies {
                include(project(":data"))
                api(project(":data"))
            }
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