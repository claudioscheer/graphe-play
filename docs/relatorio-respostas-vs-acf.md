# Relatório — respostas × tradução ACF 2011

Verificação de fidelidade factual das respostas das cartas contra o texto da **ACF 2011** (`app/src/main/assets/bible/ACF.sqlite`, a mesma base que o app usa: `AcfBible.kt` copia `bible/ACF.sqlite` dos assets e consulta a tabela `verses`).

> Esta checagem é **separada** do relatório de perguntas vagas. Ela nasceu de uma pergunta posterior ("as respostas seguem a ACF?"). Os 12 lotes foram lidos linha a linha (4.059 cartas com versículo); as 5 divergências encontradas foi corrigidas no baralho.

## Cobertura

| Recorte | Cartas |
| --- | --- |
| Total da coleção | 4.410 |
| Com referência a versículo, conferidas contra o texto ACF | 4.059 |
| Fora do escopo da tradução: História e geografia (`HIS`) | 216 |
| Fora do escopo: cartas do livro inteiro (referência = nome do livro) | 135 |

Todas as 4.059 referências existem no banco ACF (nenhum capítulo/versículo quebrado). Em Salmos, o sobrescrito (autor, músico-mor, dedicatário) vem embutido no versículo 1 do banco e foi considerado.

## Método

- A resposta é uma "frase nossa", não o texto do versículo (regra do `content/GUIDE.md`). Portanto a checagem é de **fidelidade factual**, não de literalidade: números, personagens, lugares, ação e atribuição ("quem fez/diz/para quem").
- Regra de aceite: quem lê a pergunta e o texto ACF chega à mesma resposta que a carta registra.
- Tolerância: sinônimos, arcaísmos da ACF, grafias alternativas de nomes (ex.: "Amana"/"Amaná"), formas menos literais.
- Consulta ao contexto imediato (±3 versículos, sobrescrito, início do capítulo) quando o fato depende do entorno do versículo.

## Resultado

**Nenhuma contradição estrita encontrada** e **nenhuma resposta usando leitura de outra tradução** nas 4.059 cartas. O baralho segue a ACF 2011 até nas leituras que divergem de outras versões, por exemplo: "hora terceira" em Marcos 15:25, "setenta" (não 72) em Lucas 10:1, "Gadarenos"/"Gergesenos" conforme cada evangelho, "Betesda" (Jo 5:2), "senhora eleita" (2Jo 1:1), "Apoliom" (Ap 9:11), "Moloque" (Am 5:26), 666 (Ap 13:18).

### Divergências encontradas e corrigidas (5)

| carta | referência | antes | leitura ACF | correção aplicada |
| --- | --- | --- | --- | --- |
| `PSA.101.curiosity.1` | Sl 101:7 | "Quem pratica engano e quem fala mentira." | "O que usa de engano **não ficará dentro da minha casa**; o que fala mentiras **não estará firme perante os meus olhos**." | "Quem usa de engano não fica na casa; quem fala mentiras não fica firme diante dos olhos." |
| `JOB.34.curiosity.1` | Jó 34:20 | "Em que hora os povos são abalados?" → "No meio da noite, de um instante para outro." | "Eles **num momento** morrem; e **até à meia-noite** os povos são perturbados." | Pergunta virou "Até quando os povos são perturbados, segundo Eliú?"; resposta "Até a meia-noite, num momento."; distratores reescritos para responder à mesma pergunta. |
| `REV.1.name.1` | Ap 1:1 | "Quem recebeu a revelação para os servos de Deus, no Apocalipse?" → "João." | "Revelação de Jesus Cristo, **a qual Deus lhe deu** ... e as **notificou a João**, seu servo." | Pergunta virou "A quem o anjo notificou a revelação, no Apocalipse?" (resposta "João" passa a estar certa). |
| `LUK.11.curiosity.1` | Lc 11:5 | "...ao amigo **de madrugada**, em Lucas?" | "...se for procurá-lo **à meia-noite**..." | Pergunta virou "...ao amigo à meia-noite, em Lucas?" (resposta "Três" já estava certa). |
| `ISA.9.name.1` | Is 9:6 | "Deus Forte, Pai da Eternidade e Príncipe da Paz." | "se chamará o seu nome: **Maravilhoso, Conselheiro**, Deus Forte, Pai da Eternidade, Príncipe da Paz." | Resposta completada com os cinco títulos. |

## Fechamento

Os 6 lotes que faltavam (GEN–EXO, 2SA–2KI, 1CR–EST, PRO–ISA, JER–DAN e as cartas de Paulo, 1.989 cartas) foram lidos por inteiro depois do relatório parcial, com triagem prévia dos casos de risco: respostas negativas, cartas de nome com mais de uma pessoa no versículo, e cartas de lugar. Todos os fatos conferem com a ACF; os candidatos numéricos e de nomes eram falsos positivos (ordinais "sétima/terceiro/décimo sexto", aritmética inferida como "seis em cada mão = doze", grafias como "Amana"/"Amaná"). A única divergência nova foi a de Isaías 9:6, já corrigida.

As correções entraram no baralho junto do commit de alinhamento à ACF (`9247737`) e do commit de fechamento (`20720c0`), que também sobe `content/version.txt` de 20 para 21 para o app recarregar as cartas.

