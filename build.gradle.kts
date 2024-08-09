plugins {
    id("fabric-loom") version("1.7.3")
}

group = "maven_group"()
version = "${"mod_version"()}+mc${"minecraft_version"()}"

repositories {
    maven("https://maven.fabricmc.net")
    maven("https://maven.parchmentmc.org")
    maven("https://maven.blamejared.com")
    maven("https://raw.githubusercontent.com/Fuzss/modresources/main/maven")
    exclusiveContent {
        forRepository { maven("https://api.modrinth.com/maven") }
        filter {
            includeGroup("maven.modrinth")
        }
    }
    maven("https://maven.terraformersmc.com/releases") {
        content {
            includeGroup("com.terraformersmc")
        }
    }
    maven("https://mvn.devos.one/releases")
    maven("https://mvn.devos.one/snapshots")
    maven("https://maven.jamieswhiteshirt.com/libs-release")
    maven("https://maven.tterrag.com")
}

configurations.configureEach {
    resolutionStrategy {
        force("net.fabricmc:fabric-loader:${"fabric_loader_version"()}")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${"minecraft_version"()}")
    @Suppress("UnstableApiUsage")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${"minecraft_version"()}:${"parchment_version"()}@zip")
    })

    modImplementation("net.fabricmc:fabric-loader:${"fabric_loader_version"()}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${"fabric_api_version"()}+${"minecraft_version"()}")

    modImplementation("com.simibubi.create:create-fabric-${"minecraft_version"()}:${"create_version"()}") {
        exclude(group = "com.github.llamalad7.mixinextras", module = "mixinextras-fabric")
    }

    modLocalRuntime("maven.modrinth:lazydfu:${"lazydfu_version"()}")
    modLocalRuntime("com.terraformersmc:modmenu:${"modmenu_version"()}")

    modImplementation("mezz.jei:jei-${"minecraft_version"()}-fabric:${"jei_version"()}")
    modCompileOnly("mezz.jei:jei-${"minecraft_version"()}-common:${"jei_version"()}")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.processResources {
    val props = mapOf(
        "mod_version" to project.version,
        "minecraft_version" to "minecraft_version"(),
        "fabric_loader_version" to "fabric_loader_version"(),
        "fabric_api_version" to "fabric_api_version"(),
        "create_version" to "create_version"(),
    )

    inputs.properties(props)

    filesMatching("fabric.mod.json") {
        expand(props)
    }
}

loom {
    accessWidenerPath = file("src/main/resources/create_connected.accesswidener")
}

operator fun String.invoke(): String = rootProject.ext[this] as String