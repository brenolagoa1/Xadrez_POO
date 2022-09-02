package Teste;


//breno lagoa
//770989
import java.io.Serializable;

public class Dama extends Peca  implements Serializable{

    public Dama(char cor) {
        super(cor);
    }
    
    @Override
    public char desenho(){
        return 'D';
    }
    
    @Override
    public boolean checaMovimento(int li, int ci, int lf, int cf ){     //linha e coluna inicial e final respectivamente
        
        int aux1, aux2;
        
        aux1 = li-lf;
        aux2 = ci-cf;
        
        if((li == lf && cf != ci) ){    //cond torre horizontal
            return true;
        }else if((li != lf && ci == cf)  ){ //cond torre vertical
            return true;
        }else if( (aux1 == aux2  || aux1 == aux2 *(-1) ) && (ci != cf && li != lf) ){   //condiçoes do bispo diagonais
            return true;
        }else{
            return false;
        }
    }
}
