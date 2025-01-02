/* ***************************************************************
* Autor: Franco Ribeiro Borba
* Matricula........: 202310445
* Inicio...........: 28/08/2024
* Ultima alteracao.: 04/09/2024
* Nome.............: CamadaFisicaTransmissora
* Funcao...........: Desfazer o sinal de onda e transformar em binario de novo
*************************************************************** */
package model;

import util.DataSingleton;

public class CamadaFisicaReceptora {

    static DataSingleton data = DataSingleton.getInstance(); // concecta as telas
    static String formaDeOndaBinaria = ""; // utilizar para gerar a onda
     /* ***************************************************************
  * Metodo: camadaFisicaReceptora
  * Funcao: receber os bits e os transormar em binario
  * Parametros: vetor de bits
  * Retorno: void
  *************************************************************** */
  public static void camadaFisicaReceptora(int quadro[]){
    

    System.out.println("CAMADA FISICA RECEPTORA :");
         int fluxoBrutoDeBits [] = new int[quadro.length]; 

     
      switch (data.getOpcao()) { // verifica pelo Singleton qual vai ser a conversao 
        case 0:{ // binario
          fluxoBrutoDeBits = camadaFisicaReceptoraDecodificacaoBinaria(quadro);
          System.out.println("Valor dos bits armazenados no indice(binario) 0: " +fluxoBrutoDeBits[0]);
          System.out.println("Valor dos bits armazenados no indice(binario)  1: " +fluxoBrutoDeBits[1]);
          System.out.println("---------------------------------------------------------");
      
        
          break;
        }
        case 1:{ // manchester
         fluxoBrutoDeBits = camadaFisicaReceptoraDecodificacaoManchester(quadro);
          System.out.println("Valor dos bits armazenados no indice(manchester) 0: " +fluxoBrutoDeBits[0]);
          System.out.println("Valor dos bits armazenados no indice(manchester) 1: " +fluxoBrutoDeBits[1]);
          System.out.println("---------------------------------------------------------");

          break;
        }
        case 2:{ // manchester diferencial
          fluxoBrutoDeBits = camadaFisicaReceptoraDecodificacaoManchesterDiferencial(quadro);
          System.out.println("Valor dos bits armazenados no indice(manchester diferencial) 0: " +fluxoBrutoDeBits[0]);
          System.out.println("Valor dos bits armazenados no indice(manchester diferencial) 1: " +fluxoBrutoDeBits[1]);
          System.out.println("---------------------------------------------------------");
          break;
        }

          
          
      
        default:
          break;
      }
      CamadaAplicacaoReceptora.camadaDeAplicacaoReceptora(fluxoBrutoDeBits); // chama a proxima camada
}

   /* ***************************************************************
  * Metodo: camadaFisicaReceptoraDecodificacaoBinaria
  * Funcao: transformar os bits em binario , nesse metodo nao fazemos nada pois nao houve conversao
  * Parametros: vetor de bits
  * Retorno: int[] (vetor de bits)
  *************************************************************** */
public static int[] camadaFisicaReceptoraDecodificacaoBinaria(int quadro[]){
    // nao precisa decodificar pois a binaria nao sofre alteracoes
    return  quadro;
  }

     /* ***************************************************************
  * Metodo: camadaFisicaReceptoraDecodificacaoManchester
  * Funcao: transformar os sinais da manchester em binario 
  * Parametros: vetor de bits
  * Retorno: int[] (vetor de bits)
  *************************************************************** */
  public static int[] camadaFisicaReceptoraDecodificacaoManchester(int quadro[]){
    int novoQuadro[] = new int[quadro.length / 2]; // apos a decodificacao manchester o quadro diminui de tamanho
    int indiceNQ = 0; // indice do novo quadro
    int mascara = 3 ; // Máscara para capturar 2 bits de cada vez 
    int contador = 0; // o indice so deve atualizar a cada vezes do for ja que em manchester so cabem 2 caracteres por indice enquanto em binario cabem quatro
    String formaDeOndaBinaria = ""; // utilizar para debug e identificar erros
    int vezes = 16; // a cada 32 bits(2 em 2 ) deve trocar o indice do quadro original
   
   
    
    for(int i = 0 ; i < quadro.length ; i++){
        if(quadro[i] != 0){
        vezes =16;
      }

      
   
    while (vezes > 0) {

      if ((quadro[i] & mascara) == 1) {
        novoQuadro[indiceNQ] = novoQuadro[indiceNQ] << 1; // desloca um bit para esquerda e adiciona 0
        formaDeOndaBinaria = "0" + formaDeOndaBinaria;
        contador++;
       
      } else if((quadro[i] & mascara) == 2){
        novoQuadro[indiceNQ] = (novoQuadro[indiceNQ] << 1); // desloca um bit para esquerda e adiciona 1
        novoQuadro[indiceNQ] = novoQuadro[indiceNQ] | 1;
        formaDeOndaBinaria = "1" + formaDeOndaBinaria ;
        contador++;
       
      }
      
      
      quadro[i] = quadro[i] >> 2; // faz o deslocamento da direita para esquerda de dois bit para ser analisado
      vezes--;
      
      if(contador == 32){ // o indice atualiza apos a analise de 64 bits, porem a analise é feita de 2 em 2      
        indiceNQ++;
        contador = 0;
      }
      
    }
    
 
   }
    

    System.out.println(formaDeOndaBinaria);
    System.out.println("Tamanho da onda = " + formaDeOndaBinaria.length());
    
    return novoQuadro;
  }

