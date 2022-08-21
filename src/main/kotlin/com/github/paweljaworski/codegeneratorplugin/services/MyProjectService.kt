package com.github.paweljaworski.codegeneratorplugin.services

import com.intellij.openapi.project.Project
import com.github.paweljaworski.codegeneratorplugin.MyBundle
import com.intellij.openapi.vfs.VirtualFile

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))

    }
}
