# Cartas

Cada arquivo em `content/pt/` é um livro. O id da carta não muda quando houver outra língua. Uma tradução futura repete o mesmo id e os mesmos versículos, com outro `prompt`, `answer` e `reference`.

```json
{
  "id": "GEN.1.subject.1",
  "bookNumber": 10,
  "chapter": 1,
  "verseStart": 1,
  "kind": "subject",
  "prompt": "O que Deus fez no princípio?",
  "answer": "Criou os céus e a terra.",
  "reference": "Gn 1:1",
  "wrongs": [
    "Separou a luz das trevas.",
    "Formou o homem do pó.",
    "Pôs o sol e a lua no céu."
  ]
}
```

- `bookNumber` é o número do Graphe, não a posição 1–66.
- `kind` é `subject`, `curiosity`, `name` ou `place`.
- Todo capítulo de um livro incluído tem uma carta `subject`.
- `name`, `place` e `curiosity` só entram quando o capítulo tem uma pessoa, um lugar ou um detalhe concreto.
- Cada livro também tem curiosidade do livro inteiro: `capítulo` 0, sem versículo (`verseStart` 0) e `reference` igual ao nome longo (`Gênesis`). O id fica `GEN.0.curiosity.1`. Se o fato estiver num versículo, a referência cita esse versículo e o `chapter` continua 0, para a carta sair com o livro e não só com aquele capítulo.
- `content/pt/HIS.json` é história da igreja, doutrina e geografia bíblica, não um livro da Bíblia. `bookNumber` 0, sem capítulo nem versículo. O id fica `HIS.0.curiosity.1`. No app, essas cartas ficam no assunto História e geografia.
- A pergunta cabe no cartão: até 90 caracteres. A resposta é uma frase: até 140.
- A pergunta se entende sozinha. A frente do cartão não mostra o livro nem o capítulo. Uma pergunta geral, como a fé ou a criação, continua geral. Um detalhe de capítulo traz a pessoa, o lugar ou o livro, para valer também quando o assunto é a Bíblia inteira.
- A resposta é uma frase nossa. O app não leva o texto do versículo.
- A referência de um capítulo usa a abreviação portuguesa do Graphe e o mesmo capítulo da carta: `Gn 1:1`.
- Um fato por carta. Sem pegadinha.
- `wrongs` são três respostas erradas para o quiz. Respondem a mesma pergunta, no mesmo tamanho da certa, e só uma opção fica correta.

## Respostas erradas do quiz

A errada se parece com a certa. Quem lê as quatro opções não escolhe a certa pelo formato, pelo tamanho ou pela negação: escolhe porque sabe o fato. A errada é uma quase paráfrase da certa, com um ou dois elementos trocados.

- **Mesma forma.** Nome responde com nome de pessoa; lugar, com lugar e a mesma preposição; número, com número e a mesma unidade; frase, com frase do mesmo tipo (sujeito, verbo e complemento na mesma ordem).
- **Mesma moldura, termo trocado.** Mantenha a estrutura da certa e troque o termo que decide a resposta por outro do mesmo tipo. Não cole a certa e acrescente um "e…": isso deixa a primeira metade certa e a errada parece verdadeira. "Porque Deus é amor" vira "Porque Deus é bondoso", "Porque Deus é longânimo" ou "Porque Deus exige amor", não "Porque Deus é amor, e o amor está só nos mandamentos".
- **Mesma polaridade.** Se a certa afirma, as três erradas afirmam. Se a certa nega, as três erradas negam. A errada nunca é o contrário direto da certa (negar o que ela afirma, afirmar o que ela nega, trocar o sujeito pelo seu oposto).
- **Mesmo tamanho.** Entre 60% e 160% dos caracteres da certa. Sem opção curta de duas palavras ao lado de uma frase longa.
- **Mesmo grau de detalhe.** Se a certa cita pessoa, lugar e número, a errada também cita. Se a certa é só um nome, a errada é só um nome.
- **Erro plausível.** A errada faz sentido e soa verdadeira para quem não decorou o versículo. É o erro de quem confunde outra pessoa, outro lugar, outro número, outra ordem dos fatos ou outro versículo parecido. Nada absurdo ("Ninguém", "Nada", "Um anjo", "Deus" quando a certa é um nome) e nada que repita a certa inteira.

Exemplo:

```json
{
  "prompt": "Qual mensagem 1 João anuncia no início a respeito de Deus?",
  "answer": "Deus é luz, e não há nele trevas nenhumas.",
  "wrongs": [
    "Deus é luz, e não há nele sombra nenhuma.",
    "Deus é luz, e não há nele mancha alguma.",
    "Deus é luz, e não há nele mudança alguma."
  ]
}
```

As três mantêm "Deus é luz, e não há nele…" e trocam só o último substantivo. Nenhuma é o contrário direto ("Deus é sombra…"), nenhuma é absurda ("Deus é fogo…") e nenhuma é a certa com uma explicação colada no fim.

Outro exemplo, com resposta de ação:

```json
{
  "prompt": "O que Deus faz quando os pecados são confessados?",
  "answer": "Perdoa os pecados e purifica de toda injustiça.",
  "wrongs": [
    "Perdoa as culpas e purifica de toda injustiça.",
    "Perdoa os pecados e lava de toda injustiça.",
    "Perdoa os pecados e purifica de toda maldade."
  ]
}
```

As três repetem a certa e trocam uma palavra: o objeto, o verbo ou o complemento. Continuam plausíveis e continuam erradas para 1Jo 1:9. O que não serve: "Perdoa os pecados e promete purificar no último dia", porque a primeira metade já entrega a resposta e o resto vira explicação.


`content/version.txt` sobe quando o conjunto de cartas muda, para o app recolocar as cartas sem apagar os acertos.

Ordem dos lotes: Jonas e Gênesis, depois Êxodo–Deuteronômio, Josué–Ester, Jó–Cantares, Isaías–Malaquias, Mateus–João, Atos–Apocalipse.

## Mais adiante

Não entra no baralho agora. Uma sessão futura de língua pode tratar grego e hebraico: o alfabeto, palavras do texto e o sentido delas. Fica só registrada para não se perder.