       /* ***************************************************************
  * Metodo: camadaFisicaReceptoraDecodificacaoManchesterDiferencial
  * Funcao: transformar os sinais da manchester diferencial em binario 
  * Parametros: vetor de bits
  * Retorno: int[] (vetor de bits)
  *************************************************************** */
  public static int[] camadaFisicaReceptoraDecodificacaoManchesterDiferencial(int quadro[]) {
    // falhei miseravelmente em identificar o erro , as 19:45 deste domingo a noite (ps nao deixei para cima da hora)
    int novoQuadro[] = new int[quadro.length / 2]; // apos a decodificacao manchester o quadro diminui de tamanho
    int indiceNQ = 0; // indice do novo quadro
    int mascara = 3 ; // Máscara para capturar 2 bits de cada vez 
    int mascaraTransicao =1;
    int contador = 0; // o indice so deve atualizar a cada vezes do for ja que em manchester so cabem 2 caracteres por indice enquanto em binario cabem quatro
    String formaDeOndaBinaria = ""; // utilizar para debug e identificar erros
    int vezes = 16; // a cada 32 bits(2 em 2 ) deve trocar o indice do quadro original
   
    

    for(int i =0 ; i < quadro.length ; i++){

        if(quadro[i] != 0){
        vezes =16;
      }


      while (vezes > 0) {

        if((quadro[i] & mascara )== 0){ // analisa o par de bits
            
            quadro[i] = quadro[i] >> 2; // analisa o segundo par de bits
           
            if((quadro[i] & mascaraTransicao )== 0){ // se o proximo par de bits tambem era baixo alto indica transicao ou seja bit 0 (baixo-alto baixo-alto)
              novoQuadro[indiceNQ] = novoQuadro[indiceNQ] << 1;
               contador++;
               formaDeOndaBinaria = "0" + formaDeOndaBinaria;

            } else{
              novoQuadro[indiceNQ] = novoQuadro[indiceNQ] << 1; // indica que o proximo par de bits era alto baixo assim indicando falta de transicao (baixo-alto alto-baixo) 
              novoQuadro[indiceNQ] = novoQuadro[indiceNQ] | 1;
              contador++;
              formaDeOndaBinaria = "0" + formaDeOndaBinaria;
              
            }
        } else if((quadro[i] & mascara) == 1){
             quadro[i] = quadro[i] >> 2;
             if((quadro[i] & mascaraTransicao) == 0){ // se o proximo par de bits for alto baixo indica falta de transicao (baixo-alto alto-baixo) logo se adiciona um bit 1
              novoQuadro[indiceNQ] = novoQuadro[indiceNQ] << 1;  
              novoQuadro[indiceNQ] = novoQuadro[indiceNQ] | 1;
              contador++;
              formaDeOndaBinaria = "1" + formaDeOndaBinaria;
      
             
             } else{ // o proximo par de bits tambem eh alto baixo assim indica falta de transicao logo bit 0    
               novoQuadro[indiceNQ] = novoQuadro[indiceNQ] << 1;
               contador++;    
               formaDeOndaBinaria = "1" + formaDeOndaBinaria;
             }
        }
         vezes--;
      
       
      
      if(contador == 32){ // o indice atualiza apos a analise de 64 bits, porem a analise é feita de 2 em 2      
        indiceNQ++;
        contador = 0;
      }
   


      }

  }
  System.out.println(formaDeOndaBinaria);
    System.out.println("Tamanho da onda = " + formaDeOndaBinaria.length());
 return novoQuadro;

}
}


