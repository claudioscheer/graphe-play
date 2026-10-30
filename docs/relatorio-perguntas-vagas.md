# Relatório de perguntas vagas — coleção de cartas (PT)

Auditoria completa de `content/pt` (67 arquivos, **4.410 cartas**), linha por linha. O critério vem do próprio `content/GUIDE.md`: a frente do cartão não mostra livro nem capítulo, e o usuário responde perguntas de toda a Bíblia misturadas; logo, a pergunta deve identificar o contexto (livro, pessoa, lugar ou ocasião) ou ser uma doutrina geral de verdade, com resposta única na Escritura. Pergunta que só tem uma resposta certa para quem decorou de qual capítulo ela vem está vaga.

**Estado examinado:** snapshot congelado do diretório em 29/10/2026, às 21h05, conferido carta a carta contra os JSONs.

> **Importante — o conteúdo mudou durante a auditoria.** Outras sessões do OpenCode estavam editando os JSONs em paralelo (228 prompts e 986 respostas mudaram entre o início e o fim dos lotes; várias reescritas acrescentaram âncoras como "segundo Paulo" e "em Isaías"). Este relatório vale para o estado congelado acima; antes de corrigir cada carta, confira o `prompt` atual, pois parte pode ter sido reescrita entretanto.

## Resumo

| Recorte | Cartas |
| --- | --- |
| Revisadas (todos os livros, sem amostragem) | 4.410 |
| **Vagas de fato** — sem âncora identificável; prioridade de correção | **31** |
| **Duvidosas** — têm âncora parcial, mas o fato se repete em outra obra e a resposta esperada não é selecionável com certeza | **275** |
| **Total a revisar** | 306 |

Livros com mais cartas a revisar:

| Livro | Cartas |
| --- | --- |
| Provérbios | 46 |
| Salmos | 43 |
| Oséias | 24 |
| Eclesiastes | 20 |
| Êxodo | 10 |
| Jeremias | 10 |
| Zacarias | 10 |
| Marcos | 9 |
| Mateus | 8 |
| Lucas | 8 |
| Números | 7 |
| Deuteronômio | 7 |

## Problemas típicos, com exemplo real do baralho

1. **Afirmação universal sem âncora** — `2CO.13.subject.1` "O que Paulo diz que a igreja deve examinar em si?". "A igreja" e "segundo Paulo" não apontam uma ocorrência única; a resposta (2Co 13:5) só é possível para quem sabe de qual capítulo o card vem. A Bíblia fala em exame de si em vários lugares (1Co 10:12, 1Co 11:28, Gl 6:4, Ap 2–3).
2. **Ato que se repete entre livros** — `LUK.10.subject.1` "Quantos discípulos Jesus enviou de dois em dois?". Tanto os doze (Mc 6:7) quanto os setenta (Lc 10:1) foram assim; o card treina "setenta" sem dizer de qual envio fala.
3. **Papel sem nome** — `NEH.2.curiosity.1` "O que o rei decidiu sobre a viagem de Neemias?". Há vários reis persas nos arredores (Esdras 7, Ester 10); basta nomear Artaxerxes.
4. **Frase literal do versículo sem âncora** — `PRO.31.curiosity.1` "O que é enganoso e vão, ao contrário de temer ao Senhor?". Só fecha para quem tem o texto de Pv 31:30 na cabeça; a frente não indica nem o livro.
5. **Pergunta duplicada entre evangelhos com respostas diferentes** — `MAT.9.name.1`, `MRK.2.name.1` e `LUK.5.name.1` perguntam o mesmo chamado do cobrador de impostos, e cada uma espera um nome diferente (Mateus, Levi, Levi), sem indicar o livro.

## Como corrigir (padrões que o baralho já usa e funcionam)

- **Citar o livro:** "em Provérbios", "na carta aos Efésios", "nas visões de Ezequiel", "no Salmo 23".
- **Citar a voz do autor:** "segundo Paulo", "segundo Tiago", "segundo Miquéias", "no cântico de Débora". Vale quando não há outro texto da mesma voz treinando fato rival.
- **Nomear a persona e a ocasião:** "Eliseu e a sunamita", "Davi quando fugiu de Absalão", "na parábola do semeador", "no sermão do monte", "no Getsêmani".
- **Fato único e famoso dispensa âncora:** "O que Deus mandou Noé construir?" está certo (a arca é única). Antes de deixar uma pergunta geral, confira se há card-irmão no baralho disputando a mesma resposta.

## A. Perguntas vagas — revisar prioritariamente (31)

O leitor não consegue chegar a UMA resposta com o que está na frente do cartão.

### Deuteronômio · 4

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `DEU.8.curiosity.2` | Dt 8:15 | De que tipo de rocha Deus tirou água para Israel no deserto? | Pergunta com cara de episódio comum da água da rocha (Êx 17; 1Co 10:4), mas o tipo 'pederneira' só existe neste versículo e não se deduz sem a âncora do livro. | citar 'em Deuteronômio' (a lembrança que Moisés faz da viagem no deserto) |
| `DEU.9.name.1` | Dt 9:20 | Por quem mais Moisés orou no caso do bezerro de ouro? | O 'mais' pressupõe contexto não mostrado e, no episódio famoso do bezerro (Êx 32), Moisés orou pelo povo — a resposta 'Arão' é um detalhe de Dt 9:20. | citar 'em Deuteronômio, no discurso de Moisés' e formular 'além de orar pelo povo, por quem mais?' |
| `DEU.10.subject.1` | Dt 10:12 | O que Moisés disse que Deus pede de Israel? | Sem âncora de livro, o que Deus 'pede de Israel' lembra também Dt 6:5 e Mq 6:8, com respostas diferentes da lista esperada. | citar 'em Deuteronômio' (o chamado de Moisés a Israel) |
| `DEU.18.subject.1` | Dt 18:15 | Quem Deus prometeu suscitar como Moisés? | Sem âncora, muitos responderiam Jesus, pois At 3:22/7:37 citam este versículo como promessa messiânica, e nada indica o discurso de Moisés. | citar 'em Deuteronômio' (as palavras de Moisés antes de entrar em Canaã) |

### Juízes · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `JDG.7.name.1` | Jz 7:25 | Quais chefes de Midiã foram capturados e mortos? | Há dois pares de chefes midianitas capturados e mortos (Orebe e Zeebe em Jz 7:25; Zeba e Zalmuna em Jz 8): a pergunta não indica a ocasião. | Indicar a ocasião (“na perseguição, à travessia do Jordão” ou “as duas cabeças que Gideão matou no fim”) ou o livro. |
| `JDG.12.name.1` | Jz 12:7 | Quem julgou Israel e foi sepultado em Gileade? | Dois juízes foram sepultados em Gileade: Jair (em Camom) e Jefté (nas cidades de Gileade); sem nome nem âncora há duas respostas. | Nomear a personagem ou ancorar o fato (“o juiz que ergueu a palavra Chibolete”), por exemplo “Quem julgou Israel vinte e três anos...”. |

### 1 Reis · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `1KI.4.subject.1` | 1Rs 4:29 | O que Deus deu a Salomão? | Deus deu a Salomão vários dons documentados (sabedoria, riqueza, glória, um coração sábio) e a pergunta sem ocasião não aponta qual . | Indicar a ocasião: 'o que Deus deu a Salomão para julgar o povo?' ou remeter ao pedido em Gibeão. |
| `1KI.5.curiosity.1` | 1Rs 5:15 | Quantos levavam cargas e quantos talhavam pedra? | Nenhum nome de pessoa ou ocasião na frente indica o canteiro do templo; o mesmo quantitativo aparece em 2Cr 2:18. | a força de trabalho que Salomão preparou para o templo (1 Reis 5:15) |

### 2 Reis · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `2KI.15.place.1` | 2Rs 15:29 | Para onde Tiglate-Pileser levou o povo que prendeu? | 'O povo que prendeu' não diz de qual povo (ele deportou os israelitas de Gileade e Galileia para a Assíria e os moradores de Damasco para Quir), então a resposta é ambígua. | Especificar o povo: 'Para onde Tiglate-Pileser levou o povo de Gileade, Naftali e Galileia?' |
| `2KI.8.name.1` | 2Rs 8:16 | Quem passou a reinar em Judá no quinto ano de Jorão? | Há dois reis chamados Jorão (um de Israel, filho de Acabe; outro de Judá, filho de Josafá) e a fórmula de calendário de reinado não diz de qual Jorão se trata. | Desambiguar: 'no quinto ano de Jorão de Israel, filho de Acabe' ou citar o livro. |

### Salmos · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `PSA.39.name.1` | Sl 39:1 | Conforme qual músico Davi promete vigiar a língua? | A resposta ('Jedutum') vem do sobretítulo do Sl 39, e Jedutum também é o dedicatário do Sl 62; sem o número, o usuário não chega a uma resposta única. | 'Conforme o sobretítulo do Salmo 39, Davi promete...' (ou citar 'no Salmo 39'). |

### Provérbios · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `PRO.22.subject.1` | Pv 22:1 | O que vale mais do que muita riqueza? | Sem âncora e com resposta não única: "vale mais do que riquezas" encaixa a sabedoria (Pv 8), o bom nome (Pv 22:1) e outros ditos, mas a carta espera só um. | Ancorar com o versículo: "Segundo Provérbios 22:1, o que vale mais do que muita riqueza?". |
| `PRO.23.subject.1` | Pv 23:5 | O que acontece se alguém não tira o olho do que não vale nada? | Enunciado confuso e sem âncora: "o que não vale nada" não nomeia as riquezas, o capítulo nem o livro; impossível saber que responde Pv 23:5. | Reformular com âncora: "Em Provérbios 23, o que acontece com as riquezas se alguém não tira o olho delas?". |

### Eclesiastes · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `ECC.3.curiosity.1` | Ec 3:11 | O que Deus pôs no coração humano? | Sem âncora: "Deus pôr algo no coração" repete na Bíblia (Rm 5:5; Jr 31:33; 2Co 1:22), então não se sabe que a resposta é a de Ec 3:11. | Ancorar: "O que Deus pôs no coração humano, em Eclesiastes 3?". |

### Jeremias · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `JER.26.subject.1` | Jr 26:16 | O que os príncipes e o povo decidiram sobre Jeremias? | Os príncipes e o povo decidem o contrário em dois episódios de Jeremias (absolvem-no em Jr 26:16, pedem sua morte em Jr 38:4); sem a ocasião não há resposta única. | Incorporar a ocasião: 'escutando o sermão no pátio do templo (Jr 26)' ou dizer 'quando ele ameaçou com juízo quem não se convertesse'. |

### Ezequiel · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `EZK.15.subject.1` | Ez 15:6 | A que Deus comparou os moradores de Jerusalém? | Sem âncora de livro, 'os moradores de Jerusalém' admite várias comparações de Ezequiel (videira inútil em Ez 15, escória de prata em Ez 22, recém-nascida abandonada em Ez 16) e de outros profetas, com respostas diferentes. | Citar o livro e a ocasião: 'em Ezequiel, no sermão da videira inútil, a que ele comparou os moradores de Jerusalém?'. |

### Oséias · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `HOS.6.subject.1` | Os 6:6 | O que Deus pede a Israel mais que oferta? | Há respostas plausíveis distintas sem âncora: "misericórdia e conhecimento" (Os 6:6) conflita com "justiça, bondade e humildade" (Mq 6:8, que está nesta mesma baralho) e com 1Sm 15:22. | Citar o livro: "em Oséias, capítulo 6". |

### Naum · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `NAM.3.subject.1` | Na 3:7 | O que dirão de Nínive ao vê-la caída? | Duas passagens dão respostas diferentes sobre a queda de Nínive: Nn 3:7 (destruída e sem consolo) e Sf 2:15 (escárnio e balançar de cabeça), e sem âncora não se decide. | Citar o livro: "em Naum". |

### Zacarias · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `ZEC.8.place.1` | Zc 8:3 | Que nomes receberão Jerusalém e o monte de Deus? | Há duas nomeações diferentes de Jerusalém com nomes distintos: "cidade fiel" (Is 1:26) e "cidade da verdade" (Zc 8:3), e sem âncora não se decide. | Citar o livro: "em Zacarias". |

### Mateus · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `MAT.9.name.1` | Mt 9:9 | Quem Jesus chamou na coletoria de impostos? | O cobrador chamado por Jesus tem nomes diferentes por evangelho: Mateus em Mt 9:9 e Levi em Mc 2:14/Lc 5:27; sem âncora de livro há duas respostas válidas. | Citar 'em Mateus' ou dizer 'o cobrador que passou a ser apóstolo'. |

### Marcos · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `MRK.2.name.1` | Mc 2:14 | Quem Jesus chamou no posto de impostos? | O cobrador chamado por Jesus tem nomes diferentes por evangelho (Levi em Marcos, Mateus em Mt 9:9); sem âncora de livro há duas respostas válidas. | Citar 'em Marcos' ou dizer 'o cobrador chamado por Marcos'. |
| `MRK.15.curiosity.1` | Mc 15:25 | Em que hora crucificaram Jesus? | Sem âncora de livro, a pergunta tem duas respostas na Escritura: hora terceira em Marcos e cerca da hora sexta em João 19:14. | Citar 'em Marcos'. |

### Lucas · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `LUK.5.name.1` | Lc 5:27 | Quem Jesus chamou na banca de impostos? | O cobrador chamado por Jesus tem nomes diferentes por evangelho (Levi em Lucas, Mateus em Mt 9:9); sem âncora de livro há duas respostas válidas. | Citar 'em Lucas' ou 'o cobrador chamado por Lucas'. |
| `LUK.10.subject.1` | Lc 10:1 | Quantos discípulos Jesus enviou de dois em dois? | Tanto os doze (Mc 6:7) quanto os setenta (Lc 10:1) foram enviados de dois em dois; sem âncora, há duas respostas válidas na Escritura. | Dizer 'no relato dos setenta discípulos' ou citar 'em Lucas'. |

