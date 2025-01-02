/* ***************************************************************
* Autor: Franco Ribeiro Borba
* Matricula........: 202310445
* Inicio...........: 28/08/2024
* Ultima alteracao.: 01/09/2024
* Nome.............: CamadaAplicacaoTransmissora
* Funcao...........: armazena a mensagem em bits dentro de um array
*************************************************************** */
package model;

public class CamadaAplicacaoTransmissora {
  
  private static int quadro[] = new int[100]; // permite 400 caracteres
  
   /* ***************************************************************
  * Metodo: camadaAplicacaoTransmissora
  * Funcao: transformar a mensagem em bits e armazenar em um vetor
  * Parametros: void
  * Retorno: void
  *************************************************************** */
  public  static void camadaAplicacaoTransmissora(String mensagem){
    
    for (int k= 0; k < quadro.length; k++) { // percorre todo o vetor(ineficiente se o vetor for estatico)
            int valorCombinado = 0; // valor total dos 4 caracteres
            for (int j = 0; j < 4; j++) { // permite apenas 4 caracteres por indice
                int index = (k * 4) + j; // calcular o indice que vai a mensagem
                if (index < mensagem.length()) { // se o index for maior que o vetor nao adiciona
                    int valorASCII =  mensagem.charAt(index); // transforma em ASCII
                    valorCombinado |= (valorASCII << (8 * (3 - j))); // Desloca o valor  e combina
                }
            }
            quadro[k] = valorCombinado; // armazena o valor no indice
        } // até aqui
      System.out.println("-------------------------------------------------");
      System.out.println("Camada AplicacaoTransmissora");
      System.out.println("Valor dos bits armazenados no indice 0: " +quadro[0]);
      System.out.println("Valor dos bits armazenados no indice 1: " +quadro[1]);

      System.out.println("-------------------------------------------------");
      CamadaFisicaTransmissora.camadaFisicaTransmissora(quadro); // chama a proxima camada
  
  }




}
