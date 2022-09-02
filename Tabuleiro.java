package Teste;


//breno lagoa
//770989
import java.io.Serializable;


public class Tabuleiro  implements Serializable{

    private final Posicao tab[][] = new Posicao[8][8]; //tabuleiro

    public Tabuleiro(Peca conjPecaJog1[], Peca conjPecaJog2[]) {        //cria um tabuleiro inserindo cada peça nele de acordo com sua posição
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){//loop q percorre as posições do vetor e insere as peças no tabuleiro
                if(i == 1){
                   this.tab[i][j] = new Posicao(i, ((char) (j+64)), conjPecaJog2[j]); 
                }else if(i == 6){
                    this.tab[i][j] = new Posicao(i, ((char) (j+64)), conjPecaJog1[j]); 
                }else if(i == 0){
                    this.tab[i][j] = new Posicao(i, ((char) (j+64)), conjPecaJog2[j+8]);
                }else if(i == 7){
                    this.tab[i][j] = new Posicao(i, ((char) (j+64)), conjPecaJog1[j+8]); 
                }else{
                    this.tab[i][j] = new Posicao(i, ((char) (j+64)), null);
                }
                
            }
        }
        
        
    }
    
    
    public void imp(){      //função de impressão do tabuleiro 
        for(int i = -1; i<8; i++){
            if(i == -1){
                System.out.print(" ");
            }else{//coloca o indice
                System.out.print(i+1);
            }
            for(int j = 0; j<8; j++){
                if(i==-1 ){
                    System.out.print(" " +((char) (j+65))); //coloca os caracteres d indice
                }
                if(i>=0 && j>=0){//coloca as peças em suas posiçoes na tela
                    System.out.print("|");
                    //String carac;
                    if(this.tab[i][j].quemOcupa() == null){
                        System.out.print(" ");
                    }else{
                        System.out.print(this.tab[i][j].quemOcupa().desenho());
                    }
                }
            }
            System.out.println("|");
        }
        
    }
    
    public boolean checaValidade(int li, char ci, int lf, char cf, char cor){        //primeiramente ela checa se as entradas de movimento estão dentro da matriz
        
        int coli = (int) ci - 64;
        int colf = (int) cf - 64;
        
            
        if(li < 1 || lf < 1 || coli < 1 || colf < 1 || li > 8 || lf > 8 || coli > 8 || colf > 8 || (li==lf && coli==colf)){  //checa os limites do tabuleiro e se o movimento tenta ir para o mesmo indice
            return false;
        }else{
            if(this.tab[li-1][coli-1].quemOcupa() != null){ // checa se tem algm no local selecionado
                if(cor == this.tab[li-1][coli-1].quemOcupa().getCor()){ // confere se a cor é a mesma
                    if(this.tab[li-1][coli-1].quemOcupa().checaMovimento(li-1, coli-1, lf-1, colf-1)){//verifica se o movimento é valido pela peça
                        if(this.tab[lf-1][colf-1].quemOcupa() != null){ //checa se o alvo tem alguma peça 
                            if(this.tab[li-1][coli-1].quemOcupa().getCor() != this.tab[lf-1][colf-1].quemOcupa().getCor()){ //verifica se a cor bate
                                return this.checaCaminho(li, coli, lf, colf);//retorna true se n tiver peça no caminho
                            }else{
                                return false;
                            }
                        }else{
                          return this.checaCaminho(li, coli, lf, colf);
                        }
                    }else{
                        return false;
                    }
                }else{
                  return false;      
                }
            }else{
                return false;
            }
        } 

    }
    
    public void movi(int li, int ci, int lf, int cf){ //movimenta a peça pelo tabuleiro 
        if(this.tab[li][ci].quemOcupa() != null ){  //se o alvo não for nulo o alvo de partida
           Peca aux = this.tab[li][ci].quemOcupa();
           this.tab[lf][cf].colocaPeca(aux);
           this.tab[li][ci].setPeca(null);
           this.tab[li][ci].setOcupada(false);
        }
    }
    
    public boolean testaXeque(char cor, Peca kingAliado, Peca vetIni[]){
        int lr=0, cr=0;
        
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){ //loops que buscam no tabuleiro a posiçao do rei
                if(this.tab[i][j].quemOcupa()!= null){
                    if( this.tab[i][j].quemOcupa() == kingAliado){ //armazena as coordenadas
                        lr=i;
                        cr=j;
                    }
                }
            }
        }
        
        for(int i = 0; i<8; i++){   //com esse laço buscamos por todo vetor a existencia de peças que causam xeque 
            for(int j = 0; j<8; j++){
                if(this.tab[i][j].quemOcupa()!= null){//se a posiçao ij não for nula
                    
                    for(int k=0; k<16; k++){//loop pra passar o vet inimigo e ver se a peça é inimiga
                        if(this.tab[i][j].quemOcupa() == vetIni[k]){ //checa se é peça inimiga 
                            if(this.checaValidade(i+1, ((char) (j+65)), lr+1, ((char) (cr+65)), vetIni[k].getCor())){ //checaa movimentação
                                return true;
                            }
                        }
                    }
                        
                }
                
            }
        }
        return false;
    }
    
    
    public boolean testaXequeMate(char cor, Peca kingAliado, Peca vetIni[], int vetFuga[]){
        int lr=0, cr=0;
        Posicao vet[];  //vetor que irá armazenar quais peças inimigas estão causando o xeque
        vet = new Posicao[16];
        for(int i=0; i<16; i++){
            vet[i] = null;
        }
        
        for(int i = 0; i<8; i++){//loop que irá buscar no tabuleiro a posição do rei aliado
            for(int j = 0; j<8; j++){
                if(this.tab[i][j].quemOcupa()!= null){
                    if(this.tab[i][j].quemOcupa() == kingAliado){//se é igual ao rei
                        lr=i;
                        cr=j;
                        break;
                    }
                }
            }
        }
        int count=0; //contador que ira dizer quantas peças causam xeque ao rei
        for(int i = 0; i<8; i++){   //com esse laço buscamos por todo vetor a existencia de peças que causam xeque e salvamos em um vetor
            for(int j = 0; j<8; j++){
                if(this.tab[i][j].quemOcupa()!= null){//se a posi do indice ij não for nula checa se é igual as peças inimigas
                    
                    for(int k=0; k<16; k++){//loop pra passar o vet inimigo e ver se a peça é inimiga
                        if(this.tab[i][j].quemOcupa() == vetIni[k]){
                            char colunainicial = ((char) (j+65));
                            if(this.checaValidade(i+1, ((char) (j+65)), lr+1, ((char) (cr+65)), vetIni[0].getCor())){//checa se o movimento é valido, caso sim salva o indice desse inimigo
                                vet[count] =  this.tab[i][j];
                                count++;
                            }
                             
                        }
                    }
                    
                }
            }
        }
        
                
        
        if(count == 1){ //so uma peça causa xeque
            int contFuga = 0;
            
            Posicao vetAliados[];   //vetor com a posição de peças aliadas que matam o causador do xeque
            vetAliados = new Posicao[16];
            int contAliados = 0;
            
            for(int i=0; i<16; i++){ //inicializa o vetor
                vetAliados[i] = null;
            }
            
            
            for(int i = 0; i<8; i++){   //com esse laço buscamos por todo vetor a existencia de peças que matam a peça que está causando xeque
                for(int j = 0; j<8; j++){
                    if(this.tab[i][j].quemOcupa()!= null){//checa se tem alguma peça na posição depois confere a cor caso seja a mesma do rei checa o movimento dessa peça
                        if(this.tab[i][j].quemOcupa().getCor() == kingAliado.getCor() && this.tab[i][j].quemOcupa()!= kingAliado){ //ve se não é o proprio rei
                            if(this.checaValidade(i+1, ((char) (j+65)), vet[0].getLinha()+1, ((char) (vet[0].getColuna()+65)), vet[0].getCor())){ //checa se consegue matar a peça que causa xeque
                                vetAliados[contAliados] =  this.tab[i][j];
                                vetFuga[contFuga+1] = (int) this.tab[i][j].getColuna() - 64;
                                vetFuga[contFuga+2] = vet[0].getLinha();
                                vetFuga[contFuga+3] = (int) vet[0].getColuna()-64;
                                contFuga += 4;
                                contAliados++;
                                return false;  
                                        
                            }
                        }
                    }
                            
                }
                    
                
            }
            
            
            
            
            
            
            boolean ameaca = true; //recebe true caso ainda tenha perigo//****************
            if(contAliados > 0){
                ameaca = false;
            }


            int cont=0;
            Posicao vetBordaRei[]; //SALVA as posiçoes na borda do rei que são possiveis rotas de fuga
            vetBordaRei = new Posicao[8];

            for(int i=lr-1; i<=lr+1; i++){//COLOCA em um vetor as posições ao redor do rei a fim de verificar se pode se mover para elas
                for(int j=cr-1; j<cr+2; j++){
                    if( !(i <0 || j<0 || i>7 || j>7) ){
                        if(i!=lr && j!=cr && !this.tab[i][j].getOcupada()){
                            vetBordaRei[cont]= this.tab[i][j];
                            cont++;
                        }
                    }
                }
            }
            
            for(int i = 0; i<=cont; i++){   //com esse laço confere que essas posiçoes ao redor do rei são seguras de se movimentar, vendo se nenhuma peça inimica causa risco a elas
                if(vetBordaRei[i] != null){
                    if(this.checaValidade(vet[0].getLinha()+1, ((char) (vet[0].getColuna()+65)), vetBordaRei[i].getLinha()+1, ((char) (vetBordaRei[i].getColuna()+65)), vet[0].getCor())){
                                vetBordaRei[i] = null;
                                return false;//retorna false indicando que há rotas de fuga
                    }
                }
            }
                
            
                    
            
            
            boolean haSaida = false; //verifica se ha saida na movimentação do rei************************
            
            for(int i=0; i<=contFuga; i++){ //loop que caso tenha rota de fuga 
                if(vetBordaRei[i] != null){//salva no vetor de fugas as posições do vetor de borda do rei
                    
                        vetFuga[cont] = lr;
                        vetFuga[cont+1] = cr;
                        vetFuga[cont+2] = vetBordaRei[i].getLinha();
                        vetFuga[cont+3] = (int) vetBordaRei[i].getColuna()-64;
                        cont += 4;
                        haSaida = true;
                }
            }
            
            
            
            boolean flagEntraFrente = false; // verifica se alguma peça pode entrar na frente e impedir o xeque*****************
            
            int colunaaux = ((int) vet[0].getColuna()-64); //variaveis de auxilio para verificar se alguma peça pode entara na frente
            int linhaaux = vet[0].getLinha();
            for(int i=0; i<8; i++){
                for (int j = 0; j < 8; j++) {  //loops que rodam em busca de peças aliadas, retornarão false caso ainda haja rota de fugas, logo so xeque
                    if(lr == linhaaux && cr != colunaaux ){  //movimento horizontal
                                            if(colunaaux > cr){ //coluna final maior q do rei
                                                for(int k=cr; k<colunaaux; k++){//nesse caso roda de modo a checar o caminho entre eles
                                                    if(this.checaValidade(lr+1, ((char) (j+65)), lr+1, ((char) (k+65)), cor)){
                                                        flagEntraFrente = true;
                                                        return false;
                                                    }
                                                }
                                            }else{
                                                for(int k=cr; k>colunaaux; k--){//nesse caso roda de modo a checar o caminho entre eles
                                                    if(this.checaValidade(lr+1, ((char) (j+65)), lr+1, ((char) (k+65)), cor)){
                                                        flagEntraFrente = true;
                                                        return false;
                                                    }
                                                }
                                            }
                                        }else if(lr != linhaaux && cr == colunaaux  ){   //movimento vertical
                                            if(lr>linhaaux){//linha do rei maior que da peça que causa xeque
                                                for(int k=lr+1; k<colunaaux; k++){//nesse caso roda de modo a checar o caminho entre eles
                                                    if(this.checaValidade(k+1, ((char) (cr+65)), k+1, ((char) (cr+65)), cor)){
                                                        flagEntraFrente = true;
                                                        return false;
                                                    }
                                                }
                                                
                                            }else{
                                                for(int k=lr-1; k>colunaaux; k--){//nesse caso roda de modo a checar o caminho entre eles
                                                    if(this.checaValidade(k+1, ((char) (cr+65)), k+1, ((char) (cr+65)), cor)){
                                                        flagEntraFrente = true;
                                                        return false;
                                                    }
                                                }
                                                
                                            }
                                        }else if( cr != colunaaux && lr != linhaaux ){   //condiçoes diagonais
                                            
                                            int m = lr;
                                            
                                            if(linhaaux>lr && colunaaux>cr){ //linha e coluna do rei menores
                                                
                                                for(cr = cr+1, m = m+1; cr < colunaaux; cr++, m++){//nesse caso roda de modo a checar o caminho entre eles
                                                    if(this.checaValidade(j+1, ((char) (j+65)), m+1, ((char) (cr+65)), cor)){
                                                        flagEntraFrente = true;
                                                        return false;
                                                    }
                                                }
                                            }else if(linhaaux<lr && colunaaux<cr){//linha e coluna do rei maiores
            
                                                for(cr = cr-1, m = m-1; cr > colunaaux; cr--, m--){//nesse caso roda de modo a checar o caminho entre eles
                                                    if(this.checaValidade(j+1, ((char) (j+65)), m+1, ((char) (cr+65)), cor)){
                                                        flagEntraFrente = true;
                                                        return false;
                                                    }
                                                }

                                            }else if(linhaaux>lr && cr>colunaaux){//linha rei menor e coluna do rei maior
                                                
                                                for(cr = cr-1, m = m+1; cr > colunaaux; cr--, m++){//nesse caso roda de modo a checar o caminho entre eles
                                                    if(this.checaValidade(j+1, ((char) (j+65)), m+1, ((char) (cr+65)), cor)){
                                                        flagEntraFrente = true;
                                                        return false;
                                                    }
                                                }
                                                
                                            }else if(linhaaux<lr && colunaaux>cr){//linha rei maior e coluna do rei menor
                                                
                                                for(cr = cr+1, m = m-1; cr < colunaaux; cr++, m--){//nesse caso roda de modo a checar o caminho entre eles
                                                    if(this.checaValidade(j+1, ((char) (j+65)), m+1, ((char) (cr+65)), cor)){
                                                        flagEntraFrente = true;
                                                        return false;
                                                    }
                                                }
                                                
                                            }
                                        }
                }
            }
            
            
            
            
            
            //caso base de retorno para so xeque
            return false;

        }else{ // if(count >=2) significa basicamente isso logo mais de uma ameaça então so o rei pode se salvar
            
            int cont=-1;
            Posicao vetBordaRei[];
            vetBordaRei = new Posicao[8];

            for(int i=lr-1; i<lr+2; i++){//COLOCA em um vetor as posições ao redor do rei a fim de verificar a seguir se alguma peça inimiga causa perigo para essas posições
                for(int j=cr-1; j<cr+2; j++){
                    if( !(i<0 || i>7 || j<0 || j>7)){//verifica se esta na matriz os indices
                        if(this.tab[i][j] != null){//indice ij se não for nulo pode verificar
                            if(i!=lr && j!=cr && !this.tab[i][j].getOcupada() ){ //verifica se não é o indice do rei e se não esta ocupada
                                if(!this.testaXeque(cor, kingAliado, vetIni)){ //testa se esssa posição causa xeque
                                    vetBordaRei[cont]= new Posicao( i, ((char)j + 64), null);
                                    cont++;
                                }
                            }else if(this.tab[i][j].getPeca().getCor() !=  kingAliado.getCor()){//se ocupada pelo inimigo 
                                vetBordaRei[cont]= new Posicao( i, ((char)j + 64), null);
                                    cont++;
                            }
                        }
                    }
                }
            }
            
            for(int i = 0; i<8; i++){   //com esse laço buscamos por todo vetor a existencia de peças inimigas que podem se mover para as posições ao redor do rei
                for(int j = 0; j<8; j++){
                    if(this.tab[i][j].quemOcupa()!= null){ //se ij não for nulo continua
                        if(this.tab[i][j].quemOcupa().getCor() != kingAliado.getCor()){ //se for inimiga
                            for(int k=0; k<=cont; k++){
                                
                                if(this.tab[i][j].quemOcupa().checaMovimento(i, j, vetBordaRei[k].getLinha(),((int) vetBordaRei[k].getColuna() - 64) )){//checa se a peça pode realizar esse movimento ate a borda
                                    if(this.tab[vetBordaRei[k].getLinha()][((int) vetBordaRei[k].getColuna() - 64)].quemOcupa() != null){ //checa se a posição do vetor de bordas não é nula
                                        if(this.tab[i][j].quemOcupa().getCor() != this.tab[vetBordaRei[k].getLinha()][((int) vetBordaRei[k].getColuna() - 64)].quemOcupa().getCor()){ //verifica se a cor dos dois é diferente
                                            if(this.checaCaminho(vetBordaRei[k].getLinha()+1, ((int) vetBordaRei[k].getColuna() - 64)+1, i+1, j+1)){ //caminho para o movimento está livre
                                                vetBordaRei[k].colocaPeca(this.tab[i][j].quemOcupa());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            boolean haSaida = false;

            for(int i=0; i<=cont; i++){ //busca se ainda há saidas no vetor
                if(!vetBordaRei[i].getOcupada()){
                    vetFuga[cont] = lr;
                    vetFuga[cont+1] = cr;
                    vetFuga[cont+2] = vetBordaRei[i].getLinha();
                    vetFuga[cont+3] = (int) vetBordaRei[i].getColuna()-64;
                    cont += 4;
                    haSaida = true;
                }
            }
            
            return haSaida != true;
        }

    }
    
    
    private boolean checaCaminho(int li, int coli, int lf, int colf){
        int lini = li-1, linf=lf-1;//padronizando ao valor da matriz
        int colini=coli-1, colfim=colf-1;//padronizando ao valor da matriz
        
        switch (this.tab[li-1][coli-1].quemOcupa().desenho()) {
                                    case 'R'://rei
                                    {
                                        return true;
                                    }
                                    case 'C':
                                    {//cavalo
                                        return true;
                                    }
                                    case 'P'://casos especiais do peão
                                    {
                                        
                                        if(coli==colf){
                                            if(lf-li == 2){//movi duplo descendo tabuleiro
                                                if(this.tab[lini+1][colini].quemOcupa() != null){
                                                    return false;
                                                }
                                                if(this.tab[lini+2][colini].quemOcupa() != null){
                                                    return false;
                                                }
                                                return true;
                                            }else if(lf-li==-2){//duplo subindo
                                                if(this.tab[lini-1][colini].quemOcupa() != null){
                                                    return false;
                                                }
                                                if(this.tab[lini-2][colini].quemOcupa() != null){
                                                    return false;
                                                }
                                                return true;
                                            }else{ //simples chcando o alvo
                                                if(this.tab[linf][colini].quemOcupa()!= null){
                                                    return false;
                                                }else{
                                                    return true;
                                                }
                                            }
                                        }else if(li!=lf && coli != colf){ //diagonal checando se o alvo é inimigo
                                            if(this.tab[lf-1][colf-1].quemOcupa()!= null){
                                                if(this.tab[li-1][coli-1].quemOcupa().getCor() == this.tab[lf-1][colf-1].quemOcupa().getCor())
                                                    return false;
                                                else
                                                    return true;
                                            }else{
                                                return false;
                                            }
                                        }
                                    }
                                    default:
                                    {
                                        int flag_valido=0;
                                        
                                        
                                        
                                        if(li == lf && colfim != colini ){  //movimento horizontal
                                            if(colfim>colini){
                                                for(int i=colini; i<colfim; i++){
                                                    if(this.tab[lini][i].getOcupada() && i!=colfim){
                                                        return false;
                                                    }
                                                }
                                            }else{
                                                for(int i=colini; i>colfim; i--){
                                                    if(this.tab[lini][i].getOcupada() && i!=colfim){
                                                        return false;
                                                    }
                                                }
                                            }
                                        }else if(li != lf && coli == colf  ){   //movimento vertical
                                            if(linf>lini){
                                                for(int i=lini+1; i<linf; i++){
                                                    if(this.tab[i][colini].getOcupada()){
                                                        return false;
                                                    }
                                                }
                                            }else{
                                                for(int i=lini-1; i>linf; i--){
                                                    if(this.tab[i][colini].getOcupada()){
                                                        return false;
                                                    }
                                                }
                                            }
                                        }else if( coli != colf && li != lf ){   //condiçoes diagonais
                                            
                                            int i = lini;
                                            
                                            if(linf>lini && colfim>colini){
                                                
                                                for(colini = colini+1, i = i+1; colini < colfim; colini++, i++){
                                                    if(this.tab[i][colini].getOcupada()){
                                                        return false;
                                                    }
                                                }
                                            }else if(linf<lini && colfim<colini){
            
                                                for(colini = colini-1, i = i-1; colini > colfim; colini--, i--){
                                                    if(this.tab[i][colini].getOcupada()){
                                                        return false;
                                                    }
                                                }

                                            }else if(linf>lini && colini>colfim){
                                                
                                                for(colini = colini-1, i = i+1; colini > colfim; colini--, i++){
                                                    if(this.tab[i][colini].getOcupada()){
                                                        return false;
                                                    }
                                                }
                                                
                                            }else if(linf<lini && colfim>colini){
                                                
                                                for(colini = colini+1, i = i-1; colini < colfim; colini++, i--){
                                                    if(this.tab[i][colini].getOcupada()){
                                                        return false;
                                                    }
                                                }
                                                
                                            }
                                        }
                                        
              return true;
            }
        }
    }
}