### Romanos · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `ROM.9.curiosity.1` | Rm 9:12 | Qual filho serviria o outro, na palavra citada por Paulo? | A pergunta não nomeia os filhos nem o livro; colide com outros casos de 'o menor servirá o maior' (Gn 48:19, Efraim/Manassés). | Citar a passagem ('em Romanos 9') ou nomear Jacó e Esaú. |

### 1 Coríntios · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `1CO.5.curiosity.1` | 1Co 5:6 | O que um pouco de fermento faz na massa, segundo Paulo? | A mesma pergunta/resposta aparece sem âncora em Mt 13:33, Lc 13:21 e Gl 5:9; não se sabe a qual ocasião se refere. / Colide com GAL.5.curiosity.3 (Gl 5:9): mesmo fato, mesma cara 'segundo Paulo', sem âncora que distinga as irmãs. | Citar a carta ('em 1 Coríntios') ou o contexto ('no aviso sobre o mal na igreja'). ; em 1 Coríntios (5:6, a imoralidade da igreja) |

### 2 Coríntios · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `2CO.13.subject.1` | 2Co 13:5 | O que Paulo diz que a igreja deve examinar em si? | 'A igreja' sem citar a carta soa como doutrina geral, mas a resposta só se conecta a 2Co 13:5. | Citar a carta ('a igreja de Corinto, na segunda carta') ou Tito/Paulo como ocasião. |

### Gálatas · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `GAL.2.curiosity.1` | Gl 2:1 | Depois de quanto tempo Paulo subiu outra vez a Jerusalém? | 'Outra vez' não define a visita; Paulo subiu a Jerusalém muitas vezes, e a própria carta cita outra visita 'depois de três anos' (Gl 1:18). | Citar Gálatas 2 ou precisar a ocasião ('catorze anos depois da primeira visita'). |
| `GAL.5.curiosity.3` | Gl 5:9 | O que um pouco de fermento faz com a massa, segundo Paulo? | Réplica exata do cartão de 1Co 5:6 e do provérbio dos evangelhos (Mt 13:33; Lc 13:21), sem âncora do livro. / Colide com 1CO.5.curiosity.1 (1Co 5:6); as duas frentes são praticamente idênticas. | Citar a carta ('em Gálatas') ou o contexto ('no aviso contra o ensino falsificado'). ; em Gálatas (5:9, a falsa doutrina dos judaizantes) |

### Hebreus · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `HEB.12.curiosity.1` | Hb 12:20 | O que devia ocorrer com o animal que tocasse o monte? | Referências soltas: "o monte" e "o animal" sem nomear Sinai; no mesmo capítulo o monte celestial é para se chegar, o que confunde. | Nomear o monte: "No Sinai, o que acontecia com o animal que tocasse o monte?" |

### Apocalipse · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `REV.19.name.1` | Ap 19:13 | Qual é o nome de quem vem com a roupa manchada de sangue, no Apocalipse? | O mesmo cavaleiro tem dois nomes próximos (Fiel e Verdadeiro em 19:11 e Palavra de Deus em 19:13); o usuário frio não separa os dois cartões. | Garantir que o cartão peça explicitamente: "seu nome em Apocalipse 19:13" e não o de 19:11 |

## B. Perguntas duvidosas — revisar uma a uma (275)

Ficam entre a correção e o "válido": têm um ponteiro parcial (uma pessoa ou lugar citado), mas o fato perguntado se repete na obra ou no baralho. Revisar uma a uma: se a resposta esperada for única mesmo sem o ponteiro que falta, não mexa; caso contrário, incorpore o contexto sugerido.

### Gênesis · 4

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `GEN.1.curiosity.1` | Gn 1:31 | Como Deus viu tudo o que tinha feito? | Sem âncora de livro ou ocasião, 'tudo o que tinha feito' pode parecer geral, e o estranho 'Como' não deixa claro que se pede o veredito final sobre a criação. | Ancorar a criação/livro: 'Ao fim da criação, em Gênesis, como Deus viu tudo o que tinha feito?' |
| `GEN.5.subject.1` | Gn 5:1 | De quem começa a lista que chega até Noé? | Referência solta 'a lista': a frente não diz que é a genealogia do livro de Gênesis nem quais os itens listados. | Nomear a ocasião: 'Em Gênesis, a genealogia que chega até Noé começa de quem?' |
| `GEN.26.subject.1` | Gn 26:3 | O que Deus confirmou a Isaque? | Fato não ancorado: Deus falou a Isaque mais de uma vez e 'confirmou' não indica qual promessa/alvança, nem cita o livro. | Citar Gênesis ou explicitar o objeto: 'Que promessa Deus confirmou a Isaque em Gênesis?' |
| `GEN.30.curiosity.1` | Gn 30:22 | O que Deus fez por Raquel, que ainda não tinha filho? | 'O que Deus fez por...' é genérico e se repete na história de qualquer personagem; a cláusula da esterilidade não bastaria sem o contexto de Jacó/Labão. | Apresentar a ocasião: 'O que Deus fez pela estéril Raquel, mulher de Jacó, em Gênesis?' |

### Êxodo · 10

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `EXO.1.curiosity.1` | Êx 1:22 | O que Faraó mandou fazer com todo menino recém-nascido? | Sem dizer 'hebreu' nem citar Êxodo/Egito, a ordem se confunde com outros editos contra meninos (ex.: Herodes em Mateus 2), e a resposta ('lançá-lo no rio') não é dedutível. | Ancorar: 'No Egito, em Êxodo, o que Faraó mandou fazer com todo menino hebreu recém-nascido?' |
| `EXO.4.place.1` | Êx 4:27 | Onde Arão encontrou Moisés? | Encontro entre os dois é episódio menor e não indica a ocasião (a volta de Moisés ao Egito); 'encontrou' sem âncora de livro não se resolve com segurança. | Citar Êxodo ou a ocasião: 'Quando Arão saiu para encontrar Moisés de volta ao Egito, onde se encontraram?' |
| `EXO.5.subject.1` | Êx 5:2 | Por que Faraó recusou deixar Israel sair? | Ato que se repete: Faraó recusou em cada praga com razões variadas; a pergunta não indica que é o primeiro encontro. | Ancorar a ocasião: 'No primeiro encontro de Faraó com Moisés e Arão, por que ele recusou deixar Israel sair?' |
| `EXO.7.curiosity.1` | Êx 7:7 | Que idade tinham Moisés e Arão ao falar com Faraó? | 'Ao falar com Faraó' não define a ocasião (falaram muitas vezes); sem âncora do livro, a idade exata não é dedutível. | Ancorar: 'Em Êxodo, que idade tinham Moisés e Arão quando começaram a falar com Faraó?' |
| `EXO.14.place.1` | Êx 14:2 | Onde Israel acampou junto ao mar? | Sem âncora do livro ou do mar Vermelho, 'junto ao mar' não delimita a ocasião; a resposta exige nomes obscuros (Pi-Hairote, Migdol, Baal-Zefom) não dedutíveis da frente. | Ancorar: 'Na saída do Egito, em Êxodo, onde Israel acampou junto ao mar Vermelho?' |
| `EXO.16.subject.1` | Êx 16:4 | O que Deus prometeu fazer chover para Israel? | Ato/promessa que se repete sem âncora: Deus prometeu chuva a Israel em outros livros (ex.: Deuteronômio 11); nada na frente aponta o maná do deserto. | Ancorar o deserto/Êxodo: 'No deserto, em Êxodo, o que Deus prometeu fazer chover do céu para Israel?' |
| `EXO.18.place.1` | Êx 18:5 | Onde Jetro encontrou Moisés? | Detalhe menor sem ocasião indicada (a visita de Jetro com Zípora) nem âncora de livro; a resposta ('no deserto, junto ao monte de Deus') não é dedutível. | Ancorar a ocasião: 'Quando Jetro trouxe a Zípora aos filhos no deserto, onde encontrou Moisés?' |
| `EXO.19.curiosity.1` | Êx 19:6 | O que Deus disse que Israel seria para ele? | Pergunta de cara geral ('o que Deus disse que Israel seria') que só fecha no versículo específico; a mesma fórmula volta em Deuteronômio e é citada de 1 Pedro 2:9. | Ancorar o Sinai: 'No Sinai, o que Deus disse que Israel seria para ele: reino de sacerdotes e...?' |
| `EXO.32.name.1` | Êx 32:26 | Quem se juntou a Moisés à porta do acampamento? | Sem âncora do episódio do bezerro de ouro nem do livro, 'se juntou a Moisés à porta do acampamento' não identifica a ocasião (cenário que se repete em Êxodo e Números). | Ancorar o episódio: 'Depois do bezerro de ouro, em Êxodo, quem se juntou a Moisés à porta do acampamento?' |
| `EXO.39.subject.1` | Êx 39:43 | O que Moisés fez ao ver a obra terminada? | 'Fez' é genérico e 'a obra terminada' não diz qual obra; sem âncora do tabernáculo/Êxodo a ocasião não se identifica. | Ancorar: 'Ao ver terminada a obra do tabernáculo, em Êxodo, o que Moisés fez?' |

### Levítico · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `LEV.8.subject.1` | Lv 8:30 | Quem Moisés santificou com azeite e sangue? | Sem âncora de livro ou ocasião — é a única carta do bloco de Levítico sem 'em Levítico' — e chegar a Arão e aos filhos dele exige lembrar da única ordenação sacerdotal. | citar 'em Levítico' ou apresentar a ocasião: a ordenação de Arão como sacerdote |

### Números · 7

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `NUM.1.place.1` | Nm 1:19 | Onde Moisés e Arão contaram o povo de Israel? | Israel foi contado duas vezes, no Sinai (Nm 1) e em Moabe (Nm 26), e a pergunta sem âncora admite dois lugares diferentes. | citar 'em Números' e indicar a primeira contagem, no deserto do Sinai |
| `NUM.3.subject.1` | Nm 3:12 | Por que Deus separou os levitas em Israel? | Sem âncora, a pergunta aceita respostas bíblicas distintas: para ficarem no lugar dos primogênitos (Nm 3:12) ou para servirem na tenda (Nm 8:19; Dt 10:8). | citar 'em Números' e o contexto: o levita substitui todos os primogênitos de Israel |
| `NUM.7.curiosity.2` | Nm 7:89 | De onde Moisés ouvia a voz dentro da tenda? | 'Moisés ouvir a voz na tenda' também evoca Êx 33:9 (voz junto à entrada da tenda fora do acampamento); sem âncora não se resolve o detalhe 'entre os querubins'. | citar 'em Números', na dedicação do tabernáculo, com a arca e os querubins |
| `NUM.10.name.1` | Nm 10:29 | Quem Moisés convidou para ir com Israel? | Sem âncora de livro, lugar ou ocasião, nada indica que se trata do convite a Hobabe ao sair do Sinai, fato único mas pouco notável. | citar 'em Números' e a ocasião da partida do Sinai (o parente midianita de Moisés) |
| `NUM.20.place.1` | Nm 20:27-28 | Onde Arão morreu, à vista da congregação? | A morte de Arão traz dois lugares (monte Hor em Nm 20:28; Moserá em Dt 10:6) e, sem âncora de livro, a resposta pode divergir. | citar 'em Números' ou 'no monte Hor' para desempatar de Dt 10:6 |
| `NUM.33.curiosity.3` | Nm 33:55 | O que Moisés disse que os moradores deixados em Canaã seriam? | Sem âncora, o leitor pode lembrar de Josué 23:13, onde a mesma advertência sobre os habitantes de Canaã é repetida com imagens diferentes. | citar 'em Números' (a mensagem de Moisés na lista das marchas) |
| `NUM.36.place.1` | Nm 36:13 | Onde Moisés deu a Israel a lei sobre as herdeiras? | O caso das herdeiras aparece em duas ocasições de Números (Nm 27, à entrada da tenda; Nm 36, em Moabe) e a pergunta sem âncora admite as duas. | citar 'em Números' e a ocasião: a decisão sobre o casamento das herdeiras, nas campinas de Moabe |

### Deuteronômio · 3

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `DEU.3.place.1` | Dt 3:27 | De que cume Moisés veria a terra, após Deus negar a travessia? | Contemplar a terra se repete em três cumes (Pisga em Dt 3:27; Abarim em Nm 27:12; Nebo em Dt 32:49), e sem âncora de livro a resposta pode ser Nebo. | citar 'em Deuteronômio' e o contexto (após a derrota de Seom e Ogue) |
| `DEU.6.place.1` | Dt 6:16 | Onde Israel tentou a Deus? | 'Tentar a Deus' se repete no deserto (Sl 78; Sl 95 'Massá e Meribá') e, sem âncora, pode-se responder outro episódio ou par de lugares. | citar 'em Deuteronômio' (a advertência de Moisés ao povo) |
| `DEU.28.subject.1` | Dt 28:1 | O que Deus prometeu antes da lista de bênçãos, se Israel obedecesse? | Promessas semelhantes de Deus exaltar o povo aparecem em Lv 26:13 e Dt 26:19, e 'a lista de bênçãos' sem âncora de livro não resolve unicamente para Dt 28:1. | citar 'em Deuteronômio', na abertura do capítulo das bênçãos e maldições |

### Josué · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `JOS.22.name.1` | Js 22:13 | Quem liderou a delegação enviada a Gileade? | “Delegação enviada a Gileade” não indica o livro nem a ocasião; sem a disputa do altar, o grupo e o líder não são reconhecíveis. | Citar “em Josué” ou indicar a ocasião (“depois de Rúben e Gade armarem altar junto ao Jordão”). |

