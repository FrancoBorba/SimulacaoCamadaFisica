/* ***************************************************************
* Autor: Franco Ribeiro Borba
* Matricula........: 202310445
* Inicio...........: 28/08/2024
* Ultima alteracao.: 04/09/2024
* Nome.............: CamadaFisicaTransmissora
* Funcao...........:  Transformar o sinal de onda relativo ao escolhido na GUI 
*************************************************************** */
package model;

import util.DataSingleton;

public class CamadaFisicaTransmissora {
  static DataSingleton data = DataSingleton.getInstance(); // concecta as telas

   public static String formaDeOnda = ""; // utilizada para debug e para gerar a animacao

  


     /* ***************************************************************
  * Metodo: camadaFisicaTransmissora
  * Funcao: transformar os bits nos sinais de onda
  * Parametros: void
  * Retorno: void
  *************************************************************** */
    public static void camadaFisicaTransmissora(int quadro[]){

        formaDeOnda = ""; // reseta a forma de onda a cada
        System.out.println("CAMADA FISICA TRANSMISSORA:");
         int fluxoBrutoDeBits [] = new int[quadro.length]; 

     
      switch (data.getOpcao()) { // verifica pelo Singleton qual vai ser a conversao 
        case 0:{
          fluxoBrutoDeBits = camadaFisicaTransmissoraCodificacaoBinaria(quadro);
          System.out.println("Valor dos bits armazenados no indice(binario) 0: " +fluxoBrutoDeBits[0]);
          System.out.println("Valor dos bits armazenados no indice(binario)  1: " +fluxoBrutoDeBits[1]);
          System.out.println("---------------------------------------------------------");
      
        
          break;
        }
        case 1:{
          fluxoBrutoDeBits = camadaFisicaTransmissoraCodificacaoManchester(quadro);
          System.out.println("Valor dos bits armazenados no indice(manchester) 0: " +fluxoBrutoDeBits[0]);
          System.out.println("Valor dos bits armazenados no indice(manchester) 1: " +fluxoBrutoDeBits[1]);
              System.out.println("---------------------------------------------------------");

          break;
        }
        case 2:{
          fluxoBrutoDeBits = camadaFisicaTransmissoraCodificacaoManchesterDiferencial(quadro);
          System.out.println("Valor dos bits armazenados no indice(manchester diferencial) 0: " +fluxoBrutoDeBits[0]);
          System.out.println("Valor dos bits armazenados no indice(manchester diferencial) 1: " +fluxoBrutoDeBits[1]);
          System.out.println("Valor dos bits armazenados no indice(manchester diferencial) 2: " +fluxoBrutoDeBits[2]);
          System.out.println("Valor dos bits armazenados no indice(manchester diferencial) 3: " +fluxoBrutoDeBits[3]);
          System.out.println("---------------------------------------------------------");
          break;
        }

          
          
      
        default:
          break;
      }
      
      MeioDeComunicacao.meioDeComunicacao(fluxoBrutoDeBits); // chama a proxima camada

      
    }

