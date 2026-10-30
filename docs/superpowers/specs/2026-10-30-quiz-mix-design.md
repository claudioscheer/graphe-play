# Mistura do quiz em seis fatias

## Contexto

O quiz de hoje reserva metade das perguntas para `verseQuizQuestion`
("O que está escrito em…?") e sorteia a outra metade uniformemente do baralho.
Numa prova de 10 perguntas sobre a Bíblia inteira, a média é:

| Tipo | Hoje |
| --- | --- |
| Texto do versículo | 5 (50%) |
| Curiosidade | ~1,9 (19%) |
| História e geografia | ~0,25 (2,5%) |
| Assunto | ~1,35 (13,5%) |
| Nome | ~0,66 (7%) |
| Lugar | ~0,82 (8%) |

O tipo mais difícil ocupa metade da prova, e a história quase não aparece.
O objetivo é forçar variedade sem perder a promessa do contexto escolhido.

## Mistura aprovada

Seis fatias: cinco tipos fixos e uma carta aleatória.

| Tipo | 5 perguntas | 10 perguntas | 20 perguntas |
| --- | --- | --- | --- |
| Texto do versículo | 1 | 1–2 | 3–4 |
| História e geografia | 1 | 1–2 | 3–4 |
| Curiosidade | 1 | 1–2 | 3–4 |
| Assunto | 1 | 1–2 | 3–4 |
| Nomes e lugares | 1 | 1–2 | 3–4 |
| **Carta aleatória** | — | **2** | **4** |

- A fatia aleatória fica com **20%** (1 em cada 5), um pouco acima do sexto
  igual de 16,7%.
- Os outros 80% são divididos entre os cinco tipos fixos. Numa prova de 10,
  três tipos fixos recebem 2 e dois recebem 1. Numa de 20, um tipo fixo recebe
  4 e quatro recebem 3.
- As sobras giram entre os tipos fixos a cada quiz, para assunto ou nomes e
  lugares não ficarem sempre com menos.
- **5 perguntas** é a exceção: não cabe a sexta fatia sem deixar um tipo de
  fora, então os cinco tipos fixos recebem uma pergunta cada e a aleatória
  fica de fora. A fatia aleatória começa nas provas de 10.
- A carta aleatória vem do mesmo contexto, sem repetir carta já sorteada. Ela
  pode render uma segunda curiosidade, um assunto, um lugar — é a surpresa.

## Algoritmo

`quizPlan(count, random)` devolve `QuizPlan(fixed, random)`:

1. `count <= 5`: um de cada tipo fixo, `random = 0`.
2. `count > 5`: `random = max(1, count / 5)`; o resto é dividido entre os
   cinco tipos fixos, com as sobras distribuídas em ordem sorteada.
3. `QuizPlan.total` é sempre `count` quando há cartas suficientes.

`buildMixedQuiz(prompts, distractorPool, verseQuestions, count, random)`:

1. Limita `prompts` ao contexto e agrupa por `Card.quizTopic()`.
2. Sorteia as fatias fixas. A fatia de versículo consome
   `verseQuestions` já montadas; as outras consomem cartas.
3. Sorteia as cartas aleatórias entre as que sobraram.
4. Se uma fatia fixa estiver vazia (história num quiz de livro, por exemplo),
   completa com as cartas restantes.
5. Se ainda faltar, completa com perguntas de versículo até o total pedido.
6. Embaralha a prova final.

Sem carta repetida e sem versículo repetido. As opções erradas continuam
vindo de `wrongAnswers`, com as três erradas escritas na carta.

## Contextos

- **Bíblia inteira**: as seis fatias disponíveis.
- **História e geografia**: só cartas `HIS`; a prova inteira vira história,
  sem versículo.
- **Livro ou capítulo**: sem história; as vagas de história vão para a fatia
  aleatória e para as outras cartas.
- **Capítulo pequeno** (3–5 cartas): as cartas acabam e o quiz completa com
  perguntas de versículo, como hoje. A mistura é alvo, não teto rígido.

## UI

- **Preparação**: abaixo de "Quantas perguntas", uma seção "Mistura" com a
  linha dos seis tipos e a nota "1 em cada 5 é uma carta aleatória." Para 5
  perguntas, "Uma pergunta de cada tipo disponível."
- **Resultado**: um rótulo pequeno acima de cada pergunta errada
  (`VERSÍCULO`, `HISTÓRIA E GEOGRAFIA`, `CURIOSIDADE`, `ASSUNTO`,
  `NOMES E LUGARES`). O histórico antigo não tem rótulo.
- Sem novo ajuste ou botão nesta rodada.

## Código

- `domain/.../Quiz.kt`: `QuizTopic`, `QuizPlan`, `quizPlan`, `Card.quizTopic`,
  `buildMixedQuiz`, campo `QuizQuestion.topic`.
- `app/.../QuizViewModel.kt`: pede as perguntas de versículo do contexto e
  chama `buildMixedQuiz`.
- `app/.../QuizScreen.kt`: seção "Mistura" e rótulos no resultado.
- `domain/src/test/.../QuizTest.kt`: planos de 5/10/20, giro das sobras,
  fallback de livro/capítulo, contexto de história e ausência de repetição.
- Sem migração de banco: `topic` só existe em memória.

## Fora do escopo

- Seletor de mistura na preparação.
- Guardar o tipo da pergunta no histórico.
- Mudar o texto ou a dificuldade de `verseQuizQuestion`.

## Critérios de aceite

- Prova de 10 na Bíblia inteira: 1–2 versículos, 2 aleatórias e os cinco
  tipos fixos representados.
- Prova de 5: um de cada tipo fixo, nenhuma aleatória.
- Prova de 20: 4 aleatórias e os cinco tipos fixos representados.
- Quiz de livro não pergunta história da igreja.
- Quiz de história não pergunta versículo nem assunto.
- Nenhuma carta ou versículo aparece duas vezes na mesma prova.
- Os testes existentes de `buildQuiz` continuam passando.
