# offer manager

---

## Objetivo:

Validar a solução pensada para o seguinte problema:

Em um sistema onde o usuario pode assinar ofertas disponibilizadas,
chegou um novo requisito querendo que algumas ofertas não possam ser
assinadas enquanto o usuário estiver assinado em outra determinada oferta.

### Um pouco mais sobre o problema:
- Essa limitação deve ser apenas para algumas ofertas, por isso deve ser possivel o
administador do sistema poder configurar quais ofertas estão nessa regra;
- deve ser possivel criar mais de 1 regra, ou seja, 1 regra limita oferta A e oferta B, 
e outra regra limita oferta X e oferta Y;
- a regra pode incluir mais de 2 ofertas, ou seja, pode existir uma regra que envolva as ofertas A, B e C, fazendo com 
que o usuário possa estar assinado em apenas 1 delas; 

## Descrição:

Nesse sistema é possivel:
- criar oferta;
- excluir oferta;
- consultar oferta;
- criar regra de exclusividade de oferta;
- consultar uma regra de exclusividade;
- consultar quais ofertas não podem ser assinadas a partir de uma oferta;