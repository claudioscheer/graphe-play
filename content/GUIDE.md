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
- `content/pt/HIS.json` é a história da igreja, não um livro da Bíblia. `bookNumber` 0, sem capítulo nem versículo. O id fica `HIS.0.curiosity.1`. No app, essas cartas ficam no assunto História.
- A pergunta cabe no cartão: até 90 caracteres. A resposta é uma frase: até 140.
- A pergunta se entende sozinha. A frente do cartão não mostra o livro nem o capítulo. Uma pergunta geral, como a fé ou a criação, continua geral. Um detalhe de capítulo traz a pessoa, o lugar ou o livro, para valer também quando o assunto é a Bíblia inteira.
- A resposta é uma frase nossa. O app não leva o texto do versículo.
- A referência de um capítulo usa a abreviação portuguesa do Graphe e o mesmo capítulo da carta: `Gn 1:1`.
- Um fato por carta. Sem pegadinha.
- `wrongs` são três respostas erradas para o quiz. Respondem a mesma pergunta, no mesmo tamanho da certa, e só uma opção fica correta.

`content/version.txt` sobe quando o conjunto de cartas muda, para o app recolocar as cartas sem apagar os acertos.

Ordem dos lotes: Jonas e Gênesis, depois Êxodo–Deuteronômio, Josué–Ester, Jó–Cantares, Isaías–Malaquias, Mateus–João, Atos–Apocalipse.
