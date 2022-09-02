/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Teste;

import java.io.Serializable;

/**


//breno lagoa
//770989 
 */
public abstract class Peca  implements Serializable{
    private boolean capturada;
    private char cor;
    
    public Peca(char cor) { //construtor
        this.capturada = false;
        this.cor = cor;
    }

    public boolean isCapturada() {//retorna se esta capturada
        return capturada;
    }

    private void setCapturada(boolean capturada) {  //captura peça
        this.capturada = capturada;
    }
    
    public void capturar(){ //captura a peça
        this.setCapturada(true);
    }
    
    public char getCor() {//retorna cor
        return cor;
    }

    private void setCor(char cor) { //configura cor
        this.cor = cor;
    }
    
    public abstract char desenho(); //desenho
    
    
    public abstract boolean checaMovimento(int li, int ci, int lf, int cf );     //linha e coluna inicial e final respectivamente
        
        
    
}
