package com.github.paweljaworski.codegeneratorplugin.action

import com.github.paweljaworski.codegeneratorplugin.java.JavaClassJavaPsiGenerator
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.JavaDirectoryService
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiManager
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.jps.model.java.JavaSourceRootType
import pl.javorek.codegenerator.adapter.java.CommandFacadeGenerationConfig
import pl.javorek.codegenerator.adapter.java.CqrsCommandMetadata
import java.util.*


class GenerateCode : AnAction() {
    val PACKAGE = "pl.javorek.codegenerator";

    override fun actionPerformed(e: AnActionEvent) {
        val file: VirtualFile? = e.dataContext.getData(PlatformDataKeys.VIRTUAL_FILE)
        val folder: VirtualFile = file!!.getParent()
        val config = CommandFacadeGenerationConfig.builder()
            .javaClassGenerator(JavaClassJavaPsiGenerator(e))
            .build()
        val cqrsMetadata = CqrsCommandMetadata.builder()
            .basePackage("pl.javorex.mda")
            .useCase("FirstExample")
            .facadeContext("Example")
            .build()
        config.generator
            .generate(cqrsMetadata)
    }
}