# Calculadora de IMC

Aplicativo Android nativo, feito em **Kotlin** com **Jetpack Compose**, que calcula o Índice de Massa Corporal (IMC) a partir do peso (kg) e da altura (m) digitados pelo usuário.

Em uma única tela, o app permite:

- digitar o **peso** em quilogramas (aceita casas decimais, ex.: `72.5`);
- digitar a **altura** em metros (aceita casas decimais, ex.: `1.75`);
- ativar ou desativar o **Switch "Arredondar resultado"**, que arredonda o IMC para o inteiro mais próximo;
- tocar em **Calcular** para exibir o valor do IMC e sua **classificação**.

Fórmula: `IMC = peso / (altura × altura)`. Exemplo: 70 kg e 1.75 m → IMC = 22,86 → "Peso normal".

| Faixa de IMC | Classificação |
|---|---|
| Menor que 18.5 | Abaixo do peso |
| De 18.5 até 24.9 | Peso normal |
| De 25.0 até 29.9 | Sobrepeso |
| 30.0 ou mais | Obesidade |

Se peso ou altura estiverem vazios ou inválidos, o app mostra `--` no lugar do resultado.

## Tecnologias utilizadas

- Kotlin
- Jetpack Compose (Material 3)
- Android Studio Classic
- Gradle (Kotlin DSL)

## Como compilar e executar

1. Instale o [Android Studio](https://developer.android.com/studio) e o Git.
2. Clone o repositório:
   ```bash
   git clone https://github.com/StrayTeacher03/kotlin-calculadora-imc.git
   ```
3. Abra o Android Studio e escolha **File > Open**, selecionando a pasta `kotlin-calculadora-imc`.
4. Aguarde o **Gradle Sync** terminar.
5. Crie um emulador em **Tools > Device Manager > Create Device** (ou conecte um celular com depuração USB ativada).
6. Selecione o dispositivo na barra superior e clique em **Run ▶** (`Shift + F10`).

## Estrutura de arquivos e responsabilidades

Código principal em `app/src/main/java/com/example/calculadoradeimc/MainActivity.kt`:

| Elemento | Responsabilidade |
|---|---|
| `MainActivity` | Ponto de entrada do app. Ativa o edge-to-edge e define o conteúdo com o tema e o `Scaffold`. |
| `CalculadoraImcLayout` | Tela única. Guarda o estado (`remember` + `mutableStateOf`): peso, altura, switch e resultado. Monta a `Column` com todos os elementos. |
| `EditNumberField` | Composable reutilizável de campo de texto numérico, usada para peso e altura. Aplica *state hoisting* (`value` + `onValueChanged`) e recebe o rótulo e as opções de teclado por parâmetro. |
| `ArredondarRow` | Linha com o texto "Arredondar resultado" e o `Switch`, também com *state hoisting* (`checked` + `onCheckedChange`). |
| `LimitesIMC` | `object` que centraliza os quatro limites da tabela (18.5, 24.9, 25.0, 29.9), evitando números mágicos. |
| `calcularIMC` | Função pública, fora da UI e não composable. Calcula o IMC e arredonda se o switch estiver ligado. Retorna `null` se os dados forem inválidos. |
| `classificar` | Usa `when` como expressão, comparando o IMC com os limites de `LimitesIMC`. |
| `formatarIMC` | Formata o valor para exibição (2 casas decimais, ou inteiro quando arredondado). |

Detalhes de implementação:

- Teclado numérico com `KeyboardType.Number` nos dois campos.
- Conversão de texto para número com `toDoubleOrNull()` e `?:` (valor padrão `0.0`), sem nenhum `!!`.
- O resultado só é atualizado ao tocar em **Calcular**.

## Hierarquia de composables

```
CalculadoraImcLayout
 └─ Column
     ├─ Text            "Calcular IMC"
     ├─ EditNumberField peso
     ├─ EditNumberField altura
     ├─ ArredondarRow
     │    ├─ Text       "Arredondar resultado"
     │    └─ Switch
     ├─ Button          "Calcular"
     ├─ Text            IMC
     └─ Text            classificação
```

## Autor

- **Aluno:** Keslley Antonio de Almeida Dornelas Junior
- **Turma:** 3° Período de informática
- **Disciplina:** Laboratório de Programação para Dispositivos Móveis (LPDM) — CEFET-MG, Unidade Campo Belo