### Juízes · 3

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `JDG.2.subject.1` | Jz 2:16 | Quem Deus levantava para salvar Israel dos saqueadores? | A resposta é a papel genérico “juízes”, sem âncora do livro nem “no tempo dos juízes”; a pergunta não indica a era. | Ancorar (“em Juízes” ou “no ciclo na terra de Israel”) para o papel “juízes” se entender sozinho. |
| `JDG.17.place.1` | Jz 17:1 | Em que região morava Mica? | “Mica” sem âncora do livro pode remeter ao profeta Mica, ao escrivão em 2Rs 22 ou ao dono da imagem em Juízes; a ocasião não se identifica. | Citar “em Juízes” ou apresentar a personagem (“o dono da prata e da imagem no monte de Efraim”). |
| `JDG.19.place.1` | Jz 19:14 | Em qual cidade de Benjamim o sol se pôs para o levita? | “O levita” vem sem nome nem âncora do livro; só com a história da concubina é possível saber que é o levita de Efraim em Gibeá. | Apresentar a ocasião (“o levita que voltava com a concubina, em Juízes”) ou citar o livro. |

### 1 Samuel · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `1SA.11.place.1` | 1Sm 11:15 | Onde o povo confirmou Saul como rei? | Rei foi proclamado em Mispá (1Sm 10:24) e confirmado em Gilgal (1Sm 11:15); sem a ocasião, há dois lugares candidatos. | Indicar a ocasião (“depois da vitória sobre Naás” ou “na reconfirmação em Sichel?”/Gilgal) para fechar em um lugar. |
| `1SA.13.subject.1` | 1Sm 13:13-14 | O que Samuel anunciou sobre o reino de Saul? | Samuel anunciou o fim do reino duas vezes (1Sm 13:13-14 e 1Sm 15:23-28) e a pergunta não indica qual ocasião. | Indicar a ocasião (“depois de Saul oferecer o holocausto sozinho”) para distinguir do anúncio após Amaleque. |

### 2 Samuel · 4

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `2SA.10.curiosity.1` | 2Sm 10:18 | Quantos cavaleiros sírios Davi feriu? | Davi lutou contra sírios em pelo menos duas campanhas (2Sm 8 e 2Sm 10, com outros números) e a pergunta não indica qual batalha. | Ancorar a ocasião: '...na batalha em que Davi venceu o general Sobaque' ou citar a segunda campanha contra Hadadezer. |
| `2SA.23.subject.1` | 2Sm 23:5 | Que acordo Davi disse que Deus fez com ele? | 'Que acordo Davi disse que Deus fez com ele?' sem âncora de livro: combina com o pacto davídico (2Sm 7) e pode confundir com a aliança em 2Cr 21:7. | Acrescentar 'em 2 Samuel' ou 'nas últimas palavras de Davi', como fazem os cards irmãos.. (varredura de segurança) |
| `2SA.24.curiosity.1` | 2Sm 24:24 | Quanto prata Davi pagou pelo terreno e pelos animais? | Entre livros os números divergem (1Cr 21:25 diz seiscentos siclos de ouro), e sem citar o livro o leitor não sabe qual versículo seguir. | Citar o livro: 'em 2 Samuel' ou explicitar o censo de Araúna como ocasião. |
| `2SA.8.place.1` | 2Sm 8:13 | Onde Davi feriu dezoito mil homens? | Davi venceu em várias campanhas e o número dezoito mil também aparece em 1 Crônicas (por Abisai); sem citar edomitas nem ocasião, a batalha não se identifica sozinha. | Incorporar o alvo e a ocasião: 'Onde Davi feriu dezoito mil edomitas na guerra contra eles?' |

### 1 Reis · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `1KI.19.subject.1` | 1Rs 19:12 | Como Deus falou a Elias depois do fogo? | 'Depois do fogo' não nomeia o Horebe nem o episódio; em baralho misturado pode soar como o fogo do Carmelo ou da sequência do vento e do terremoto. | Ancorar o local/episódio: 'Como Deus falou a Elias no monte Horebe?' |
| `1KI.4.curiosity.1` | 1Rs 4:26 | Quantas estrebarias de cavalos Salomão tinha para os carros? | O mesmo dado vem com números diferentes em 1Rs 4:26 (quarenta mil) e 2Cr 9:25 (quatro mil), e a frente não indica o livro. | em 1 Reis (4:26) |

### 2 Reis · 3

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `2KI.10.curiosity.1` | 2Rs 10:7 | O que puseram em cestos e mandaram a Jizreel? | A pergunta não diz de quem eram nem quem mandou; sem mencionar a casa de Acabe ou Jeú, a cena das cabeças não se ancora. | Ancorar: 'O que os chefes de Samaria mandaram a Jeú em cestos, na vingança sobre a casa de Acabe?' |
| `2KI.17.name.1` | 2Rs 17:1 | Quem era o rei de Israel, filho de Elá? | 1–2 Reis listam dezenas de reis de Israel; 'filho de Elá' é confundível com Elã, filho de Baasa (que também foi rei), e não basta para identificar sem o livro. | Ancorar com o livro/ocasião: 'Quem era o último rei de Israel, filho de Elá, no início de 2 Reis 17?' |
| `2KI.17.subject.1` | 2Rs 17:6 | O que o rei da Assíria fez com Samaria? | 'O rei da Assíria' aparece sem nome (Salmane só é citado antes) e 2 Reis registra dois feitos dele com Samaria: tomar a cidade e exilar Israel versus pôs estrangeiros para povoá-la. | Nomear o rei e a ocasião: 'O que Salmane, rei da Assíria, fez com Samaria na queda de Israel?' |

### 1 Crônicas · 3

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `1CH.23.subject.1` | 1Cr 23:6 | De quem eram os turnos dos contados de trinta anos para cima? | Sem âncora, 'os contados de trinta anos para cima' pode ser o registro dos levitas no deserto (Nm 4) ou o censo de Davi (1Cr 23), e a resposta vale para os dois, sem apontar a ocasião. | Citar 1 Crônicas ou nomear Davi ('os contados por Davi, de trinta anos para cima'). |
| `1CH.7.place.1` | 1Cr 7:28-29 | Onde habitaram os filhos de José? | Sem citar livro, a resposta não se resolve de forma única: pode-se responder região de Efraim/Manassés, Siquém ou Betel, e há outros textos sobre onde os filhos de José se assentaram (Js 16-17). | Citar 1 Crônicas (ex.: 'conforme a lista de Crônicas' ou 'em 1 Crônicas') ao perguntar onde os filhos de José habitaram. |
| `1CH.8.place.1` | 1Cr 8:28 | Onde moravam os chefes de Benjamim na lista que começa com Belá? | Em Crônicas há duas listas de Benjamim que começam com Belá (1Cr 7:6 e 1Cr 8:1) e, sem âncora do livro, o leitor não sabe a qual se refere nem é levado a Jerusalém com segurança. | Citar 1 Crônicas ou apresentar a ocasião (os chefes de Benjamim voltados do exílio que se estabeleceram em Jerusalém). |

### 2 Crônicas · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `2CH.2.subject.1` | 2Cr 2:1 | O que Salomão decidiu construir? | Todos lembram 'o templo', mas a resposta inclui duas construções (a casa de Deus e o palácio real) que a pergunta sozinha não leva a prever. | Incluir a ocasião/ambos os itens (ex.: 'O que Salomão decidiu construir para Deus e para si, antes da obra?') ou limitar a pergunta à casa de Deus. |
| `2CH.6.subject.1` | 2Cr 6:21 | O que Salomão pediu quando o povo orasse no templo? | A oração de dedicação tem vários pedidos sobre situações diferentes (2Cr 6:22-39); sem indicar qual pedido, a resposta não se resolve em uma só (ex.: juízo, derrota, seca, exílio). | Especificar a ocasião do pedido (ex.: 'quando o povo pecasse') ou reformular em torno do refrão 'ouvir do céu e perdoar' em 2Cr 6:21. |

### Neemias · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `NEH.2.curiosity.1` | Ne 2:6 | O que o rei decidiu sobre a viagem de Neemias? | 'O rei' aparece sem nome e, entre os reis persas dos pós-exílio (Ciro, Dario, Xerxes, Artaxerxes), quem lê só a pergunta não sabe a qual se refere, embora a resposta dependa disso. | Nomear o rei Artaxerxes (ex.: 'O que o rei Artaxerxes decidiu sobre a viagem de Neemias?'). |

