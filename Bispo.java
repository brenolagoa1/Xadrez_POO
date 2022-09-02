package Teste;


//breno lagoa
//770989
import java.io.Serializable;

public class Bispo extends Peca  implements Serializable{

    public Bispo(char cor) {
        super(cor);
    }
    
    @Override
    public char desenho(){
        return 'B';
    }
    
    @Override
    public boolean checaMovimento(int li, int ci, int lf, int cf ){     //linha e coluna inicial e final respectivamente
        
        int aux1, aux2;
        
        aux1 = li-lf;
        aux2 = ci-cf;
        
        if( (aux1 == aux2  || aux1 == aux2 *(-1) ) && (ci != cf && li != lf) ){     //diagonais evitando que exista tentativa de mover a peça para o kugar que estava
            return true;
        }else{
            return false;
        }
    }
}
