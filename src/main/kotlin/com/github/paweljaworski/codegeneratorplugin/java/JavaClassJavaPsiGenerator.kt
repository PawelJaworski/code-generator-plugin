package com.github.paweljaworski.codegeneratorplugin.java

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.JavaDirectoryService
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.jps.model.java.JavaSourceRootType
import pl.javorek.codegenerator.adapter.java.JavaClassMetadata
import pl.javorek.codegenerator.application.CodeGenerator
import pl.javorek.codegenerator.application.Result
import pl.javorek.codegenerator.application.Success

class JavaClassJavaPsiGenerator(val e: AnActionEvent) : CodeGenerator<JavaClassMetadata> {
    override fun generate(classMetadata: JavaClassMetadata) {
        var module: Module = e.getData(LangDataKeys.MODULE)!!
        var project = module.project
        var moduleRootManager = ModuleRootManager.getInstance(module)
        var sourceRoots = moduleRootManager.getSourceRoots(JavaSourceRootType.SOURCE)
            .first()

        ApplicationManager.getApplication().runWriteAction{
            CommandProcessor.getInstance()!!.executeCommand(project, {
                VfsUtil.createDirectoryIfMissing(sourceRoots, directoryFromPackage(classMetadata.classPackage))
                var javaPsiFacade = JavaPsiFacade.getInstance(project)
                var foundPackage = javaPsiFacade.findPackage(classMetadata.classPackage)
                var classDir = foundPackage!!.getDirectories( GlobalSearchScope.moduleScope(module) )
                    .first()
                JavaDirectoryService.getInstance().createClass(classDir, classMetadata.className)
            }, "test", "test")
        }
    }

    private fun directoryFromPackage(packageName: String): String {
        return packageName.replace(".", "/")

    }
}