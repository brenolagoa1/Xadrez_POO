package Teste;


//breno lagoa
//770989
import java.io.Serializable;

public class Rei extends Peca implements Serializable{

    public Rei(char cor) {
        super(cor);
    }
    
    @Override
    public char desenho(){
        return 'R';
    }
    
    @Override
    public boolean checaMovimento(int li, int ci, int lf, int cf ){     //linha e coluna inicial e final respectivamente
        
        if((li == lf && cf == ci+1) || (li == lf && cf == ci-1) || (lf == li+1 && ci == cf) || (lf == li-1 && ci == cf) ){ //movimentos verticais e horizontais
            return true;
        }else if((lf == li+1 && cf == ci+1) || (lf == li+1 && cf == ci-1) || (lf == li-1 && cf == ci+1) || (lf == li-1 && cf == ci-1)){ //diagonais 
            return true;
        }else{
            return false;
        }
    }
}
