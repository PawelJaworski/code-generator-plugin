package com.github.paweljaworski.codegeneratorplugin.java

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.psi.JavaDirectoryService
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiType
import com.intellij.psi.impl.search.JavaFilesSearchScope
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.jps.model.java.JavaSourceRootType
import pl.javorek.codegenerator.adapter.java.JavaMethodMetadata
import pl.javorek.codegenerator.application.CodeGenerator

class JavaMethodJavaPsiGenerator(val e: AnActionEvent) : CodeGenerator<JavaMethodMetadata> {
    override fun generate(methodMetadata: JavaMethodMetadata) {
        var module: Module = e.getData(LangDataKeys.MODULE)!!
        var project = module.project
        var moduleRootManager = ModuleRootManager.getInstance(module)
        var sourceRoots = moduleRootManager.getSourceRoots(JavaSourceRootType.SOURCE)
            .first()

        ApplicationManager.getApplication().runWriteAction{
            CommandProcessor.getInstance()!!.executeCommand(project, {

                var javaPsiFacade = JavaPsiFacade.getInstance(project)
                var foundClass = javaPsiFacade.findClass(methodMetadata.classQualifiedName, JavaFilesSearchScope(project))!!
                val method = javaPsiFacade.elementFactory.createMethod(methodMetadata.methodName, PsiType.VOID)
                foundClass.add(method)
            }, "test", "test")
        }
    }

    private fun directoryFromPackage(packageName: String): String {
        return packageName.replace(".", "/")

    }
}