### Salmos · 42

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `PSA.3.curiosity.1` | Sl 3:6 | Quantas pessoas ao redor Davi diz que não temeria? | O tema 'não temer a multidão' se repete em outro salmo de Davi (Sl 27:1-3), e a carta não cita o número do salmo. | Citar o número: 'no Salmo 3'. |
| `PSA.3.subject.1` | Sl 3:7 | O que Davi diz que aconteceu aos dentes dos maus? | Sem o número do salmo, 'Davi' não resolve a ocasião: o motivo de Deus quebrar os dentes dos maus se repete no Sl 58:6, também de Davi. | Citar o número: 'no Salmo 3'. |
| `PSA.4.curiosity.1` | Sl 4:6 | O que Davi pede que Deus levante sobre o povo? | O pedido de Deus 'levantar a luz do rosto' se repete na história de Davi (Sl 31:16; tb. 67:1; 80:3), e a carta não cita o número do salmo. | Citar o número: 'no Salmo 4'. |
| `PSA.4.subject.1` | Sl 4:7 | A alegria de Davi é maior do que qual fartura? | Detalhe de capítulo sem âncora: 'Davi' sozinho não indica o salmo, e a comparação é específica do Sl 4:7. | Citar o número: 'no Salmo 4'. |
| `PSA.4.curiosity.2` | Sl 4:8 | Por que Davi pode se deitar e dormir em paz? | Dormir tranquilo aprendendo de Deus já aparece no Sl 3:5, também de Davi; sem o número o usuário não distingue o salmo 3 do 4. | Citar o número: 'no Salmo 4'. |
| `PSA.5.curiosity.1` | Sl 5:3 | Em que hora do dia Davi apresenta o clamor? | Oração pela manhã é motivo recorrente nos salmos de Davi (Sl 59:16; 143:8), e a carta não cita o número. | Citar o número: 'no Salmo 5'. |
| `PSA.5.place.1` | Sl 5:7 | Para onde Davi se inclina ao adorar na casa de Deus? | Inclinar-se diante do templo santo se repete no Sl 138:2, também de Davi, e a carta não cita o número. | Citar o número: 'no Salmo 5'. |
| `PSA.7.name.1` | Sl 7:1 | Depois das palavras de quem Davi cantou a Deus? | A resposta depende do sobretítulo ('palavras de Cuxe') e a carta não cita o número do salmo; sem isso só quem decorou títulos resolve. | Citar o número: 'no Salmo 7' (ou descrever a ocasião do perseguidor benjamita). |
| `PSA.9.curiosity.1` | Sl 9:4 | Onde Deus se sentou para julgar a causa de Davi? | 'Julgar a causa' é linguagem recorrente nos salmos de Davi (tb. Sl 7), e a carta não cita o número. | Citar o número: 'no Salmo 9'. |
| `PSA.11.subject.1` | Sl 11:1 | Como mandam a alma de Davi fugir? | Fugir como pássaro se repete no Sl 55:6 (asas de pomba, também de Davi), e a carta não cita o número do salmo. | Citar o número: 'no Salmo 11'. |
| `PSA.13.curiosity.1` | Sl 13:1 | O que Davi pergunta quando Deus esconde o rosto? | 'Até quando' diante do rosto escondido se repete em vários salmos de Davi (Sl 6:3; 22; 27:9), e a carta não cita o número. | Citar o número: 'no Salmo 13'. |
| `PSA.13.subject.1` | Sl 13:3 | Por que Davi pede que Deus ilumine os olhos dele? | A luz dos olhos ligada à angústia e à morte aparece também no Sl 38:10, de Davi, e a carta não cita o número. | Citar o número: 'no Salmo 13'. |
| `PSA.15.place.1` | Sl 15:1 | Em que tenda e em que monte Davi pergunta quem mora? | A pergunta é quase idêntica à do Sl 24:3 (tabernáculo/monte do Senhor), ambos de Davi, e a carta não cita o número. | Citar o número: 'no Salmo 15'. |
| `PSA.16.curiosity.1` | Sl 16:5 | O que Davi diz que Deus é na herança dele? | 'Deus é a porção/herança' aparece também no Sl 73:26 (não-Davi) e o detalhe depende do Sl 16:5; a carta não cita o número. | Citar o número: 'no Salmo 16'. |
| `PSA.17.subject.1` | Sl 17:8 | Como Davi pede para ser guardado e escondido? | 'Sombra das asas' como abrigo se repete em vários salmos de Davi (Sl 36:7; 57:1; 63:7), e a carta não cita o número. | Citar o número: 'no Salmo 17'. |
| `PSA.17.curiosity.1` | Sl 17:15 | Quando Davi ficará satisfeito com a imagem de Deus? | Detalhe exclusivo do Sl 17:15, mas sem o número do salmo nem âncora própria na pergunta. | Citar o número: 'no Salmo 17'. |
| `PSA.18.curiosity.1` | Sl 18:33 | Com os pés de qual animal os de Davi são comparados? | 'Pés como os das cervas' aparece também em Hab 3:19, e a carta não cita o número do salmo. | Citar o número: 'no Salmo 18'. |
| `PSA.18.curiosity.2` | Sl 18:34 | Qual arco se quebra nos braços de Davi? | 'Arco de bronze' também aparece em Jó 20:24, e a carta não cita o número do salmo. | Citar o número: 'no Salmo 18'. |
| `PSA.25.curiosity.1` | Sl 25:7 | O que Davi pede que Deus não traga à memória? | 'Não te lembres dos pecados' é pedido que se repete, e o detalhe dos erros da juventude depende do Sl 25:7, não citado. | Citar o número: 'no Salmo 25'. |
| `PSA.25.subject.1` | Sl 25:11 | O que Davi pede em nome de Deus, pela culpa grande? | Davi pede perdão da maldade em vários salmos (Sl 32:5; 38; 41; 51), e a carta não cita o número que isola a ocasião. | Citar o número: 'no Salmo 25'. |
| `PSA.26.subject.1` | Sl 26:6 | O que Davi faz nas mãos ao rodear o altar? | 'Lavo as mãos na inocência' aparece também no Sl 73:13 (não-Davi), e a carta não cita o número do salmo de Davi. | Citar o número: 'no Salmo 26'. |
| `PSA.26.place.1` | Sl 26:8 | Qual morada Davi diz que ama? | O amor pela casa de Deus se repete em vários salmos (Sl 27:4; 69:9; 84:1-2), e a carta não cita o número. | Citar o número: 'no Salmo 26'. |
| `PSA.27.curiosity.1` | Sl 27:4 | Qual pedido único Davi quer para a vida toda? | 'Morar na casa do Senhor todos os dias' também encerra o Sl 23:6, ambos de Davi, e a carta não cita o número. | Citar o número: 'no Salmo 27'. |
| `PSA.28.subject.1` | Sl 28:1 | O que Davi teme, se Deus ficar calado com ele? | 'Descer à cova' em silêncio aparece também no Sl 143:7, de Davi, e a carta não cita o número. | Citar o número: 'no Salmo 28'. |
| `PSA.28.place.1` | Sl 28:2 | Para onde Davi levanta as mãos ao clamar? | 'Templo santo' como direção das mãos se repete no Sl 5:7 e 138:2, ambos de Davi, e a carta não cita o número. | Citar o número: 'no Salmo 28'. |
| `PSA.31.curiosity.1` | Sl 31:12 | Com qual objeto Davi se compara ao ser esquecido? | Ser como vaso quebrado é comparação que se repete na Escritura (Jó; Is; Sl 2:9), e o alvo do Sl 31:12 não fica identificável sem o número. | Citar o número: 'no Salmo 31'. |
| `PSA.31.curiosity.2` | Sl 31:21 | A que lugar Davi compara a bondade de Deus com ele? | 'Cidade murada' é detalhe do Sl 31:21 e a carta não cita o número nem traz outra âncora da ocasião. | Citar o número: 'no Salmo 31'. |
| `PSA.35.curiosity.1` | Sl 35:3 | Qual arma Davi pede que Deus pegue contra quem o persegue? | Pedidos de armas contra perseguidores se repetem nos salmos de Davi, e a 'lança' fica no Sl 35:3, não citado. | Citar o número: 'no Salmo 35'. |
| `PSA.35.subject.1` | Sl 35:13 | Como Davi se vestia quando o inimigo ficava doente? | Luto/jejum pelo adversário é comportamento difícil de situar sem o sobretítulo do Sl 35, não citado. | Citar o número: 'no Salmo 35'. |
| `PSA.38.curiosity.1` | Sl 38:2 | O que atingiu Davi, junto com a mão pesada? | Flechas/mão pesada de Deus sobre o aflito é tema recorrente, e a carta não cita o número do salmo. | Citar o número: 'no Salmo 38'. |
| `PSA.38.curiosity.2` | Sl 38:4 | Com o que Davi compara o peso das culpas dele? | 'Culpa/carga pesada demais' é linguagem que se repete (tb. Sl 39), e a comparação exata depende do Sl 38:4, não citado. | Citar o número: 'no Salmo 38'. |
| `PSA.38.subject.1` | Sl 38:13 | A quem Davi se iguala quando não abre a boca? | Ficar mudo/surdo diante dos adversários se repete no Sl 39:2 (também Davi), e a carta não cita o número. | Citar o número: 'no Salmo 38'. |
| `PSA.39.subject.1` | Sl 39:5 | Com qual medida curta Deus marcou os dias de Davi? | A brevida/palmo dos dias é tema que se repete em salmos de Davi (tb. Sl 39 vs. Sl 144:4), e o 'palmo' fica no Sl 39:5, não citado. | Citar o número: 'no Salmo 39'. |
| `PSA.39.curiosity.1` | Sl 39:12 | Como Davi se descreve diante de Deus, igual aos pais? | 'Estrangeiro e peregrino' descreve também os patriarcas (Gn 23:4) e Davi em 1Cr 29:15; a carta não cita o número do salmo. | Citar o número: 'no Salmo 39'. |
| `PSA.40.subject.1` | Sl 40:2 | Onde Deus firmou os pés de Davi depois do lamaçal? | 'Firmar os pés na rocha' se repete em salmos de Davi (Sl 27:5; 61:2), e a carta não cita o número. | Citar o número: 'no Salmo 40'. |
| `PSA.40.curiosity.1` | Sl 40:6 | O que Deus fez nas orelhas de Davi, sem pedir oferta? | 'Abriu as orelhas' é detalhe do Sl 40:6 e confunde com o rito de rasgar a orelha da lei (Êx 21:6); a carta não cita o número. | Citar o número: 'no Salmo 40'. |
| `PSA.48.curiosity.1` | Sl 48:12 | O que se deve contar dando a volta em Sião? | 'Contar as torres' é detalhe do Sl 48:12-13, e a carta não cita o número nem outra âncora. | Citar o número: 'no Salmo 48'. |
| `PSA.128.curiosity.1` | Sl 128:6 | Quem ainda será visto, junto com a paz sobre Israel? | Ver os filhos dos filhos junto com a paz de Israel é detalhe fino do Sl 128:6, não citado; a pergunta sozinha não indica uma passagem única. | Citar o número: 'no Salmo 128'. |
| `PSA.129.place.1` | Sl 129:5 | O que se pede contra quem odeia Sião? | Vários salmos pedem vergonha e recuo para os adversários; sem o número, o usuário não resolve só o Sl 129:5. | Citar o número: 'no Salmo 129'. |
| `PSA.132.curiosity.1` | Sl 132:4 | O que Davi recusou dar aos próprios olhos? | O voto de não dar sono aos olhos depende do contexto do Sl 132 (busca pelo lugar do templo), não citado na pergunta. | Citar o número: 'no Salmo 132'. |
| `PSA.142.curiosity.1` | Sl 142:7 | De onde Davi pede que a alma seja tirada? | 'Tira a alma da prisão' é detalhe do Sl 142:7 e a carta não cita o número nem a ocasião da caverna. | Citar o número: 'no Salmo 142'. |
| `PSA.147.place.1` | Sl 147:2 | O que Deus faz por Jerusalém e pelos dispersos de Israel? | Reconstruir Jerusalém e ajuntar os dispersos aparece também no Sl 51:18 e Sl 102:13; sem o número, a pergunta não resolve uma única passagem. | Citar o número: 'no Salmo 147'. |

### Provérbios · 44

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `PRO.1.curiosity.1` | Pv 1:10 | O que o filho deve fazer se pecadores tentarem convencê-lo? | Sujeito "o filho" sem nome e sem âncora de livro; a cena de conselho só se localiza com o livro indicado. | Incluir "em Provérbios 1" na frente. |
| `PRO.2.curiosity.1` | Pv 2:16 | De que mulher a sabedoria pode livrar o jovem? | "O jovem" e "a mulher" são papéis sem âncora; a cena da mulher estranha se repete em Provérbios 2, 5 e 7. | Incluir "em Provérbios 2". |
| `PRO.4.curiosity.1` | Pv 4:18 | A que se compara o caminho de quem é justo? | Papel "o justo" sem âncora do livro; imagens de luz para o justo também existem em 2Sm 23:4, então a passagem não se fecha. | Incluir "em Provérbios 4". |
| `PRO.5.curiosity.1` | Pv 5:4 | Como termina o caminho da mulher pervertida? | "A mulher" é papel sem nome e sem citação do livro; o retrato se repete em Provérbios 5–7. | Incluir "em Provérbios 5". |
| `PRO.6.subject.1` | Pv 6:6 | Quem o preguiçoso deve observar para aprender? | "O preguiçoso" aparece em dezenas de capítulos e a frente não cita o livro nem o capítulo. | Incluir "em Provérbios 6". |
| `PRO.7.subject.1` | Pv 7:22 | A que se parece o jovem que segue a mulher estranha? | "O jovem" sem nome e sem âncora de livro; a advertência sobre a mulher estranha se repete em Provérbios 5–7. | Incluir "em Provérbios 7". |
| `PRO.8.subject.1` | Pv 8:11 | O que a sabedoria diz valer mais do que rubis? | Sem citar Provérbios: o contraste sabedoria/rubis também ocorre em Pv 3:15 e Jó 28, então a passagem fica ambígua. | Incluir "em Provérbios 8, no discurso da Sabedoria". |
| `PRO.8.curiosity.1` | Pv 8:22 | Desde quando a sabedoria diz que Deus a possui? | Persona "a sabedoria diz" sem âncora do livro; só o leitor de Provérbios 8 reconhece a personificação falando. | Incluir "em Provérbios 8". |
| `PRO.9.curiosity.1` | Pv 9:17 | O que a mulher tola diz das águas roubadas? | "A mulher" é papel sem âncora de livro; o dito das águas roubadas precisa de Provérbios citado. | Incluir "em Provérbios 9". |
| `PRO.10.curiosity.1` | Pv 10:12 | O que o amor faz com os pecados? | Sem âncora do livro: "o amor cobre os pecados" repete tema em 1Pe 4:8, então a frente não indica o dito de Pv 10:12. / Colide com 1Pe 4:8 (1PE.4.curiosity.2: amor cobre pecados) sem diferencial de origem na frente. | Incluir "em Provérbios 10". ; em Provérbios (10:12) |
| `PRO.10.subject.1` | Pv 10:26 | A que se compara o preguiçoso para quem o manda? | "O preguiçoso" é papel recorrente sem âncora; há várias imagens dele em Provérbios (vinagre, porta, mão no seio). | Incluir "em Provérbios 10". |
| `PRO.11.subject.1` | Pv 11:22 | A que se compara a mulher bela sem discrição? | "A mulher" é papel sem âncora de livro; a comparação do focinho da porca precisa do livro na frente. | Incluir "em Provérbios 11". |
| `PRO.12.curiosity.1` | Pv 12:4 | O que a mulher virtuosa é para o marido? | "A mulher virtuosa" sem livro citado e retratada em dois lugares (Pv 12:4 e Pv 31), então a ocasião fica indeterminada. | Incluir "em Provérbios 12". |
| `PRO.12.subject.1` | Pv 12:10 | Como o justo trata os animais dele? | Papel "o justo" recorrente em Provérbios e sem âncora de livro na frente. | Incluir "em Provérbios 12". |
| `PRO.13.curiosity.1` | Pv 13:12 | O que a esperança atrasada faz ao coração? | Aforismo sem âncora de pessoa, lugar ou livro; precisa do livro para se localizar. | Incluir "em Provérbios 13". |
| `PRO.13.subject.1` | Pv 13:24 | O que faz quem nunca usa a vara no filho? | "Quem" é papel genérico (o pai) sem âncora; o tema da vara também aparece em Provérbios 23. | Incluir "em Provérbios 13". |
| `PRO.14.subject.1` | Pv 14:4 | Como fica o estábulo quando não há bois? | Cena de estábulo e bois sem sujeito nomeado e sem citação do livro; versículo pouco conhecido (Pv 14:4). | Incluir "em Provérbios 14". |
| `PRO.14.curiosity.1` | Pv 14:30 | O que a inveja faz nos ossos? | Sem âncora do livro; a "podridão dos ossos" por inveja só se atribui à passagem com Provérbios citado. | Incluir "em Provérbios 14". |
| `PRO.15.subject.1` | Pv 15:1 | O que uma resposta mansa faz com a raiva? | Aforismo famoso sem âncora: a frente não diz Provérbios, então não se sabe que é o dito de Pv 15:1. | Incluir "em Provérbios 15". |
| `PRO.15.curiosity.1` | Pv 15:17 | O que é melhor do que o boi cevado com ódio? | Sem âncora: comparações "melhor do que..." se repetem em Provérbios 15–17 e a frente não indica o versículo. / Frase literal de versículo sem âncora de livro na frente. | Incluir "em Provérbios 15". ; em Provérbios (15:17) |
| `PRO.16.curiosity.1` | Pv 16:24 | Como são as palavras suaves? | Sem âncora de livro; a descrição das palavras precisa de Provérbios para se localizar. / Frase literal sem âncora; pode ser confundida com a resposta branda/submisso de Pv 15:1 e 25:15. | Incluir "em Provérbios 16". ; em Provérbios (16:24) |
| `PRO.16.subject.1` | Pv 16:32 | O que é melhor do que conquistar uma cidade? | Sem âncora do livro; sem "em Provérbios", a pergunta vira reflexão geral em vez de item de Pv 16:32. | Incluir "em Provérbios 16". |
| `PRO.17.curiosity.1` | Pv 17:1 | O que é melhor do que festa com briga em casa? | Sem âncora: outra comparação de mesa (Pv 15:17) é parecida e a frente não distingue o capítulo. | Incluir "em Provérbios 17". |
| `PRO.17.subject.1` | Pv 17:12 | Quem é pior de encontrar do que uma ursa sem filhotes? | Sem âncora: a cena da ursa sem filhotes não se liga a nenhum livro sem citar Provérbios. | Incluir "em Provérbios 17". |
| `PRO.18.subject.1` | Pv 18:21 | O que tem poder de trazer morte ou vida? | Sem âncora do livro: morte e vida no poder da língua só em Pv 18:21, mas a frente não dá pessoa, lugar ou livro. | Incluir "em Provérbios 18". |
| `PRO.18.curiosity.1` | Pv 18:24 | Quem pode ser mais chegado do que um irmão? | Sem âncora de livro; "amigo mais chegado que um irmão" precisa de Provérbios citado. | Incluir "em Provérbios 18". |
| `PRO.19.subject.1` | Pv 19:17 | A quem empresta quem ajuda o pobre? | Sem âncora: ajudar o pobre como empréstimo a Deus só se liga a Provérbios com o livro na frente. | Incluir "em Provérbios 19". |
| `PRO.20.curiosity.1` | Pv 20:17 | Como acaba o pão conseguido com mentira? | Sem âncora do livro; imagem rara (cascalho) que só se resolve com o livro indicado. | Incluir "em Provérbios 20". |
| `PRO.22.curiosity.1` | Pv 22:6 | Até quando o menino bem ensinado segue o caminho? | "O menino" é papel sem âncora de livro; o dito de Pv 22:6 não se localiza sem citar Provérbios. | Incluir "em Provérbios 22". |
| `PRO.23.curiosity.1` | Pv 23:32 | A que se parece o fim de quem bebe vinho? | Sem âncora de livro; o ditado do fim de quem bebe não localiza a passagem sem Provérbios. | Incluir "em Provérbios 23". |
| `PRO.24.curiosity.1` | Pv 24:11 | A quem se deve socorrer a caminho da morte? | Sem âncora do livro; a frase peculiar "caminho da morte" precisa de Provérbios na frente. | Incluir "em Provérbios 24". |
| `PRO.25.subject.1` | Pv 25:11 | A que se compara a palavra dita na hora certa? | Sem âncora: maçãs de ouro em salvas são imagem conhecida, mas a frente não indica Provérbios. | Incluir "em Provérbios 25". |
| `PRO.25.curiosity.1` | Pv 25:21 | O que se deve dar a quem odeia, se ele tiver fome? | Sem âncora: a ordem é citada também em Romanos 12:20; a frente não diz que é o dito de Provérbios 25. | Incluir "em Provérbios 25". |
| `PRO.26.subject.1` | Pv 26:11 | A que se parece o tolo que repete a loucura? | "O tolo" é papel recorrente sem âncora; a imagem do cão que volta ao vómito também aparece em 2Pe 2:22. | Incluir "em Provérbios 26". |
| `PRO.26.curiosity.1` | Pv 26:14 | A que se parece o preguiçoso na cama? | "O preguiçoso" sem âncora de livro; em Provérbios o retrato de cama e porta só em 26:14. | Incluir "em Provérbios 26". |
| `PRO.27.curiosity.1` | Pv 27:6 | Como são as feridas que um amigo provoca? | Sem âncora: feridas fiéis do amigo podem confundir com Sl 141:5; a frente não cita o livro. | Incluir "em Provérbios 27". |
| `PRO.27.subject.1` | Pv 27:17 | Como o amigo afia o companheiro? | Sem âncora do livro; o ditado do ferro afiando ferro fica solto sem "em Provérbios". | Incluir "em Provérbios 27". |
| `PRO.28.curiosity.1` | Pv 28:1 | A que se compara a coragem dos justos? | "Os justos" são papel sem âncora de livro; imagens de leão se espalham pela Bíblia. | Incluir "em Provérbios 28". |
| `PRO.28.subject.1` | Pv 28:13 | O que recebe quem confessa o erro e o deixa? | Sem âncora: confissão com misericórdia também soa como 1Jo 1:9; a frente não indica Provérbios. | Incluir "em Provérbios 28". |
| `PRO.29.curiosity.1` | Pv 29:25 | O que o medo das pessoas prepara? | Sem âncora do livro; os laços do medo das pessoas são dito de Pv 29:25, mas a frente não cita o livro. | Incluir "em Provérbios 29". |
| `PRO.30.subject.1` | Pv 30:8 | O que convém pedir, em vez de ser pobre ou rico? | Sem âncora: a oração de Agur (Pv 30:7-9) não se localiza sem apresentar quem pede. | Apresentar a cena: "Na oração de Agur em Provérbios 30". |
| `PRO.30.curiosity.1` | Pv 30:15 | Como se chamam as filhas da sanguessuga? | Sem âncora de livro; as filhas da sanguessuga são figura única de Pv 30:15, mas a frente não cita o livro. | Incluir "em Provérbios 30". |
| `PRO.31.subject.1` | Pv 31:10 | Qual mulher vale muito mais do que os rubis? | Sem âncora de livro: "vale mais que rubis" pode confundir com a sabedoria (Pv 3:15; 8:11; Jó 28) e não só com a mulher de Pv 31. | Incluir "em Provérbios 31". |
| `PRO.31.curiosity.1` | Pv 31:30 | O que é enganoso e vão, ao contrário de temer ao Senhor? | Sem âncora do livro; o contraste da beleza com o temor ao Senhor só aparece em Provérbios 31:30. / Frase literal de versículo (mulher nobre) sem âncora de livro na frente. | Incluir "em Provérbios 31". ; em Provérbios (31:30, o retrato da mulher nobre) |