     /* ***************************************************************
  * Metodo: camadaFisicaTransmissoraCodificacaoBinaria
  * Funcao: apenas gerar uma string com os bits ja que nao e necessario modificar os bits
  * Parametros: vetor de bits
  * Retorno: int[] (vetor de bits)
  *************************************************************** */
     public static int[] camadaFisicaTransmissoraCodificacaoBinaria(int quadro[]){
    int mascara =1; //para verificar cada bit individualmente
    //String formaDeOnda = "" ; // ira conter os bits após a codificacao

    // Como não alteramos o quadro nao é necessario criar um novo vetor para retornar
   int arrayDeRetorno [] = new int[quadro.length]; // esse array sera retornado

   for( int i =0 ; i < quadro.length ; i++){ // passa o quadro para o outro array , devemos fazer isso pois a manipulacao do quadro com a mascara altera o array
      arrayDeRetorno[i] = quadro[i];
  } 


     // Itera sobre cada elemento do quadro.
     /*Operação &: A operação E bit a bit (&) compara cada bit de dois números e retorna 1 se ambos os bits forem 1, e 0 caso contrário. */
    for(int j = 0; j < quadro.length; j++){
      while(quadro[j] > 0){
        if((quadro[j] & mascara) == 0){
          formaDeOnda = "0" + formaDeOnda; // Se o bit atual for 0, adiciona "0" à sequência.
        }//Fim if
        else if((quadro[j] & mascara) == 1){
          formaDeOnda = "1" + formaDeOnda; // Se o bit atual for 1, adiciona "1" à sequência.
        }//Fim else if
        quadro[j] = quadro[j] >> 1; // Move para o próximo bit da direita para a esquerda.
      }//Fim while
    }//Fim for j

    formaDeOnda = "0" + formaDeOnda; // Adiciona um bit "0" no início da sequência

    System.out.println("Forma de onda binaria da palavra: " + formaDeOnda); // Teste da forma de onda( ver se os bits estao certos)
     System.out.println("Tamanho da onda = " + formaDeOnda.length());
   

    return arrayDeRetorno;
  }

      /* ***************************************************************
  * Metodo: camadaFisicaTransmissoraCodificacaoManchester
  * Funcao: transformar os bits em sinais manchester 
  * Parametros: vetor de bits
  * Retorno: int[] (vetor de bits)
  *************************************************************** */
   public static int[] camadaFisicaTransmissoraCodificacaoManchester(int quadro[]){
    int novoQuadro [] = new int[quadro.length * 2]; // vetor com o dobro de tamanho do quadro 
    int mascara = 1; //para verificar cada bit individualmente
    //String formaDeOnda = ""; // Armazena a forma de onda em uma String
    int estadoAtual = 0; // Variavel para armazenar se o bit e 0 ou 1.
   // estado 0 = baixo alto
    // estado 1 = alto baixo
    String formaDeOndaManchester = ""; // utilizada para analisar se a onda esta correta
   
    int contador = 0 ; // como na manchester apenas cabem dois caracteres por indice utilizo essa variavel para controle do indice do novoQuadro
 
    int indiceNQ = 0; // Indice para o novo quadro.
    
    int vezes = 32;
    

    for( int i = 0 ; i < quadro.length ; i++){
   
      if(quadro[i] != 0){
        vezes =32;
      }

      while (vezes > 0) {
        novoQuadro[indiceNQ] = novoQuadro[indiceNQ] << 2; // Desloca 2 bits para esquerda para a entrada do estadoAtual

        if((quadro[i] & mascara) == 0){ // verifica se e 0
          formaDeOnda = "0" + formaDeOnda; // representacao o bit 0 , utilizado para gerar a onda
          formaDeOndaManchester = "01" + formaDeOndaManchester; // representacao em manchester
          estadoAtual = 1; /*Quando o bit é 0 no quadro original ((quadro[i] & mask) == 0), a codificação Manchester é "01". Em termos binários, "01" equivale a 1 (em decimal). */
          novoQuadro[indiceNQ] = novoQuadro[indiceNQ] | estadoAtual; // adiciona no novoQuadro
          contador++;
        } else if((quadro[i] & mascara) == 1){
          formaDeOnda = "1" + formaDeOnda; // representacao o bit 1 , utilizado para gerar a onda
          formaDeOndaManchester = "01" + formaDeOndaManchester;
          estadoAtual = 2; /*Quando o bit é 1 no quadro original ((quadro[i] & mask) == 1), a codificação Manchester é "10". Em termos binários, "10" equivale a 2 (em decimal). */
          novoQuadro[indiceNQ] = novoQuadro[indiceNQ] | estadoAtual; // adiciona no novoQuadro
          contador++;

        }
        
        quadro[i] = quadro[i] >> 1; // faz o deslocamento da direita para esquerda de um bit para ser analisado
        vezes--;
       
 
        if(contador == 16){ // quando o contador bate 16 quer dizer que ja foram 32 bits desta forma é necessario pular o indice do novo vetor
           indiceNQ++; 
           contador = 0;
        }
        
  
      } 
  }
 
    
    System.out.println(formaDeOndaManchester); // Teste da forma de onda( ver se os bits estao certos)
    System.out.println("Tamanho da onda = " + formaDeOndaManchester.length());
    return novoQuadro;
  } 

