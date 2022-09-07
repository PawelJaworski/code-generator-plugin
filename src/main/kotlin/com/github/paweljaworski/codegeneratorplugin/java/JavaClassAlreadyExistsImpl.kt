package com.github.paweljaworski.codegeneratorplugin.java

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.impl.search.JavaFilesSearchScope
import pl.javorek.codegenerator.adapter.java.JavaClassMetadata
import pl.javorek.codegenerator.application.CodePredicate

class JavaClassAlreadyExistsImpl(val e: AnActionEvent) : CodePredicate<JavaClassMetadata> {
    override fun test(metadata: JavaClassMetadata): Boolean {
        var project = e.getData(LangDataKeys.MODULE)!!.project

        return JavaPsiFacade.getInstance(project)
                .findClass("${metadata.classPackage}.${metadata.className}", JavaFilesSearchScope(project)) != null

    }
}