### Eclesiastes · 19

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `ECC.1.curiosity.1` | Ec 1:7 | O que os rios fazem ao chegar no mar? | Imagem dos rios e do mar sem âncora do livro; só o leitor de Eclesiastes 1:7 localiza a pergunta. | Incluir "em Eclesiastes". |
| `ECC.4.subject.1` | Ec 4:12 | Que corda é a mais difícil de arrebentar? | A corda de três dobras é famosa, mas a frente não cita Eclesiastes; sem o livro, não se localiza Ec 4:12. | Incluir "em Eclesiastes 4". |
| `ECC.4.curiosity.2` | Ec 4:13 | Quem é melhor do que um rei velho e tolo? | Personagens genéricos (rei velho e tolo; criança pobre e sábia) sem âncora de livro. | Incluir "em Eclesiastes". |
| `ECC.5.subject.1` | Ec 5:5 | O que é melhor do que fazer um voto e não pagar? | Sem âncora: sobre não fazer voto, Dt 23:22 traz afirmação concorrente; a frente não cita Eclesiastes. | Incluir "em Eclesiastes 5". |
| `ECC.5.curiosity.1` | Ec 5:10 | O que nunca sacia quem ama o dinheiro? | Sem âncora do livro; a resposta sobre quem ama o dinheiro só se liga a Ec 5:10 com o livro indicado. | Incluir "em Eclesiastes". |
| `ECC.6.subject.1` | Ec 6:2 | Quem come os bens de quem não pode desfrutá-los? | Cena do estranho que come os bens sem âncora de livro na frente. | Incluir "em Eclesiastes 6". |
| `ECC.6.curiosity.1` | Ec 6:6 | O que se ganha em viver mil anos duas vezes sem o bem? | Sem âncora: "viver mil anos duas vezes" é frase única de Ec 6:6, mas a frente não cita o livro. | Incluir "em Eclesiastes 6". |
| `ECC.7.subject.1` | Ec 7:1 | O que vale mais do que o melhor unguento? | Sem âncora de livro: o tema do unguente também aparece em Provérbios 27:9 e a frente não diz Eclesiastes. / Frase sem âncora; 'o bom nome' comparado a algo melhor também aparece em Provérbios 22:1. | Incluir "em Eclesiastes 7". ; em Eclesiastes (7:1) |
| `ECC.8.curiosity.1` | Ec 8:11 | Por que o coração se anima a fazer o mal? | Sem âncora do livro; sobre a demora do julgamento da obra má, precisa de Eclesiastes citado. | Incluir "em Eclesiastes 8". |
| `ECC.9.subject.1` | Ec 9:4 | O que é melhor do que um leão morto? | Sem âncora: leão morto e cão vivo não se ligam a nenhum livro sem citar Eclesiastes. | Incluir "em Eclesiastes 9". |
| `ECC.9.curiosity.1` | Ec 9:11 | Com quem ficam a corrida e a batalha? | Sem âncora do livro; corrida, batalha e o papel do tempo só se ancoram em Eclesiastes. | Incluir "em Eclesiastes 9". |
| `ECC.9.curiosity.2` | Ec 9:15 | Quem livrou a cidade e mesmo assim foi esquecido? | Sem âncora: a história do sábio pobre que salva a cidade é única de Ec 9:15, mas a frente não cita o livro. | Incluir "em Eclesiastes 9". |
| `ECC.10.curiosity.2` | Ec 10:20 | Por que não amaldiçoar o rei nem dentro do quarto? | Sem âncora: as aves que levam a fala pertencem a Ec 10:20; a frente não cita Eclesiastes. | Incluir "em Eclesiastes 10". |
| `ECC.11.subject.1` | Ec 11:1 | O que se deve lançar sobre as águas? | Sem âncora de livro: "pão sobre as águas" é ditado conhecido, mas sem "em Eclesiastes" não se localiza. | Incluir "em Eclesiastes 11". |
| `ECC.11.curiosity.1` | Ec 11:2 | A quantas pessoas se deve dar uma parte? | Sem âncora e de leitura difícil: "a sete e a oito" remete a Ec 11:2; a frente não menciona o livro. | Incluir "em Eclesiastes 11". |
| `ECC.11.curiosity.2` | Ec 11:4 | O que deixa de fazer quem só olha o vento? | Sem âncora do livro; vento e nuvens de Ec 11:4 não se ligam sem o livro. | Incluir "em Eclesiastes 11". |
| `ECC.12.curiosity.1` | Ec 12:6 | O que acontece com a prata e o ouro na imagem da velhice? | Sem âncora: cordão de prata e copo de ouro fazem parte da imagem da velhice de Ec 12:6; frente sem livro. | Incluir "em Eclesiastes 12". |
| `ECC.12.curiosity.2` | Ec 12:12 | O que cansa o corpo, além dos livros sem fim? | Sem âncora: livros sem fim e o cansaço de estudar pertencem a Ec 12:12; a frente não cita o livro. | Incluir "em Eclesiastes 12". |
| `ECC.12.subject.1` | Ec 12:13 | Qual é a conclusão sobre o dever de toda pessoa? | Sem âncora: a chamada "conclusão" só se reconhece como Ec 12:13 com o livro citado. | Incluir "em Eclesiastes 12". |

### Cantares · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `SNG.1.subject.1` | Ct 1:5 | Como a amada se descreve às filhas de Jerusalém? | "A amada" é papel sem nome e a frente não cita Cantares; a conversa com as filhas de Jerusalém não se localiza. | Incluir "em Cantares". |

### Isaías · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `ISA.3.curiosity.2` | Is 3:17 | O que Deus faria com a cabeça das filhas de Sião? | Sem âncora na frente, o leitor não sabe a que oráculo de juízo sobre as filhas de Sião a pergunta se refere. | em Isaías (3:16-17, a soberba das filhas de Sião) |
| `ISA.48.subject.1` | Is 48:20 | Que ordem Deus dá ao povo sobre a Babilônia? | A ordem de sair da Babilônia se repete (Is 48:20; Is 52:11; Jr 50:8; Jr 51:45; Ap 18:4) e a frente não indica qual anúncio é o da carta. | Incluir "em Isaías 48". |

### Jeremias · 9

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `JER.2.subject.1` | Jr 2:13 | Que dois males o povo de Israel cometeu contra Deus? | Pergunta de cara geral sobre 'o povo de Israel' sem âncora do livro; a resposta (fonte viva abandonada e cisternas rachadas) só existe em Jr 2:13, exatamente o padrão 'doutrina geral, resposta de versículo' que o GUIDE critica. | Ancorar: 'em Jeremias (a palavra contra a fonte abandonada), que dois males o povo cometeu?'. |
| `JER.3.subject.1` | Jr 3:8 | O que Deus deu à infiel Israel ao mandá-la embora? | Sem âncora do livro: a infiel Israel mandada embora evoca a alegoria de Jr 3:8, mas Is 50:1 também fala de carta de repúdio da 'mãe' infiel, e as duas passagens divergem no fato. | Citar o quadro do casamento e do repúdio práticas na profecia de Jeremias (as duas esposas infiéis). |
| `JER.8.subject.1` | Jr 8:1-2 | No juízo, o que Deus tiraria das sepulturas dos reis de Judá? | 'No juízo' não indica ocasião nem livro, e os reis de Judá não são nomeados; a resposta (ossos expostos ao sol e à lua) só se liga a Jr 8 e não é alcançável pela pergunta. | Indicar a ocasião: 'na palavra de juízo de Jeremias sobre os ossos dos reis de Judá' ou citar Jr 8. |
| `JER.14.subject.1` | Jr 14:4 | Por que o chão se rachou durante a seca em Judá? | A seca em Judá também é episódio de Joel, e o livro não é citado; além disso a resposta ('porque a chuva não caiu') é uma generalidade que não aponta para a passagem alvo. | Citar o oráculo da seca de Jeremias (Jr 14) ou âncorar 'quando Jeremias anunciava o juízo da seca'. |
| `JER.28.name.1` | Jr 28:1 | Quem era o profeta filho de Azur? | Sem âncora do livro, 'filho de Azur' tem homônimo em Ezequiel (Jazanias, filho de Azur, Ez 11:1); a frase só se resolve pela palavra 'profeta', e o episódio (a contestação de Jeremias em Gibeom) não aparece na pergunta. | Citar o episódio com Hananias: 'o profeta que contestou Jeremias dizendo que a Babilônia cairia em dois anos'. |
| `JER.30.curiosity.1` | Jr 30:18 | O que Deus prometeu pôr outra vez no lugar de costume, em Jeremias? | Mesmo citando Jeremias, a pergunta não diz o quê nem onde ('o lugar de costume'); a resposta ('o templo') só se alcança sabendo Jr 30:18 — que na verdade fala do palácio reassentado, o que evidencia a fragilidade. | Trazer a ocasião: 'na promessa de restauração de Sião e dos montes de Judá (Jr 30), o que Deus volta a pôr em seu lugar?'. |
| `JER.34.place.1` | Jr 34:7 | Quais cidades fortificadas de Judá ainda resistiam? | 'Ainda' remete a um cerco que a pergunta não nomeia; 'quais cidades...?', sem âncora do livro ou da ocasião, não permite chegar só à dupla Laquis e Azeca. | Incluir a ocasião: 'nos últimos dias do cerco babilônico a Jerusalém (Jr 34)'. |
| `JER.44.name.1` | Jr 44:30 | Qual rei do Egito Deus entregaria aos inimigos? | Papel sem nome e sem âncora do livro; a entrega de um faraó 'aos inimigos' também aparece como juízo contra o Egito em Ezequiel, então a frase não resolve sozinha para Ofra. | Ancorar pela ocasião: 'na palavra aos judeus refugiados no Egito (Jr 44), qual rei Deus entregaria na mão dos seus inimigos?'. |
| `JER.50.curiosity.1` | Jr 50:23 | O que se quebrou na imagem da queda da Babilônia? | 'Na imagem da queda' não se entende sozinho e pode evocar a estátua de Nabucodonosor de Dn 2 (outro livro do mesmo baralho misturado), em vez do martelo quebrado de Jr 50:23. | Usar o termo do texto: 'na queda da Babilônia, o que se quebrou — o martelo que quebrava toda a terra?'. |

