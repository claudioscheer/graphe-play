# Relatório da revisão das respostas erradas do quiz

Revisão linguística de `content/pt`, carta a carta, para que as três `wrongs` de cada quiz se pareçam com a `answer` e não entreguem a resposta pelo formato.

**Estado revisado:** 67 arquivos, 4.410 cartas. **Cartas com respostas erradas reescritas: 1.883.**

## Regra aplicada

A regra ficou registrada em `content/GUIDE.md`, seção "Respostas erradas do quiz".

- Mesma moldura, termo trocado: mantém a estrutura e as palavras da certa e troca só o elemento que decide a resposta. Nunca cola a certa e acrescenta "e…", "mas…", "porque…".
- Mesma forma e tamanho: nome com nome, lugar com lugar e a mesma preposição, número com número e a mesma unidade, frase com frase do mesmo tipo, entre 60% e 160% dos caracteres da certa.
- Mesma polaridade: certa afirma, três erradas afirmam; certa nega, três negam. Pergunta de sim/não com "Não." recebe três "Não." com motivo falso; com "Sim.", três "Sim.".
- Erro plausível: a errada faz sentido e soa verdadeira para quem não decorou o versículo, mas está errada para o versículo citado. Sem contrário direto, sem antônimo, sem "Ninguém.", "Nada.", "Um anjo.", sem frase absurda e sem errada que seja verdadeira.

## Como foi feito

Um subagente por arquivo, 67 no total. Cada um leu o `GUIDE.md` e o arquivo-modelo `content/pt/1JN.json`, revisou todas as cartas do seu livro e alterou apenas o array `wrongs`. Depois da primeira rodada, o autor corrigiu dois desvios de critério: erradas que eram o contrário direto continuavam óbvias, e erradas montadas como "certa + complemento" deixavam a primeira metade correta. Os seis arquivos-piloto foram refeitos e os demais já rodaram com o critério final.

No fechamento, cinco cartas foram corrigidas à mão por ainda terem o padrão "certa + complemento": `LEV.6.subject.1`, `MAT.22.curiosity.2`, `REV.18.curiosity.1`, `2KI.18.subject.1` e `ZEP.2.subject.1`.

## Exemplos

`1JN.1.subject.1`

```text
Certa:  Deus é luz, e não há nele trevas nenhumas.
Antes:  Deus é sombra, e a luz fica fora dele.
        Deus é só lei, e a luz cabe apenas a Israel.
        Deus é fogo, e ninguém pode se aproximar.
Agora:  Deus é luz, e não há nele sombra nenhuma.
        Deus é luz, e não há nele mancha alguma.
        Deus é luz, e não há nele mudança alguma.
```

`1JN.4.subject.1`

```text
Certa:  Porque Deus é amor.
Antes:  Porque Deus é só juiz, e o amor é humano.
        Porque amar é opcional para quem crê.
        Porque o amor nasce da pessoa, não de Deus.
Agora:  Porque Deus é bondoso.
        Porque Deus é longânimo.
        Porque Deus exige amor.
```

`2CO.2.curiosity.1`

```text
Certa:  O bom perfume de Cristo, nos que se salvam e nos que se perdem.
Antes:  O cheiro do incenso, só dentro do templo.
        O odor da morte, sem notícia de vida.
        O perfume da lei, que agrada a quem a cumpre.
Agora:  O bom perfume de Cristo, só nos que se salvam.
        O bom perfume da lei, nos que se salvam e nos que se perdem.
        O bom perfume de Cristo, só nos que se perdem.
```

## Cartas alteradas por arquivo

| Arquivo | Alteradas | Cartas |
| --- | ---: | ---: |
| 1CH | 42 | 98 |
| 1CO | 38 | 58 |
| 1JN | 13 | 20 |
| 1KI | 39 | 90 |
| 1PE | 15 | 22 |
| 1SA | 26 | 123 |
| 1TH | 15 | 23 |
| 1TI | 16 | 25 |
| 2CH | 76 | 121 |
| 2CO | 25 | 48 |
| 2JN | 6 | 7 |
| 2KI | 46 | 102 |
| 2PE | 10 | 14 |
| 2SA | 41 | 97 |
| 2TH | 10 | 13 |
| 2TI | 11 | 20 |
| 3JN | 3 | 7 |
| ACT | 58 | 114 |
| AMO | 20 | 36 |
| COL | 11 | 18 |
| DAN | 21 | 51 |
| DEU | 46 | 126 |
| ECC | 30 | 36 |
| EPH | 18 | 26 |
| EST | 12 | 41 |
| EXO | 43 | 147 |
| EZK | 65 | 183 |
| EZR | 13 | 43 |
| GAL | 17 | 30 |
| GEN | 36 | 151 |
| HAB | 8 | 14 |
| HAG | 6 | 10 |
| HEB | 25 | 51 |
| HIS | 112 | 216 |
| HOS | 17 | 53 |
| ISA | 69 | 234 |
| JAS | 15 | 22 |
| JDG | 20 | 86 |
| JER | 37 | 130 |
| JHN | 46 | 82 |
| JOB | 51 | 99 |
| JOL | 7 | 14 |
| JON | 11 | 18 |
| JOS | 51 | 93 |
| JUD | 6 | 6 |
| LAM | 12 | 22 |
| LEV | 25 | 29 |
| LUK | 50 | 94 |
| MAL | 8 | 19 |
| MAT | 54 | 110 |
| MIC | 11 | 30 |
| MRK | 31 | 65 |
| NAM | 6 | 14 |
| NEH | 15 | 54 |
| NUM | 54 | 150 |
| OBA | 4 | 6 |
| PHM | 5 | 8 |
| PHP | 10 | 19 |
| PRO | 47 | 67 |
| PSA | 102 | 429 |
| REV | 44 | 89 |
| ROM | 37 | 56 |
| RUT | 12 | 18 |
| SNG | 14 | 29 |
| TIT | 8 | 13 |
| ZEC | 25 | 58 |
| ZEP | 6 | 13 |

## Validação

- Os 67 JSONs abrem e mantêm a mesma ordem de cartas.
- Nenhum campo fora de `wrongs` mudou: `id`, `bookNumber`, `chapter`, `verseStart`, `kind`, `prompt`, `answer` e `reference` estão iguais ao estado anterior.
- Todas as cartas continuam com exatamente três erradas distintas, diferentes da certa e terminadas em ponto.
- `./gradlew :domain:test` passou, incluindo as duas provas de `CardValidatorTest` que leem o baralho inteiro com `requireWrongs`.
- `content/version.txt` subiu de 21 para 22, para o app recolocar as cartas sem apagar os acertos.

## Observações

- A resposta certa nunca foi alterada. Onde o `prompt` ou a `answer` pareciam ter problema de conteúdo, o caso ficou registrado pelo subagente e não foi mexido nesta revisão. Exemplos: `HEB.4.name.1` ("Quem não deu ao povo o descanso definitivo?"), `JER.52.name.1` (Jeoiaquim em Jr 52:31) e o eco em `PRO.5.subject.1`.
- Cartas cuja `answer` é "O livro não diz o autor" mantêm erradas afirmativas. Negá-las produziria opções verdadeiras, porque o livro também não nomeia autor nenhum. O mesmo vale para respostas universais negativas, como "Ninguém consegue".
- Em alguns casos a errada é uma afirmação verdadeira em outro versículo e falsa no versículo citado. É proposital: o quiz cobra o detalhe do versículo, não uma doutrina isolada.
- O diff completo (`git diff content/`) é a transcrição integral das 1.883 cartas alteradas.
