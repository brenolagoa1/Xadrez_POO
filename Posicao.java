package Teste;


//breno lagoa
//770989
import java.io.Serializable;

public class Posicao  implements Serializable{
    
    private int linha;
    private char coluna;
    private char cor;
    private Peca peca;
    private boolean ocupada;

    public Posicao(int linha, char coluna, Peca peca) {    //inicializa a posição inserindo quem ocupa a linha e coluna do parametro snedo a linha e coluna valores entre 0 e 7 devido o tamanho do tabuleiro
        this.linha = linha;
        this.coluna = coluna;
        this.peca = peca;
        
        
        if(linha == ((int) (coluna-65)) ){  //faz a checagem se a inha e a coluna tem mesmo valor sendo assim a cor da posição sera branca
            this.cor = 'b';
        }else if( ((linha + ((int) (coluna-65))))%2 == 1 ){     //se soma da linha e da coluna for impar a cor da posição é preta
            this.cor = 'p';
        }else{
            this.cor = 'b'; // nas outras possibilidades ser branca tambem
        }
        
        if(peca != null){
            this.ocupada = true;   //caso onde tem uma peça na posição
        }else{
            this.ocupada = false;   //caso base onde não tem  peça na posição
        }
    }

    public Peca getPeca() {//retorna a peça
        return peca;
    }

    public void setPeca(Peca peca) {//coloca uma peça
        this.peca = peca;
    }

    public void setOcupada(boolean ocupada) {//coloca q ta ocupada
        this.ocupada = ocupada;
    }

    public boolean getOcupada() {//verifica se esta ocupada
        return ocupada;
    }

    public int getLinha() {//retorna linha
        return linha;
    }

    public char getColuna() {//retorna coluna
        return coluna;
    }

    public char getCor() { //retorna cor
        return cor;
    }
    
    public void colocaPeca(Peca peca){ //coloca uma peça na posiçao
        if(this.getOcupada()){//verifica se ja tem peça caso tenha come ela
            if(this.getPeca().getCor() != peca.getCor()){
                this.getPeca().capturar();
                this.setPeca(peca);
                this.setOcupada(true);
            }
        }else{ //caso contrario so coloca
            this.setPeca(peca);
            this.setOcupada(true);
        }
    }

    public Peca quemOcupa(){       // retorna a peça que  ocupa a posição
        if(this.getOcupada()){
            return this.peca;
        }
        return null;
    }
    
    
}
