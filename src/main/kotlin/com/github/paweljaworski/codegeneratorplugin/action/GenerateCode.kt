package com.github.paweljaworski.codegeneratorplugin.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.JavaDirectoryService
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiPackage
import com.intellij.psi.PsiPackageStatement
import com.intellij.psi.impl.file.PsiDirectoryFactory
import com.intellij.psi.impl.file.PsiJavaDirectoryFactory
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.jps.model.java.JavaSourceRootType
import java.util.*

class GenerateCode : AnAction() {
    val PACKAGE = "pl.javorek.codegenerator";

    override fun actionPerformed(e: AnActionEvent) {

        var module: Module = e.getData(LangDataKeys.MODULE)!!

        var moduleRootManager = ModuleRootManager.getInstance(module)

        var sourceRoots = moduleRootManager.getSourceRoots(JavaSourceRootType.SOURCE)
            .first()

        var project = module.project
        var javaPsiFacade = JavaPsiFacade.getInstance(project)
        var elementFactory = javaPsiFacade.elementFactory
        var packageStatement = elementFactory.createPackageStatement("pl.javorex")
        var psiManager = PsiManager.getInstance(module.project)
        var psiDirectory = psiManager.findDirectory(sourceRoots)!!
        VfsUtil.createDirectoryIfMissing(sourceRoots, "pl/srorex")
        var foundPackage = javaPsiFacade.findPackage("pl.srorex")
        var javaDirectoryService = JavaDirectoryService.getInstance()
        var rootPackage = javaDirectoryService.getPackage(psiDirectory)
        var classDir = foundPackage!!.getDirectories( GlobalSearchScope.moduleScope(module) )
            .first()


        var createdClass = elementFactory.createClass("GeneratedCode")



        var destinationDirectory = psiDirectory.findSubdirectory("pl/javorex")



        ApplicationManager.getApplication().runWriteAction{
//                var createdFile = psiDirectory.createFile("GenerateCode.java")

                CommandProcessor.getInstance()!!.executeCommand(project, {

                    var createdClass = JavaDirectoryService.getInstance().createClass(classDir, "GeneratedCodeInPackage")
//                    var codeBlock = elementFactory.createCodeBlock()
//                    var statements = codeBlock
//                        .add(packageStatement)
//                        .add(createdClass)
//                    createdFile.add(createdClass)

            }, "test", "test")
    }

//        var foundFile = Optional.ofNullable(psiDirectory.findFile("GeneratedCode"))
//            .orElse(createdFile)
    }

    fun runWriteAction(e: AnActionEvent) {

//        psiDirectory.add(foundFile)


    }
}