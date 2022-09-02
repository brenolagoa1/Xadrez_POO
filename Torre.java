package Teste;


//breno lagoa
//770989
import java.io.Serializable;


public class Torre extends Peca implements Serializable{

    public Torre(char cor) {
        super(cor);
    }
     
    
    @Override
    public char desenho(){
        return 'T';
    }
    
    @Override
    public boolean checaMovimento(int li, int ci, int lf, int cf ){     //linha e coluna inicial e final respectivamente
        if(li == lf && cf != ci ){  //movimento horizontal
            return true;
        }else if(li != lf && ci == cf  ){   //movimento vertical
            return true;
        }else{
            return false;
        }
    }
}
