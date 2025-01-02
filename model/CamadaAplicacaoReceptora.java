/* ***************************************************************
* Autor: Franco Ribeiro Borba
* Matricula........: 202310445
* Inicio...........: 28/08/2024
* Ultima alteracao.: 04/09/2024
* Nome.............: CamadaAplicacaoReceptora
* Funcao...........: Descodificar os bits e armazenar em uma String
*************************************************************** */
package model;



import util.DataSingleton;

public class CamadaAplicacaoReceptora {


   static DataSingleton data = DataSingleton.getInstance(); // concecta as telas

        /* ***************************************************************
  * Metodo: camadaDeAplicacaoReceptora
  * Funcao: transforma os bits em String
  * Parametros: vetor de bits
  * Retorno: void
  *************************************************************** */
  public static void camadaDeAplicacaoReceptora(int quadro[]){ 
  

    
  int tipoDeDecodificacao =  data.getOpcao();
  
  String mensagem = "";
  int c = 0;
  String desinverter = "";
  int j = 0;
  int n = 0;
  String result = "";
  String mensagem2= "";

  // Verifica se o tipo de decodificação é 1 (Manchester) ou 2 (Manchester Diferencial)
  if(tipoDeDecodificacao == 1 || tipoDeDecodificacao == 2){
    while(n < quadro.length * 4){
      c = quadro[j] & 255; // Compara usando o operador 'and' com o valor 11111111 para obter o byte inferior.
      quadro[j] = quadro[j] >> 8; // Desloca 8 bits para a direita para obter o próximo byte.
      mensagem += (char)c; // Converte o byte em um caractere e adiciona à mensagem.

      n++;

      // Inverte a mensagem a cada dois caracteres (para decodificação Manchester).
      if(n % 2 == 0){
        for(int m = mensagem.length() - 1; m >= 0; m--){
          desinverter += mensagem.charAt(m);
        }
        mensagem2 += desinverter;
        desinverter = "";
        mensagem = "";
      }

      // Verifica se já processou 4 bytes e, em seguida, adiciona a mensagem resultante.
      if(n != 0 && n % 4 == 0 && j < quadro.length - 1){
        j++;
      }
      if(n != 0 && n % 4 == 0 || n == quadro.length * 4){
        result = mensagem + result;
        mensagem = "";
      }
    }
    
    // Atribui a mensagem à mensagem2 e chama aplicacaoReceptora com a mensagem resultante.
    mensagem = mensagem2;
    AplicacaoReceptora.aplicacaoReceptora(mensagem);
  }
  // Tipo de decodificação binária (0)
  else if(tipoDeDecodificacao == 0){
    while(n < quadro.length * 4){
      c = quadro[j] & 255; // Compara usando o operador 'and' com o valor 11111111 para obter o byte inferior.
      quadro[j] = quadro[j] >> 8; // Desloca 8 bits para a direita para obter o próximo byte.
      mensagem += (char)c; // Converte o byte em um caractere e adiciona à mensagem.
      n++;

      // Verifica se já processou 4 bytes e, em seguida, adiciona a mensagem resultante.
      if(n != 0 && n % 4 == 0 && j < quadro.length - 1){
        j++;
      }
      if(n != 0 && n % 4 == 0 || n == quadro.length * 4){
        result = mensagem + result;
        mensagem = "";
      }
    }
    
    // Inverte a mensagem resultante (para decodificação binária) e chama aplicacaoReceptora.
    for(int k = result.length() - 1; k >= 0; k--){
      desinverter += result.charAt(k);
    }
    mensagem = desinverter;
    AplicacaoReceptora.aplicacaoReceptora(mensagem);
  }
}
}
