package Teste;


//breno lagoa
//770989
import java.io.Serializable;

public class Jogador  implements Serializable{
    
    private String nome;
    private char cor;
    private Peca conjPecas[];
    

    public Jogador(String nome, char cor, Peca vetor[]) { //coloca os dados do jogador
        this.nome = nome;
        this.conjPecas = vetor;
        this.cor = cor;
    }
    

    public String getNome() { //retorna o nome do jogador
        return nome;
    }

}