### Lamentações · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `LAM.1.subject.1` | Lm 1:1 | Como a cidade populosa de Jerusalém ficou? | A cidade é nomeada, mas a pergunta não dá ocasião nem livro (a queda de 586); 'como ficou?' admite calibragens distintas em outros textos de destruição de Jerusalém. | Ancorar a ocasião do lamento: 'depois da queda, conforme o lamento de Lamentações (Lm 1), como ficou a cidade?'. |
| `LAM.1.curiosity.2` | Lm 1:11 | O que o povo de Jerusalém trocava por comida? | Sem âncora do livro nem do episódio da fome do cerco, e a resposta específica ('os próprios bens', Lm 1:11) não é adivinhável de qualquer outra passagem de fome. | Citar a ocasião: 'no lamento de Lamentações, durante a fome do cerco, o que o povo dava por comida?'. |

### Ezequiel · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `EZK.30.subject.1` | Ez 30:4 | O que viria sobre o Egito e causaria dor na Etiópia? | Oráculo de fórmula ('a espada') sem âncora de livro; soa genérico e fecha só no versículo específico. | em Ezequiel (30:4, oráculo contra o Egito) |

### Daniel · 4

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `DAN.4.subject.1` | Dn 4:33 | O que houve com Nabucodonosor quando a sentença caiu? | 'A sentença' é referência solta: só se liga à sentença do sonho da árvore, que a pergunta não nomeia; Belsazar também 'tem a sentença lida' em Dn 5, então o leitor não sabe de que decreto se trata. | Explicitar o decreto: 'a sentença do sonho da árvore (ser expulso de entre os homens e comer erva)'. |
| `DAN.7.subject.1` | Dn 7:14 | Até quando dura o reino dado na visão de Daniel? | 'O reino dado' não diz a quem nem em qual visão; Daniel tem várias visões de reinos, e 'o reino' pode ser o da estátua (Dn 2) ou o dado ao Filho do Homem (Dn 7). | Nomear o recebedor e o quadro: 'na visão das quatro bestas, o reino e o domínio dados ao Filho do Homem — até quando duram?'. |
| `DAN.11.subject.1` | Dn 11:11 | Quem luta contra o rei do norte na visão de Daniel? | 'Na visão de Daniel' não precisa o episódio: o choque entre o rei do norte e o rei do sul se repete ao longo de Dn 11, e a resposta ('o rei do sul') espelha a própria pergunta em vez de se sustentar em fato marcado. | Trazer a ocasião: 'quando o rei do sul se irritou e saiu com uma grande multidão para revidar (Dn 11:11)'. |
| `DAN.12.place.1` | Dn 12:5 | Onde estavam os dois seres que Daniel observou? | 'Os dois seres' é referência vaga: o livro tem vários agentes sobrenaturais (quatro animais, o homem de linho, anjos), e sem dizer qual quadro (a visão final junto ao rio) a pergunta não aponta a resposta. | Ancorar o quadro: 'nos dois que apareceram das margens, um de cada lado do rio, no fim da visão (Dn 12)'. |

### Oséias · 23

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `HOS.1.place.1` | Os 1:5 | Em que vale Deus quebraria o arco de Israel? | Sem âncora de livro, "quebrar o arco de Israel" não indica ocasião nem é fato famoso o bastante para rastrear Os 1:5. | Citar o livro na frente: "em Oséias". |
| `HOS.2.curiosity.1` | Os 2:16 | Que título Israel deixa de usar para Deus? | Sem âncora de livro, o detalhe do título trocado (Baal por marido) não deixa chegar a Os 2:16. | Citar o livro: "em Oséias". |
| `HOS.2.subject.1` | Os 2:19 | Até quando Deus promete se casar com Israel? | Sem âncora de livro, o casamento de Deus com Israel aparece em Jr 3:14 e Is 54:5, e a ocasião de Os 2:19 não fica clara. | Citar o livro: "em Oséias". |
| `HOS.4.curiosity.1` | Os 4:17 | A que Efraim se prendeu? | Efraim é nomeado, mas o fato se repete em Hó 4:17, 8:4, 11:12 e fora do livro, sem ocasião única. | Citar o livro: "de Oséias". |
| `HOS.5.place.1` | Os 5:13 | Onde Efraim buscou cura, sem conseguir? | Sem âncora de livro; buscar socorro na Assíria aparece em Hó 5:13, 7:11 e 8:9 e a pergunta não indica qual. | Citar o livro: "em Oséias". |
| `HOS.5.curiosity.1` | Os 5:14 | Como Deus age contra Efraim e Judá quando a cura falha? | Sem âncora de livro, a imagem do leão não permite rastrear o oráculo de Os 5:14. | Citar o livro: "em Oséias". |
| `HOS.6.curiosity.1` | Os 6:2 | Em que dia Israel espera ser levantado? | Sem âncora de livro, a resposta genericamente "terceiro dia" confunde a cura de Israel (Os 6:2) com a ressurreição. | Citar o livro: "em Oséias". |
| `HOS.6.name.1` | Os 6:7 | Como quem Israel quebrou a aliança? | Sem âncora de livro, a comparação "como Adão" só se resolve sabendo que o versículo é Os 6:7. | Citar o livro: "de Oséias". |
| `HOS.7.curiosity.1` | Os 7:8 | A que comida Efraim é comparado? | Sem âncora de livro, a imagem do bolo que ninguém virou não deixa chegar a Os 7:8. | Citar o livro: "em Oséias". |
| `HOS.7.subject.1` | Os 7:11 | Para onde Efraim voa sem pensar? | Sem âncora de livro, Egito e Assíria repetem em vários oráculos do mesmo livro (Hó 7:11, 8:9, 9:3) sem ocasião. | Citar o livro: "em Oséias". |
| `HOS.8.curiosity.2` | Os 8:4 | De quem não vieram os reis feitos por Israel? | Sem âncora de livro, "reis por Israel" confunde com os reis de 1Rs 12 e o detalhe de Os 8:4 não fica evidente. | Citar o livro: "em Oséias". |
| `HOS.8.place.1` | Os 8:9 | Para onde Efraim sobe sozinho? | Sem âncora de livro, a resposta (Assíria) coincide com as cartas de Os 5:13 e 7:11 e não identifica a ocasião. | Citar o livro: "em Oséias". |
| `HOS.9.subject.1` | Os 9:3 | Para onde Efraim vai em vez de ficar na terra? | Sem âncora de livro, Egito e Assíria repetem entre oráculos de Oséias (9:3, 7:11, 11:5) sem indicar a ocasião. | Citar o livro: "em Oséias". |
| `HOS.9.place.1` | Os 9:6 | Qual cidade sepulta quem foge ao Egito? | Sem âncora de livro, o detalhe de Mênfis como cova dos que fogem ao Egito não é rastreável a Os 9:6. | Citar o livro: "em Oséias". |
| `HOS.10.subject.1` | Os 10:15 | Que fim tem o rei de Israel ligado a Betel? | "O rei de Israel" sem nome nem livro: vários reis caíram perto de Betel, e o detalhe "de manhã é eliminado" não se resolve. | Citar o profeta e a ocasião: "em Oséias, o fim que Deus anuncia". |
| `HOS.11.place.1` | Os 11:1 | De onde Deus chamou Israel menino? | Sem âncora de livro, a mesma frase retorna em Mt 2:15 e coincide com o Êxodo, e não se sabe se é Hó 11:1 ou a ida de Jesus. | Citar o livro: "em Oséias, Deus lembra". |
| `HOS.11.curiosity.1` | Os 11:4 | Com que Deus puxou Israel para perto? | Sem âncora de livro, a imagem das cordas de amor não permite rastrear Os 11:4. | Citar o livro: "em Oséias". |
| `HOS.11.subject.1` | Os 11:9 | Por que Deus não acaba com Efraim? | Sem âncora de livro, a resposta pode ser dada de várias formas; o motivo específico surgiuto em Os 11:9 ("Deus e não homem") não fica claro. | Citar o livro: "em Oséias". |
| `HOS.13.name.1` | Os 13:1 | Por qual deus Efraim se perdeu na culpa? | Sem âncora de livro, o deus pelo qual Efraim caiu não deixa chegar a Os 13:1 com segurança. | Citar o livro: "em Oséias". |
| `HOS.13.place.1` | Os 13:5 | Em que terra Deus conheceu Israel? | Sem âncora de livro, "conhecer Israel no deserto" repete Jr 2:2 e o Êxodo com resposta parecida, sem ocasião única. | Citar o livro: "em Oséias". |
| `HOS.13.curiosity.1` | Os 13:11 | Como Deus deu e retirou o rei de Israel? | Sem âncora de livro, confunde com a história de Saul (1Sm 8) e o detalhe "por ira e por furor" de Os 13:11 não fica evidente. | Citar o livro: "em Oséias". |
| `HOS.14.curiosity.2` | Os 14:5 | A que flor Israel vai se parecer? | Sem âncora de livro, a comparação com o lírio não informa a ocasião e convive com os lírios de Cnt 2:1 e Mt 6:28. | Citar o livro: "em Oséias". |
| `HOS.14.place.1` | Os 14:5 | As raízes de Israel se parecem com as de qual terra? | Sem âncora de livro, a imagem das raízes comparadas ao Líbano não deixa chegar a Os 14:5. | Citar o livro: "em Oséias". |

### Joel · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `JOL.3.curiosity.1` | Jl 3:3 | Por que preço as nações venderam os jovens de Judá? | Sem âncora de livro, o detalhe do preço (rapazes por prostitutas, moças por vinho) não é rastreável a Jl 3:3. | Citar o livro: "em Joel". |

### Amós · 3

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `AMO.3.subject.1` | Am 3:2 | Por que Deus cobra Israel entre todos os povos? | Sem âncora de livro, a resposta pode ser dada genericamente (povo escolhido) sem que o texto de Am 3:2 fique evidente. | Citar o livro: "em Amós". |
| `AMO.7.subject.1` | Am 7:8 | O que Deus apoia no meio de Israel? | Sem âncora de livro, o prumo também aparece nos versos de Zc 4:10 e a visão de Am 7:8 não fica evidente. | Citar o livro: "na visão de Amós". |
| `AMO.9.subject.1` | Am 9:11 | O que Deus fará com o tabernáculo caído de Davi? | Sem âncora de livro, a mesma promessa é repetida por At 15:16 e a ocasião de Am 9:11 não fica clara. | Citar o livro: "em Amós, Deus promete". |

### Miquéias · 3

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `MIC.4.curiosity.2` | Mq 4:10 | Para onde a filha de Sião vai antes de ser liberta? | Sem âncora de livro, o exílio antes da libertação pode ser no Egito (Jr) ou na Babilônia (Mq 4:10) sem indicação. | Citar o livro: "em Miquéias". |
| `MIC.5.curiosity.1` | Mq 5:5 | Quantos líderes se levantam contra a Assíria? | Sem âncora de livro, "sete pastores e oito príncipes" é número particular e não deduzível sem Mq 5:5. | Citar o livro: "em Miquéias". |
| `MIC.5.name.1` | Mq 5:6 | A terra de quem cai junto com a Assíria? | Sem âncora de livro, "terra de Ninrode" é detalhe só alcançável sabendo o versículo de Mq 5:6. | Citar o livro: "em Miquéias". |

### Sofonias · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `ZEP.1.curiosity.1` | Sf 1:12 | Com o que Deus vasculhará Jerusalém? | Sem âncora de livro, o detalhe das lâmpadas de Sf 1:12 não é rastreável sem saber o profeta. | Citar o livro: "em Sofonias". |

### Zacarias · 9

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `ZEC.2.curiosity.2` | Zc 2:4 | Por que Jerusalém será habitada sem muros? | Sem âncora de livro, a justificativa (muita gente e animais) só se resolve sabendo Zc 2:4. | Citar o livro: "em Zacarias". |
| `ZEC.2.subject.1` | Zc 2:5 | O que Deus será ao redor de Jerusalém? | Sem âncora de livro, o muro de fogo de Zc 2:5 pode ser confundido com imagens parecidas de Is 4:5 e não fica evidente. | Citar o livro: "em Zacarias". |
| `ZEC.8.subject.1` | Zc 8:3 | O que Deus fará em Sião quando Jerusalém ganhar novo nome? | Sem âncora de livro, Deus voltar a habitar no meio de Sião repete Ez 43:7 e Zc 2:10, e a ocasião de Zc 8:3 não fica clara. | Citar o livro: "em Zacarias". |
| `ZEC.8.curiosity.1` | Zc 8:5 | Quem vai brincar nas ruas de Jerusalém? | Sem âncora de livro, a resposta genérica (meninos e meninas) não identifica a ocasião de Zc 8:5. | Citar o livro: "em Zacarias". |
| `ZEC.10.curiosity.2` | Zc 10:4 | O que sairá de Judá junto com o opressor? | Sem âncora de livro, "o que sairá de Judá" remete a Gn 49:10 e Is 11:1, e o detalhe de Zc 10:4 (pedra de esquina, estaca e arco) não é deduzível. / A expressão citada é enigmática e, sem âncora, não permite identificar o versículo nem uma resposta única. | Citar o livro: "em Zacarias". ; em Zacarias (10:4, promessa sobre Judá) |
| `ZEC.12.curiosity.1` | Zc 12:3 | O que Jerusalém será para as nações? | Sem âncora de livro, "o que Jerusalém será para as nações" admite várias respostas (luz, invencível) e não chega à pedra que fere de Zc 12:3. | Citar o livro: "em Zacarias". |
| `ZEC.12.curiosity.2` | Zc 12:8 | Como ficará o mais fraco em Jerusalém? | Sem âncora de livro, o detalhe "como Davi" só se resolve sabendo o versículo de Zc 12:8. | Citar o livro: "em Zacarias". |
| `ZEC.12.subject.1` | Zc 12:10 | Por quem a casa de Davi fará pranto? | Sem âncora de livro, a frase retorna no NT (Jo 19:37) e a ocasião da promessa de Zc 12:10 não fica clara. | Citar o livro: "em Zacarias". |
| `ZEC.13.curiosity.1` | Zc 13:1 | O que será aberto para a casa de Davi? | Sem âncora de livro, a fonte contra o pecado é detalhe não rastreável sem saber Zc 13:1. | Citar o livro: "em Zacarias". |

