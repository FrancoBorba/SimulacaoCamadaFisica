/* ***************************************************************
* Autor: Franco Ribeiro Borba
* Matricula........: 202310445
* Inicio...........: 23/08/2024
* Ultima alteracao.: 23/08/2024
* Nome.............: DataSingleton.java
* Funcao...........: Classe que conecta a troca de informacoes entre telas
*************************************************************** */
package util;

public class DataSingleton {
  private static DataSingleton instance;
  private int opcao;

    /* ***************************************************************
  * Metodo: getInstance
  * Funcao: Garantir que apenas uma instancia seja criada e fornecer um ponto de acesso global
  * Parametros: Nao recebe
  * Retorno: DataSingleton
  *************************************************************** */
  public static DataSingleton getInstance(){
    if(instance == null){
      instance = new DataSingleton();
    }
      return instance;
  }

  /* ***************************************************************
  * Metodo: getOpcao
  * Funcao: Retornar o que foi escolhido no choice box
  * Parametros: Nao recebe
  * Retorno: inteiro
  *************************************************************** */
    public int getOpcao(){
  return opcao;
    }

  /* ***************************************************************
  * Metodo: setOpcao
  * Funcao: determinar uma nova opcao
  * Parametros: inteiro que sera armazenado em opcao
  * Retorno: void
  *************************************************************** */
    public void setOpcao(int opcao){
        this.opcao = opcao;
    }
}
