package org.example

import com.google.auto.service.AutoService
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.squareup.kotlinpoet.*
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

annotation class Anno

@AutoService(SymbolProcessorProvider::class)
class SimpleKspProcessor : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return object : SymbolProcessor {
            override fun process(resolver: Resolver): List<KSAnnotated> {
                val allSeq = resolver.getSymbolsWithAnnotation(Anno::class.java.canonicalName)
                val annotated = allSeq.firstOrNull() ?: return emptyList()
                annotated.accept(object : KSVisitorVoid() {
                    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
                        val fileSpec = longReturn(function.packageName.asString())
                        runCatching {
                            val file = environment.codeGenerator.createNewFile(Dependencies.ALL_FILES, fileSpec.packageName, fileSpec.name)
                            OutputStreamWriter(file, StandardCharsets.UTF_8).use(fileSpec::writeTo)
                        }
                    }
                }, Unit)
                return listOf(annotated)
            }
        }
    }
}

fun longReturn(packageName: String): FileSpec {
    val myLongClass = "Qwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnm"
    val simpleClass = TypeSpec.classBuilder("KspGen")
    simpleClass.addFunction(
        FunSpec.builder("ccc")
            .addModifiers(KModifier.PUBLIC)
            .returns(ClassName.bestGuess("org.example.$myLongClass"))
            .addStatement("val a = 888")
            .addStatement("return $myLongClass()")
            .build()
    )
    return FileSpec.builder(packageName, "KspGen")
        .addType(simpleClass.build())
        .build()
}