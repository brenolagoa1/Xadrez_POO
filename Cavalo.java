package Teste;


//breno lagoa
//770989
import java.io.Serializable;

public class Cavalo extends Peca  implements Serializable{

    public Cavalo(char cor) {
        super(cor);
    }
     
    @Override
    public char desenho(){
        return 'C';
    }
    
    @Override
    public boolean checaMovimento(int li, int ci, int lf, int cf ){     //linha e coluna inicial e final respectivamente
        if((li-2 == lf && cf == ci+1) || (li-2 == lf && cf == ci-1) || (lf == li+2 && ci-1 == cf) || (lf == li+2 && ci+1 == cf) ){ //vertical
            return true;
        }else if((lf == li+1 && cf == ci+2) || (lf == li-1 && cf == ci+2) || (lf == li-1 && cf == ci-2) || (lf == li+1 && cf == ci-2)){ //horizontal
            return true;
        }else{
            return false;
        }
    }
}
