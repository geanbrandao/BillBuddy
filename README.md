### Racha Conta
Em Inglês:
SplitMate
ShareTab
DivvyDine
CheckSplitter
BillBuddy

Em Português:
DividirConta
DivideAí
RachaConta
CompartilhaConta
DiviConta

### Configuração de projeto

#### Adicionar plugin do ksp.
``build.gradle.kts(:Project)``
```groovy
alias(libs.plugins.google.devtools.ksp) apply false
```

``build.gradle.kts(:app)``
````groovy 
alias(libs.plugins.google.devtools.ksp)
````

#### Adicionar dependências do koin
````groovy
// KOIN
implementation(libs.koin.core)
implementation(libs.koin.annotations)
implementation(libs.koin.androidx.compose)
ksp(libs.koin.ksp.compiler)
````

#### Configurar arquivos gerados pelo KSP
Adicionar check do koin em tempo de compilação
````groovy
ksp {
    arg("KOIN_CONFIG_CHECK","true")
}
````
Adicionar uso dos arquivos gerados pelo ksp
```groovy
applicationVariants.forEach { variant ->
    kotlin.sourceSets {
        getByName(name) {
            kotlin.srcDir("build/generated/ksp/${variant.name}/kotlin")
        }
    }
}
```





 