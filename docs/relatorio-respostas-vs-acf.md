# Relatório — respostas × tradução ACF 2011

Verificação de fidelidade factual das respostas das cartas contra o texto da **ACF 2011** (`app/src/main/assets/bible/ACF.sqlite`, a mesma base que o app usa: `AcfBible.kt` copia `bible/ACF.sqlite` dos assets e consulta a tabela `verses`).

> Esta checagem é **separada** do relatório de perguntas vagas. Ela nasceu de uma pergunta posterior ("as respostas seguem a ACF?") e está **incompleta**: 6 dos 12 lotes foram lidos linha a linha; os outros 6 passaram só por conferência mecânica (números e nomes próprios). Detalhe no fim.

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

**Nenhuma contradição estrita encontrada** e **nenhuma resposta usando leitura de outra tradução** nas 4.059 cartas examinadas até agora. O baralho segue a ACF 2011 até nas leituras que divergem de outras versões, por exemplo: "hora terceira" em Marcos 15:25, "setenta" (não 72) em Lucas 10:1, "Gadarenos"/"Gergesenos" conforme cada evangelho, "Betesda" (Jo 5:2), "senhora eleita" (2Jo 1:1), "Apoliom" (Ap 9:11), "Moloque" (Am 5:26), 666 (Ap 13:18).

### Dúvidas encontradas (4, todas para revisar)

| carta | referência | questão | leitura ACF | problema |
| --- | --- | --- | --- | --- |
| `PSA.101.curiosity.1` | Sl 101:7 | "Quem não vai morar na casa do salmista?" → "Quem pratica engano e quem fala mentira." | "O que usa de engano **não ficará dentro da minha casa**; o que fala mentiras **não estará firme perante os meus olhos**." | A resposta funde as duas consequências: o mentiroso não é expulso da casa; ele não permanece diante dos olhos do salmista. |
| `JOB.34.curiosity.1` | Jó 34:20 | "Em que hora os povos são abalados, segundo Eliú?" → "No meio da noite, de um instante para outro." | "Eles **num momento** morrem; e **até à meia-noite** os povos são perturbados." | "Até à meia-noite" indica duração (até), não o instante "no meio da noite"; e "num momento" antecede a meia-noite. |
| `REV.1.name.1` | Ap 1:1 | "Quem recebeu a revelação para os servos de Deus, no Apocalipse?" → "João." | "Revelação de Jesus Cristo, **a qual Deus lhe deu**, para mostrar aos seus servos ... e as **notificou a João**, seu servo." | Quem recebe a revelação de Deus é Jesus; João recebe a notificação pelo anjo. A pergunta induz a resposta errada — corrigir a pergunta ("A quem o anjo notificou...?") ou a resposta. |
| `LUK.11.curiosity.1` | Lc 11:5 | "Quantos pães o homem pediu ao amigo **de madrugada**, em Lucas?" → "Três." | "...se for procurá-lo **à meia-noite**..." | A resposta está certa; a pergunta troca "meia-noite" por "madrugada" (variação de outra tradução ou adaptação). Ajustar a pergunta. |

## Pendências

Lotes lidos linha a linha (2.070 cartas): LEV–DEU, JOS–1SA, JOB–PSA, os 12 profetas menores, MAT–ATOS e HEB–REV. É deste grupo que saíram as 3 primeiras dúvidas do quadro.

Lotes ainda **sem leitura semântica completa** (1.989 cartas — GEN–EXO, 2SA–2KI, 1CR–EST, PRO–ISA, JER–DAN e as cartas de Paulo): passaram por conferência automática de (a) todos os números citados na resposta contra os do texto ACF e (b) todos os nomes próprios da resposta contra o texto/pergunta. **Nenhum conflito apareceu** nos dois filtros; os candidatos levantados eram falsos positivos (ordinais "sétima/terceiro/décimo sexto", aritmética inferida como "seis em cada mão = doze", grafias de nomes). Falta, para esses 6 lotes, a leitura frase a frase que os outros tiveram.

A limitação é operacional: os subagentes revisores bateram no limite de uso do plano gratuito no meio da tarefa ("Free usage limit reached"). A conferência mecânica cobre o risco maior (números e nomes), mas não substitui a leitura completa.