### Mateus · 7

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `MAT.4.name.1` | Mt 4:18 | Que pescadores Jesus chamou no mar da Galileia? | A mesma cena chama também Tiago e João, e o chamado se repete nos evangelhos; sem âncora, 'que pescadores' não fixa a dupla esperada (Pedro e André). | Citar 'em Mateus' ou dizer 'os dois irmãos lançadores de rede'. |
| `MAT.7.subject.1` | Mt 7:12 | Como Jesus resumiu a Lei e os Profetas, antes da porta estreita? | Há dois resumos igualmente famosos da Lei e dos Profetas (a regra de ouro em Mt 7:12 e o grande mandamento em Lc 10:27/Mt 22:40); sem âncora de livro há duas respostas clássicas. | Citar 'no sermão do monte' ou 'em Mateus'. |
| `MAT.17.name.1` | Mt 17:3 | Quem apareceu conversando com Jesus no monte? | A âncora é apenas 'no monte'; nem a transfiguração nem o livro são nomeados, e o evento repete nos três sinópticos. | Mencionar a transfiguração ou citar 'em Mateus'. |
| `MAT.20.name.1` | Mt 20:20 | Quem pediu tronos para os filhos de Zebedeu? | Nas narrações o pedido vem de pessoas diferentes: a mãe deles em Mateus e os próprios irmãos em Marcos; sem âncora de livro, quem pediu é ambíguo. | Citar 'em Mateus'. |
| `MAT.21.subject.1` | Mt 21:9 | O que a multidão clamava a Jesus na entrada em Jerusalém? | O mesmo evento é aclamado com palavras diferentes por evangelho (Filho de Davi em Mt; rei de Israel em Jo); sem âncora, a resposta varia. | Citar 'em Mateus'. |
| `MAT.22.subject.1` | Mt 22:37 | Como Jesus disse para amar a Deus? | O grande mandamento vem com listas diferentes nos evangelhos (Mateus omite as 'forças', Marcos e Lucas as incluem); sem âncora, a lista esperada é ambígua. | Citar 'em Mateus' ou 'a resposta ao escriba'. |
| `MAT.23.subject.1` | Mt 23:27 | A que Jesus comparou os escribas e fariseus? | No mesmo capítulo há duas imagens parecidas (túmulos caiados e copos limpos por fora); sem citar o discurso nem o livro, o usuário pode dar a outra imagem. | Citar 'no discurso contra os escribas' ou 'em Mateus'. |

### Marcos · 7

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `MRK.5.place.1` | Mc 5:1 | À terra de quem Jesus chegou, onde o espírito se chamava Legião? | A região do endemoninhado é gadarena em Marcos/Lucas e gergesena em Mateus; sem âncora de livro, existem dois nomes concorrentes para o mesmo episódio. | Citar 'em Marcos'. |
| `MRK.9.subject.1` | Mc 9:3 | Como ficaram as roupas de Jesus no monte? | 'No monte' sozinho é âncora fraca: Jesus passou por vários montes, e a transfiguração não é nomeada nem por evento nem por livro. | Mencionar a transfiguração ou citar 'em Marcos'. |
| `MRK.9.name.1` | Mc 9:4 | Quem apareceu falando com Jesus no monte? | A âncora é apenas 'no monte'; nem a transfiguração nem o livro são nomeados, e o mesmo evento repete nos três sinópticos. | Mencionar a transfiguração ou citar 'em Marcos'. |
| `MRK.12.subject.1` | Mc 12:30 | Como Jesus mandou amar a Deus? | O grande mandamento vem com listas diferentes nos evangelhos (Marcos e Lucas incluem 'forças', Mateus não); sem âncora, a lista esperada é ambígua. | Citar 'em Marcos' ou 'a resposta ao escriba'. |
| `MRK.14.curiosity.1` | Mc 14:52 | Como fugiu o rapaz que seguia Jesus na prisão? | 'O rapaz' é personagem sem nome e o episódio do lençol é exclusivo de Marcos, mas a pergunta não indica livro nem ocasião precisos. | Apresentar 'um jovem que só vestia um lençol' ou citar 'em Marcos'. |
| `MRK.16.name.1` | Mc 16:1 | Quem comprou especiarias, passado o sábado, para ungir Jesus? | A lista de mulheres do amanhecer muda por evangelho (Marcos traz Salomé, Mateus fala de Madalena e a outra Maria); sem âncora, a lista esperada é incerta. | Citar 'em Marcos'. |
| `MRK.16.subject.1` | Mc 16:6 | O que o jovem anunciou sobre Jesus no sepulcro? | 'O jovem' é papel sem nome e sem âncora de livro; nos outros evangelhos a messagem vem de um anjo (Mt) ou de dois varões (Lc), e o usuário não sabe qual versão esperar. | Dizer 'no sepulcro vazio, ao clarear do domingo' ou citar 'em Marcos'. |

### Lucas · 6

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `LUK.4.name.1` | Lc 4:8 | Quem Jesus mandou ficar para trás, no alto monte? | Sem âncora de livro, 'ficar para trás' pode remeter a 'para trás de mim, Satanás' dito a Pedro (Mt 16:23/Mc 8:33), e a ocasião da tentação não fica clara. | Dizer 'na tentação, quando o diabo lhe mostrou os reinos do mundo'. |
| `LUK.5.place.1` | Lc 5:1 | Junto a que lago a multidão apertava Jesus? | O mesmo corpo d'água tem nomes diferentes por evangelho (lago de Genesaré por Lucas, mar da Galileia, mar de Tibérias em João); sem âncora, a resposta pode divergir. | Dizer 'no relato da chamada dos pescadores' ou citar 'em Lucas'. |
| `LUK.7.curiosity.1` | Lc 7:41 | Quanto deviam os dois homens da história de Jesus? | 'A história de Jesus' não nomeia a parábola nem a ocasião (casa de Simão fariseu); o usuário precisa adivinhar de qual história se trata. | Dizer 'na história que Jesus contou a Simão fariseu'. |
| `LUK.9.curiosity.1` | Lc 9:30 | Quem apareceu conversando com Jesus no monte? | A âncora é apenas 'no monte'; nem a transfiguração nem o livro são nomeados, e o mesmo evento repete nos três sinópticos. | Mencionar a transfiguração ou citar 'em Lucas'. |
| `LUK.11.subject.1` | Lc 11:1 | O que um discípulo pediu que Jesus ensinasse? | 'Um discípulo' fica sem nome e sem ocasião, e pedidos por ensino aparecem em mais de um evangelho; o episódio do Pai nosso não é anunciado. | Dizer 'após ver Jesus orando num lugar' ou citar 'em Lucas'. |
| `LUK.20.place.1` | Lc 20:1 | Onde Jesus ensinava quando os chefes o interrogaram? | Os chefes interrogam Jesus em mais de uma ocasião (Lc 20:1 no templo; Jo 10 em Jerusalém); sem âncora de livro ou de episódio, a resposta pode divergir. | Dizer 'na pergunta sobre a autoridade dele' ou citar 'em Lucas'. |

### João · 4

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `JHN.1.place.1` | Jo 1:28 | Onde João batizava, além do Jordão? | 'Além do Jordão' aparece mais de uma vez em João (1:28 e 10:40) e a grafia varia por tradução (Betábara/Betânia); sem âncora, há mais de uma resposta plausível. | Citar 'em João' ou 'no relato do batismo de Jesus'. |
| `JHN.12.subject.1` | Jo 12:13 | Como a multidão recebeu Jesus na chegada a Jerusalém? | O mesmo evento é aclamado com palavras diferentes por evangelho (rei de Israel em João, Filho de Davi em Mateus); sem âncora, a resposta varia. | Citar 'em João'. |
| `JHN.18.subject.1` | Jo 18:1 | Depois de orar pelos que viriam a crer, para onde Jesus foi? | A âncora é indireta (o conteúdo da oração de João 17); o jardim além do Cedrom (Getsêmani) não é nomeado, impedindo a identificação direta da ocasião. | Dizer 'ao sair da última ceia, para o jardim do Getsêmani'. |
| `JHN.21.place.1` | Jo 21:1 | Junto a qual mar Jesus apareceu de novo? | 'Junto a qual mar Jesus apareceu de novo?': o 'de novo' pressupõe a aparição anterior; mistura-se com o mar da Galiléia nos episódios de pesca (Lc 5; Mc 1) se o leitor não traça a distinção de ocasião. | Citar 'a terceira vez' ou 'aos sete discípulos, na carta de João 21'.. (varredura de segurança) |

### Atos · 6

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `ACT.3.subject.1` | At 3:8 | O que o homem coxo passou a fazer no templo de Jerusalém? | 'O homem coxo' fica sem nome e a porta Formosa não aparece na pergunta, embora exista outro coxo curado desde o nascimento em Atos 14. | Dizer 'o coxo da porta Formosa, curado por Pedro e João'. |
| `ACT.4.subject.1` | At 4:3 | O que fizeram com Pedro e João em Jerusalém, já de noite? | A prisão em Jerusalém se repete (At 4:3 e At 5:18) e o motivo da primeira (a cura da porta Formosa) não é citado; 'já de noite' é âncora fraca. | Dizer 'depois de curarem o coxo da porta Formosa'. |
| `ACT.5.place.1` | At 5:12 | Onde os apóstolos se reuniam com o povo em Jerusalém? | Sem âncora de episódio, 'os apóstolos' apenas permitem respostas genéricas ('no templo'), embora em João 10 Jesus esteja no mesmo pórtico de Salomão. | Dizer 'após os sinais e curas do capítulo 5' ou 'os muitos sinais e maravilhas entre a igreja'. |
| `ACT.5.subject.1` | At 5:19 | Quem tirou os apóstolos da prisão em Jerusalém? | Há duas libertações por anjo em Atos (5:19 e 12:7) e a ocasião não é indicada; o usuário pode misturar o episódio da prisão de Herodes. | Dizer 'na primeira prisão coletiva' ou 'na noite após o Sinédrio, antes do Conselho'. |
| `ACT.21.subject.1` | At 21:30 | O que a multidão fez com Paulo no templo de Jerusalém? | 'A multidão fez com Paulo no templo de Jerusalém': a cena se repete em mais de um trecho (At 21:30-32 e perseguições seguintes); sem âncora do trecho, há mais de uma plausível. | Citar o evento: 'quando Paulo foi preso no templo' ou 'no tumulto em Jerusalém', na linha dos cards que já dizem 'em Éfeso'.. (varredura de segurança) |
| `ACT.26.subject.1` | At 26:28 | O que o rei Agripa disse no fim da defesa de Paulo? | 'O rei Agripa disse no fim da defesa de Paulo' tem âncora de personagem, mas 'disse no fim' pressupõe que o leitor sabe qual das defesas diante de Agripa; duvidável se a carta-irmã ACT.25/ACT.24 disputa o mesmo fato. | Manter, se a resposta for a frase 'Quase me persuades a me tornar cristão.'; caso contrário, nomear 'na defesa diante de Agripa'.. (varredura de segurança) |

### Romanos · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `ROM.6.curiosity.1` | Rm 6:4 | Para que Paulo diz que a pessoa é sepultada com Cristo no batismo? | O fato 'sepultado com ele no batismo' também está em Colossenses 2:12 e 'segundo Paulo' não localiza a ocasião. | em Romanos (6:4) |
| `ROM.15.place.2` | Rm 15:25 | Aonde Paulo ia para ajudar o povo de Deus? | 'O povo de Deus' é referência solta; Paulo viajou a Jerusalém várias vezes sem âncora do livro ou da coleta. | Citar Romanos 15 ou precisar o motivo ('levar a coleta aos santos'). |

### 1 Coríntios · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `1CO.9.curiosity.1` | 1Co 9:9 | O que a lei não permite fazer com o boi que trilha o grão? | A citação de Dt 25:4 repete em 1Tm 5:18 e a frente sem âncora não localiza o argumento paulino. | o argumento de Paulo sobre pagar os que pregam (1 Coríntios 9:9) |

### 2 Coríntios · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `2CO.13.curiosity.2` | 2Co 13:1 | Quantas testemunhas firmam uma palavra, na regra que Paulo cita? | 'A regra que Paulo cita' não ancora o lugar; a mesma regra aparece em Dt 19:15, Mt 18:16 e 1Tm 5:19. | Citar 2 Coríntios 13 ou o contexto ('na sua terceira visita a Corinto'). |
| `2CO.8.place.1` | 2Co 8:1-2 | Quem deu com alegria mesmo sendo muito pobre? | Sem citar a carta, a descrição é ambígua (pode apontar a viúva pobre de Mc 12:41-44); a resposta só fecha em 2Co 8:2. | Citar a segunda carta aos Coríntios ou nomear as igrejas. |

