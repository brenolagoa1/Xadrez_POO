
package Teste;


//breno lagoa
//770989
import java.io.Serializable;

public class Peao extends Peca implements Serializable{
    
    private boolean prim_movi = true; 

    public Peao(char cor) {
        super(cor);
    }

    private boolean isPrim_movi() {
        return prim_movi;
    }

    private void setPrim_movi(boolean prim_movi) {
        this.prim_movi = prim_movi;
    }
    
    @Override
    public char desenho(){
        return 'P';
    }
    
    @Override
    public boolean checaMovimento(int li, int ci, int lf, int cf ){     //linha e coluna inicial e final respectivamente
        
        if(this.isPrim_movi() == true){
            if( (lf-li == 2 && ci==cf && this.getCor() == 'p') || (lf-li == -2 && ci==cf && this.getCor() == 'b' )){   //checa se quer andar duas casas
                this.setPrim_movi(false);
                return true;
            }else if(ci == cf && this.getCor() == 'b' && lf-li == -1){   //checa movimento para um pra cima de sua posição
                this.setPrim_movi(false);
                return true;
            }else if(ci == cf && this.getCor() == 'p' && lf-li == 1){   //checa movimento para um pra baixo de sua posição
                this.setPrim_movi(false);
                return true;
            } else if( (ci == cf+1 || ci == cf-1) && lf-li == 1 && this.getCor() == 'p'){        //diagonal para cima
                return true;
            }else if( (ci == cf+1 || ci == cf-1) && lf-li == -1 && this.getCor() == 'b'){        //diagonal para baixo
                return true;
            }else{
                return false;
            }
        }else if(ci == cf && this.getCor() == 'b' && lf-li == -1){   //checa movimento para um pra cima de sua posição
            return true;
        }else if(ci == cf && this.getCor() == 'p' && lf-li == 1){   //checa movimento para um pra baixo de sua posição
            return true;
        }else if( (ci == cf+1 || ci == cf-1) && lf-li == 1 && this.getCor() == 'p'){        //diagonal para cima
            return true;
        }else if( (ci == cf+1 || ci == cf-1) && lf-li == -1 && this.getCor() == 'b'){        //diagonal para baixo
            return true;
        }else{
            return false;
        }
    }
    
    
    
    
}