    /* ***************************************************************
  * Metodo: camadaFisicaTransmissoraCodificacaoManchesterDiferencial
  * Funcao: transformar os bits em sinais manchester diferencial
  * Parametros: vetor de bits
  * Retorno: int[] (vetor de bits)
  *************************************************************** */
 public static int[] camadaFisicaTransmissoraCodificacaoManchesterDiferencial(int quadro[]){

    int novoQuadro [] = new int[quadro.length * 2]; // vetor com o dobro de tamanho do quadro 
    int mascara = 1; //para verificar cada bit individualmente
  //  String formaDeOnda = ""; // Armazena a forma de onda em uma String(vou utilizar para gerar a onda)
    int contador = 0; // como na manchester diferencial apenas cabem dois caracteres por indice utilizo essa variavel para controle do indice do novoQuadro
    int indiceNQ = 0; // Indice para o novo quadro.
    
    // considerando que o bit 0 é o inicial "baixo alto"
    int estadoAtual = 0; // Variavel para armazenar se o bit e 0 ou 1. 
 
  String formaDeOndaManchesterDiferenical = "";

     int vezes = 32;
    
    for(int i = 0 ; i < quadro.length ; i++){
       
       if(quadro[i] != 0){
        vezes =32;
      }


      while (vezes > 0) {

        novoQuadro[indiceNQ]  = novoQuadro[indiceNQ] << 2; // Desloca 2 bits para esquerda para a entrada do estadoAtual

       formaDeOnda= ((quadro[i] & mascara) == 0 ? "0" : "1") + formaDeOnda; // Gera forma de onda para binário

        if((quadro[i] & mascara) == 0){
          if(estadoAtual == 0){
          formaDeOndaManchesterDiferenical = "01" + formaDeOndaManchesterDiferenical; // baixo-alto
          novoQuadro[indiceNQ] = novoQuadro[indiceNQ] | 0; // mantem baixo-alto
          contador++;
          } else{
            formaDeOndaManchesterDiferenical = "10" + formaDeOndaManchesterDiferenical; // alto-baixo
            novoQuadro[indiceNQ] = novoQuadro[indiceNQ] | 1; // vai para  alto-baixo resultando em alto - alto na analise
            contador++;
          }
          
        } else if((quadro[i] & mascara) == 1){
          if(estadoAtual == 0){
            formaDeOndaManchesterDiferenical = "10" + formaDeOndaManchesterDiferenical; // vai para alto-baixo resultando em alto-alto
            novoQuadro[indiceNQ] = novoQuadro[indiceNQ] | 1;
            estadoAtual = 1; // atualiza o estado
            contador++;
          } else{
            formaDeOndaManchesterDiferenical = "01" + formaDeOndaManchesterDiferenical; // transição para baixo-alto
            novoQuadro[indiceNQ] = novoQuadro[indiceNQ] | 0; // transição para baixo-alto
            estadoAtual = 0; // Atualiza o estado
            contador++;
          }

        }
        
        quadro[i] = quadro[i] >> 1; // faz o deslocamento da direita para esquerda de um bit para ser analisado
        vezes--;
       
           if(contador == 16){ // quando o contador bate 16 quer dizer que ja foram 32 bits desta forma é necessario pular o indice do novo vetor
           indiceNQ++; 
           contador = 0;
           estadoAtual=0; 
        }
      }
      
 }

     

    System.out.println(formaDeOndaManchesterDiferenical); // Teste da forma de onda (ver se os bits estão certos)
    System.out.println(formaDeOndaManchesterDiferenical.length());
  
    
    return novoQuadro;
  
   
  }

  
}