### Gálatas · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `GAL.3.name.1` | Gl 3:6 | Quem creu em Deus e isso lhe foi imputado como justiça? | A mesma frase é citada em Gn 15:6, Rm 4:3 e Tg 2:23, sem âncora que identifique a carta. | Citar Gálatas 3 ou o contexto ('Paulo perguntou aos gálatas'). |
| `GAL.6.curiosity.1` | Gl 6:15 | O que importa em Cristo, e não a circuncisão? | Colide com 2Co 5:17 ('nova criatura em Cristo') sem citar a carta; o usuário não distingue a ocasião. | Citar Gálatas 6 ou o contexto ('nem a circuncisão vale algo'). |

### Efésios · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `EPH.1.subject.1` | Ef 1:7 | O que temos em Cristo por meio do sangue dele? | Colôs 1:14 repete quase palavra por palavra ('nele temos a redenção, o perdão dos pecados') sem âncora da carta. | Citar Efésios 1 ou o tema da carta. |
| `EPH.6.name.1` | Ef 6:21 | Quem Paulo enviou para contar como ele estava? | Colôs 4:7-8 repete o mesmo envio de Tíquico com a mesma missão, sem citar a carta. | Citar Efésios 6 ou o contexto ('junto com a carta'). |

### 1 Tessalonicenses · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `1TH.2.curiosity.1` | 1Ts 2:9 | Quando Paulo trabalhava para não ser peso? | O mesmo fato quase literal em 2Ts 3:8 ('trabalhando noite e dia, para não incomodar ninguém'), sem âncora da carta. | Citar 1 Tessalonicenses 2 ou o contexto ('anunciando o evangelho'). |
| `1TH.4.curiosity.2` | 1Ts 4:11 | O que Paulo manda fazer com as próprias mãos? | Colide com Ef 4:28 ('trabalhe, fazendo com as próprias mãos...') sem citar a carta. | Citar 1 Tessalonicenses 4 ou o contexto ('viver em paz e cuidar da própria vida'). |

### 2 Tessalonicenses · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `2TH.3.curiosity.1` | 2Ts 3:8 | Como Paulo conseguia o próprio pão? | O mesmo fato quase literal em 1Ts 2:9 ('noite e dia, para não ser peso') sem âncora da carta. | Citar 2 Tessalonicenses 3 ou o contexto ('dando o exemplo aos desocupados'). |

### 1 Timóteo · 3

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `1TI.2.curiosity.1` | 1Tm 2:9 | Com o que as mulheres não devem se ataviar, segundo Paulo? | Colide quase palavra por palavra com 1Pe 3:3, sem âncora da carta. | Citar 1 Timóteo 2 ou o contexto ('na assembleia'). |
| `1TI.3.subject.1` | 1Tm 3:2 | Como deve ser o bispo, segundo Paulo? | 'Irrepreensível, marido de uma mulher' é requisito do bispo também em Tito 1:6-7; sem âncora, as duas listas concorrem. | em 1 Timóteo (3:2) |
| `1TI.6.curiosity.2` | 1Tm 6:15 | Que títulos Paulo dá ao único poderoso Senhor? | 'Rei dos reis e senhor dos senhores' também é o título da besteira em Ap 19:16, sem citação da carta. | Citar 1 Timóteo 6 ou o contexto ('no cargo do mandato dado a Timóteo'). |

### Hebreus · 6

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `HEB.1.subject.1` | Hb 1:4 | Como o Filho supera os anjos? | "O Filho" não ancora o livro e, dentro do próprio tema, Hb 1-2 admite várias respostas (nome herdado, adoração dos anjos, trono eterno). | Citar Hebreus: "Em Hebreus 1, por que o Filho é superior aos anjos?" |
| `HEB.1.curiosity.1` | Hb 1:13 | O que será feito dos inimigos do Filho? | "Inimigos" sem âncora admite várias saídas sendo a do escabelo dos pés (Sl 110) apenas uma entre elas. | Citar Hebreus: "Segundo Hebreus 1, o que será feito dos inimigos do Filho?" |
| `HEB.2.subject.1` | Hb 2:14 | Por que Jesus compartilhou a carne e o sangue? | Sem âncora, "por que Jesus se fez homem" tem várias respostas válidas; a pretendida (destruir o diabo) é apenas uma delas. | Citar Hebreus: "Segundo Hebreus 2, por que Jesus partilhou da carne e do sangue?" |
| `HEB.2.name.1` | Hb 2:16 | De quem é a descendência que Jesus tomou? | Um leitor frio pode responder "de Israel" ou "de Davi" e ser marcado errado, pois só Hb 2:16 fixa "descendência de Abraão, e não dos anjos". | Citar Hebreus: "Em Hebreus 2, de quem é a descendência que Jesus socorre?" |
| `HEB.4.name.1` | Hb 4:8 | Quem não deu ao povo o descanso definitivo? | Pergunta estranha sem âncora; o fato só existe em Hb 4:8 (Josué) e não é reconhecível fora dele. | Citar Hebreus 4: "Em Hebreus 4, quem não deu ao povo o descanso definitivo?" |
| `HEB.5.curiosity.1` | Hb 5:7 | Como Jesus orou quando ainda vivia na carne? | Sem âncora, o leitor pensa em Getsêmani e responde "com angústia/suor", divergindo da formulação de Hb 5:7. | Citar Hebreus: "Segundo Hebreus, como Jesus orou nos dias da sua carne?" |

### 1 Pedro · 4

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `1PE.1.subject.1` | 1Pe 1:3 | Como os crentes nasceram de novo para uma esperança viva? | Sem âncora, "como se nasce de novo" admite respostas como "pela fé", "pelo Espírito" ou "pela palavra" (1Pe 1:23), divergindo do cartão (ressurreição). | Citar 1 Pedro: "Em 1 Pedro 1, como nasceram de novo os crentes?" |
| `1PE.3.subject.1` | 1Pe 3:18 | Por que Cristo sofreu uma vez? | O "uma vez" não ajuda na frente; o leitor frio responde "pelos nossos pecados", enquanto o cartão pede a formulação "o justo pelos injustos, para levar-nos a Deus". | Citar 1 Pedro: "Segundo 1 Pedro 3, por que Cristo sofreu uma vez?" |
| `1PE.4.subject.1` | 1Pe 4:16 | Como reagir ao sofrer por ser cristão? | Parece conselho geral com muitas respostas possíveis (alegrar-se, perseverar, orar); a resposta só fecha em 1Pe 4:16. | Citar 1 Pedro: "Segundo 1 Pedro 4, como reagir ao sofrer por ser cristão?" |
| `1PE.5.place.1` | 1Pe 5:13 | Onde estava a comunidade escolhida que saúda com Marcos? | A leitura fria leva à "Roma" (interpetração comum da Babilônia figurada), divergindo da resposta literal esperada. | Citar 1 Pedro: "De onde a 'eleita' e Marcos saúdam no fim de 1 Pedro?" e fixar que a texto diz Babilônia |

### 2 Pedro · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `2PE.1.subject.1` | 2Pe 1:21 | Quem produziu a profecia da Escritura? | Pergunta de cara doutrinal: o leitor frio responde "o Espírito Santo", mas o cartão espera "homens santos conduzidos pelo Espírito" (2Pd 1:21). | Citar 2 Pedro: "Segundo 2 Pedro, quem produziu a profecia da Escritura?" |
| `2PE.3.name.1` | 2Pe 3:15 | Qual irmão escreveu sobre a longanimidade do Senhor? | Pergunta solta sem âncora que a conecte à referência que Pedro faz a Paulo em 2 Pedro 3:15. | em 2 Pedro (3:15, cartas de Paulo sobre a paciência na segunda vinda) |

### 1 João · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `1JN.4.curiosity.1` | 1Jo 4:2 | Qual espírito é da parte de Deus? | Soa como teste geral de espíritos com várias respostas possíveis; o critério só existe em 1Jo 4:2. | Citar 1 João: "Segundo 1 João 4, qual espírito é da parte de Deus?" |
| `1JN.5.curiosity.1` | 1Jo 5:8 | Quais três dão testemunho na terra? | Sem âncora, o leitor pode responder "Pai, Filho e Espírito" (confusão comum com v.7) em vez de Espírito, água e sangue. | Citar 1 João: "Em 1 João 5, quais três dão testemunho na terra?" |

### 2 João · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `2JN.1.curiosity.2` | 2Jo 1:12 | Com o que o presbítero não quis escrever o restante? | 2Jo 1:12 (papel e tinta) e 3Jo 1:13 (tinta e pena) contam quase o mesmo fato, e a frente não distingue as cartas de irmãs. | o presbítero que escreve à senhora eleita (2 João 1:12) |
| `2JN.1.name.1` | 2Jo 1:1 | Quem é a destinatária do presbítero, com os filhos dela? | "O ancião" assina tanto 2 João como 3 João (Gaio); só os pequenos índices "a destinatária/filhos dela" resolvem para a senhora eleita. | Anclar a carta: "A quem João, o ancião, escreve a segunda carta?" |

### 3 João · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `3JN.1.subject.1` | 3Jo 1:7-8 | Por que acolher quem partiu em viagem por causa do nome de Deus? | Pergunta embaraçosa e específica de 3Jo 8; o leitor frio responde "por amor" em vez de "para ser cooperador da verdade". | Citar 3 João: "Segundo 3 João, por que acolher quem parte por causa do nome de Deus?" |

### Apocalipse · 2

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `REV.18.curiosity.2` | Ap 18:10 | Quanto tempo levou o juízo de Babilônia? | O capítulo 18 repete duas quantidades ("num só dia" em 18:8 e "uma hora" em 18:10 e segs.), sem unicidade de resposta. | Fixar o ângulo: "Quanto tempo os reis dizem que Babilônia caiu: 'em uma hora' (Ap 18:10)" |
| `REV.19.subject.1` | Ap 19:11 | Quem vinha sentado no cavalo branco quando o céu se abriu, no Apocalipse? | O leitor frio responde "Jesus", mas o cartão espera o nome da cena ("Fiel e Verdadeiro") e o mesmo Personagem tem outro nome em 19:13. | Citar Apocalipse 19 e fixar o nome: "No Apocalipse 19, qual nome tem o cavaleiro do cavalo branco?" |

### História e geografia · 1

| carta | ref. | pergunta (texto atual) | por que está vaga | contexto a incorporar |
| --- | --- | --- | --- | --- |
| `HIS.0.curiosity.3` | 1054 | Quando a Igreja Católica Romana foi fundada? | Pergunta de data com múltiplas respostas defensáveis (em Cristo, século IV, 1054); o cartão é apologético e nega a premissa. | Reescrever como negação autocontida ou fixar: "Houve uma fundação datada; o cisma com o Oriente foi em 1054" |

## C. Colisões de fato entre cartas do baralho

Reunidas de propósito: mesmo com a frente de cada uma corrigida, se duas cartas treinam o mesmo fato com respostas diferentes, uma delas pode induzir erro no quiz. Em cada caso, ou as duas ganham um diferencial de frente, ou uma delas deixa de disputar o fato.

| Cartas | Fato em disputa |
| --- | --- |
| `1CO.5.curiosity.1` × `GAL.5.curiosity.3` | "um pouco de fermento leveda toda a massa" (1Co 5:6 e Gl 5:9); as duas frentes hoje dizem "segundo Paulo" |
| `PRO.10.curiosity.1` × `1PE.4.curiosity.2` | "o amor cobre pecados" (Pv 10:12 e 1Pe 4:8); a segunda se distingue por "segundo Pedro", a primeira ainda não |
| `MAT.9.name.1` × `MRK.2.name.1` × `LUK.5.name.1` | chamado do cobrador de impostos: Mateus (Mt) vs. Levi (Mc e Lc), três frentes sem nomear o livro |
| `MAT.16.place.1` × `MRK.8.place.1` | mesmo episódio (onde Jesus pergunta quem o povo diz que ele é), com resposta idêntica — só redundância, sem risco de quiz |
| `MRK.15.curiosity.1` | hora da crucificação: "hora terceira" (Mc 15:25) vs. "quase a hora sexta" (Jo 19:14), e o usuário lê a Bíblia inteira |
| `LUK.10.subject.1` | envio de dois em dois: setenta (Lc 10:1) vs. os doze (Mc 6:7) |
| `JDG.7.name.1` | pares capturados e mortos: Orebe e Zeebe (Jz 7:25) vs. Zeba e Zalmuna (Jz 8) |
| `MAT.21.subject.1` × `JHN.12.subject.1` | aclamação na entrada em Jerusalém; as duas esperam citações diferentes |
| `MAT.22.subject.1` × `MRK.12.subject.1` | grande mandamento; as duas frentes esperam formulações diferentes |
| `2JN.1.curiosity.2` | papel e tinta (2Jo 1:12) vs. tinta e pena (3Jo 1:13); as frentes abrem igual com "o presbítero" |

## Livros sem cartas problemáticas nesta revisão

Rute, Esdras, Ester, Jó, Obadias, Jonas, Habacuque, Ageu, Malaquias, Filipenses, Colossenses, 2 Timóteo, Tito, Filemom, Tiago, Judas — e, em História e geografia, apenas uma carta duvidosa (`HIS.0.curiosity.3`).

## Notas de processo

1. Revisão em 12 lotes paralelos por livros + re-auditoria dos 228 prompts reescritos no meio do caminho + varredura de segurança por padrões (pronomes, "outro", "mais", "de novo", papéis genéricos) sobre todo o baralho, que conferiu os lotes e acrescentou 4 cartas. As 4 da varredura aparecem com a indicação "(varredura de segurança)" na coluna de contexto.
2. Três cartas marcadas no início deixaram de ser problema porque foram reescritas durante a auditoria e passam com o texto atual: `HEB.1.curiosity.2` (ganhou "em Hebreus"), `1CO.16.curiosity.1` e `2KI.3.curiosity.1` (reavaliadas válidas). Cartas flagradas que também foram reescritas permanecem no relatório, com o texto do snapshot.
3. Antes de editar as cartas: confira o `prompt` atual no JSON (outra sessão pode ter chegado antes de você). `content/version.txt` só sobe quando o lote corrigido estiver fechado.