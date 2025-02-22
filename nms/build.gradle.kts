
dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20-R0.1-SNAPSHOT")
    implementation("org.jetbrains:annotations:24.0.0")
    for (proj in subprojects) {
        implementation(proj)
        if (proj.name != "shared") {
            proj.dependencies.implementation(project(":nms:shared"))
        }
    }
}
