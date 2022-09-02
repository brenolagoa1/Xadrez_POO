package Teste;
import java.util.Scanner;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.EOFException;
import java.util.InputMismatchException;
import java.io.Serializable;


//breno lagoa
//770989
public class Jogo implements Serializable{
    
    private  ObjectOutputStream dado;   //atributo para escrita de arquivos
    private  ObjectInputStream dadoleitura; //atruibuto para a leitura de arquivos
    private int Op = 0; //receberá um quando desejar criar um novo arquivo e 2 para ler um arquivo existente

    private int estado;     //0 indica inicio, 1 xeque, 2 xeque-mate
    private  Tabuleiro tab; 
    private Peca conjPecasJog1[] = new Peca[16];    //conjunto de peças dos jogadores
    private Peca conjPecasJog2[] = new Peca[16];
    private int vezJogador; //recebendo 1 para vez do jogador 1 e 2 para jogador 2
    private Jogador jog1, jog2;     

    public Jogo(){//construtor
        
        for(int i=0; i<8; i++){//configura peao como as primeiras 8 peças
            this.conjPecasJog1[i] = new Peao('b');
            conjPecasJog2[i] = new Peao('p');
        }//configura as peças nos vetores dos jogadores
        this.conjPecasJog1[8] = new Torre('b');
        this.conjPecasJog2[8] = new Torre('p');
        this.conjPecasJog1[9] = new Cavalo('b');
        this.conjPecasJog2[9] = new Cavalo('p');
        this.conjPecasJog1[10] = new Bispo('b');
        this.conjPecasJog2[10] = new Bispo('p');
        this.conjPecasJog1[11] = new Dama('b');
        this.conjPecasJog2[11] = new Dama('p');
        this.conjPecasJog1[12] = new Rei('b');
        this.conjPecasJog2[12] = new Rei('p');
        this.conjPecasJog1[13] = new Bispo('b');
        this.conjPecasJog2[13] = new Bispo('p');
        this.conjPecasJog1[14] = new Cavalo('b');
        this.conjPecasJog2[14] = new Cavalo('p');
        this.conjPecasJog1[15] = new Torre('b');
        this.conjPecasJog2[15] = new Torre('p');
        
        
        
        
        
        
        //inicia a escolha do usuário para jogo
        Scanner sc = new Scanner(System.in);
        boolean arqCriado=false, arquivoRecuperado=false; //flags que checam a criação e recuperação de arquivos
        String arquivo = "xadrez"; //nome padrão
        
        
        System.out.println("Xadrez iniciado");
        System.out.println("Você deseja continuar um jogou ou começar outro?");
        System.out.println("Digite: ");
        System.out.println("1- Para começar um novo jogo.");
        System.out.println("2- Para retomar um jogo salvo.");
        
        while(this.Op != 1 && this.Op != 2){//loop que irá decidir se efetuará a leitura de um arquivo
            try{
                this.Op = sc.nextInt(); //le a opçao do usuário
                sc.nextLine();
                if(this.Op != 1 && this.Op != 2){
                    System.out.println("Digite novamente a opção desejada.");
                }
            }catch(InputMismatchException e){ //pega erro de incmpatibilidade
                sc.nextLine();
                System.out.println("Entrada inválida, digite novamente.");
            }
        }
        
        
        
        
        
        if(this.Op == 1){//caso de criar novo arquivo
            System.out.println("Digite o nome do arquivo que deseja criar:");
            char nomeArqValid = 'N'; //recebe N para não valido e S para valido
            while(nomeArqValid == 'N' && !arqCriado){//loop que checa se o nome inserido é valido
                try{
                    arquivo = sc.nextLine();//le nome do arquivo
                    nomeArqValid = 'S';//coloca s se for valido
                }catch(InputMismatchException e){//caso seja invalida a entrada
                    String a = sc.nextLine();
                    System.out.println("Entrada inválida, digite novamente.");
                }
                
                try{
                    this.dado = new ObjectOutputStream(Files.newOutputStream(Paths.get(arquivo + ".txt"))); //testa se o nome pode ser usado
                    arqCriado=true;
                    System.out.println("Arquivo criado com sucesso.");
                }catch(IOException ioException){
                    String a = sc.nextLine();
                    System.out.println("Nome de programa inválido.");
                }
            }
            
            
            
            //parte que obtem e salva o nome dos jogadores
            String PrimJog="fulano", SegJog="fulano2";
            char nomeValido = 'N'; //recebe N para não valido e S para valido
            
            System.out.println("Insira o nome do primeiro jogador que utilizará as peças brancas:");//colocando nome do primeiro jogador
            while(nomeValido == 'N'){
                try{
                    PrimJog = sc.nextLine();//nome primeiro jogador
                    nomeValido = 'S';
                }catch(InputMismatchException e){   //caso seja invalida a entrada
                    String a = sc.nextLine();
                    System.out.println("Entrada inválida, digite novamente.");
                }
            }
            System.out.println("Insira o nome do segundo jogador que utilizará as peças pretas:");//colocando nome do segundo jogador
            nomeValido = 'N';
            while(nomeValido == 'N'){
                try{
                    SegJog = sc.nextLine(); //nome segundo jogador
                    nomeValido = 'S';
                }catch(InputMismatchException e){//caso seja invalida a entrada
                    String a = sc.nextLine();
                    System.out.println("Entrada inválida, digite novamente.");
                }
            }
            
            
            //coloca o nome dos jogadores e os associa a suas peças
            jog1 = new Jogador(PrimJog, 'b', this.conjPecasJog1);     //cria jogador 1 e coloca sua cor e nome
            jog2 = new Jogador(SegJog, 'p', this.conjPecasJog2);     //cria jogador 2 e coloca sua cor e nome
            
            this.tab = new Tabuleiro(this.conjPecasJog1, this.conjPecasJog2);//cria o tabuleiro com as peças
            this.tab.imp();//imprime 
            this.setVezJogador(1); //coloca qual jogador é a vez
            this.estado = 0;    //coloca o estado de início do jogo
            
            
        }else{
            
            
            System.out.println("Digite o nome do arquivo que deseja recuperar:");
            char nomeArqValid = 'N'; //recebe N para não valido e S para valido
            sc.useDelimiter("\n"); 
            while(nomeArqValid == 'N' && !arquivoRecuperado){ //checa se o nome digitado é valido para ser recuperado
                try{
                    arquivo = sc.nextLine();//entrada do nome do arquivo
                    System.out.println(arquivo);
                    nomeArqValid = 'S';
                }catch(InputMismatchException e){//caso seja invalida a entrada
                    String a = sc.nextLine();
                    System.out.println("Entrada inválida, digite novamente.");
                }
                
                try{//ve se pode ser usado esse nome para resgatar os dados
                    this.dadoleitura = new ObjectInputStream(Files.newInputStream(Paths.get(arquivo + ".txt")));
                    arquivoRecuperado=true;
                    System.out.println("Arquivo recuperado com sucesso.");
                }catch(IOException ioException){
                    sc.nextLine();
                    System.out.println("Nome de programa inválido.");
                }
                if(arquivoRecuperado == false){
                    System.out.println("Digite novamente: ");
                }
            }
            
            
            while(!arqCriado){
                try{
                    this.dado = new ObjectOutputStream(Files.newOutputStream(Paths.get(arquivo + ".txt")));
                    arqCriado=true;
                    System.out.println("Arquivo criado com sucesso.");
                }catch(IOException ioException){
                    String a = sc.nextLine();
                    System.out.println("Nome de programa inválido.");
                }
                if(!arqCriado) {
                    System.out.println("Insira um novo nome para o arquivo: ");
                    arquivo = sc.nextLine();
                }
            }
            this.tab = new Tabuleiro(this.conjPecasJog1, this.conjPecasJog2);
            leJogo(this.estado, this.tab, this.conjPecasJog1, this.conjPecasJog2, this.vezJogador, this.jog1, this.jog2);
            this.tab.imp();
        }
    }  

    public int getEstado() { //retorna o estado do jogo    
        return estado;
    }

    public void setEstado(int estado) { //muda o estado do jogo
        if(estado>=0 && estado<=2)
            this.estado = estado;
        else
            System.out.println("Estado invalido.");
    }

    public int getVezJogador() { //retorna o jogador da vez
        return vezJogador;
    }

    public void setVezJogador(int vezJogador) { //muda o jogador da vez
        if(vezJogador ==1 || vezJogador == 2)
            this.vezJogador = vezJogador;
        else
            System.out.println("Vez inválida.");
    }
    
    
    public void comecarJogo(){
        Scanner sc = new Scanner(System.in);
        
        
        //jogador1
        int li=0, lf=0;//linha e coluna inicial e final do jogador
        char coli=0, colf=0;
        int vetorFugas[] = new int[64];//vetor que salva os possiveis movimentos de fuga
        for(int i=0; i<64; i++){ //inicializa o vetor
            vetorFugas[i] = -1;
        }
        while(this.estado != 2){
            if(this.getEstado() == 0){ // estado normal 
                while(this.getVezJogador() == 1){ //enquanto não for jogada valida não sai do jogador 1
                    boolean valida=false;
                    try{    
                        li = sc.nextInt();
                        coli = sc.next(".").charAt(0);
                        lf = sc.nextInt();
                        colf = sc.next(".").charAt(0);
                        valida = true;
                    }catch(InputMismatchException e){
                        System.out.println("Entrada inválida, digite novamente.");
                        sc.nextLine();
                    }
                    if(valida ){
                        if(!this.tab.checaValidade(li, coli, lf, colf, 'b') ){//checa se o movi não é valido
                            System.out.println("Movimento inválido, insira novamente.");
                        } else{ //se o movimento é valido
                            this.movimenta(li, coli, lf, colf); // movimenta a peça

                            if(this.tab.testaXeque('b', this.conjPecasJog1[12], conjPecasJog2) ){ //verifica se o movimento que ele realiza causa xeque pra ele mesmo
                                if(this.tab.testaXequeMate('b', this.conjPecasJog1[12], conjPecasJog2, vetorFugas)){//causou xeque mate a si mesmo
                                    System.out.println("Movimento inválido, insira novamente.");
                                    this.movimenta(lf, colf, li, coli);
                                }else{//causou xeque a si mesmo
                                    System.out.println("Movimento inválido, insira novamente. ");
                                    this.movimenta(lf, colf, li, coli);
                                }
                            }else{ //caso se movimentar sem causar xeque a si mesmo
                                this.tab.imp();
                                this.salvaJogo(this.estado, this.tab, this.conjPecasJog1, this.conjPecasJog2, this.vezJogador, this.jog1, this.jog2);
                                this.setVezJogador(2);
                            }
                        }
                    }
                }
            }else if(this.getEstado() == 1){ //se estiver em xeque
                System.out.println( this.jog1.getNome() + " você está em xeque.");
                
                while(this.getVezJogador() == 1){ // enquanto for a vez do jogador
                    boolean valida=false;
                    try{    
                        li = sc.nextInt();
                        coli = sc.next(".").charAt(0);
                        lf = sc.nextInt();
                        colf = sc.next(".").charAt(0);
                        valida = true;
                    }catch(InputMismatchException e){//le e testa erro
                        System.out.println("Entrada inválida, digite novamente.");
                        sc.nextLine();
                    }
                    if(valida ){
                        if(!this.tab.checaValidade(li, coli, lf, colf, 'b') ){//checa se não é valido o movimento
                            System.out.println("Movimento inválido, insira novamente.");
                        } else{//checa se é valido
                            this.movimenta(li, coli, lf, colf); //movimenta

                            if(this.tab.testaXeque('b', this.conjPecasJog1[12], conjPecasJog2) ){ //testa xeque e xeque mate para si mesmo se true volta o movimento
                                if(this.tab.testaXequeMate('b', this.conjPecasJog1[12], conjPecasJog2, vetorFugas)){//causou xeque mate a si mesmo
                                    System.out.println("Movimento inválido, insira novamente. ");
                                    this.movimenta(lf, colf, li, coli);
                                }else{//causou xeque a si mesmo
                                    System.out.println("Movimento inválido, insira novamente. ");
                                    this.movimenta(lf, colf, li, coli);
                                }
                            }else{ //caso se movimentar sem causar xeque a si mesmo
                                this.tab.imp();
                                this.salvaJogo(this.estado, this.tab, this.conjPecasJog1, this.conjPecasJog2, this.vezJogador, this.jog1, this.jog2);
                                this.setVezJogador(2);
                            }
                        }
                    }
                }
            }else{
                System.out.println("Xeque-mate para " + this.jog2.getNome() + ".");
                break;
                
            }
            
            
            
            if(this.tab.testaXeque('p', this.conjPecasJog2[12], conjPecasJog1) ){
                if(this.tab.testaXequeMate('p', this.conjPecasJog2[12], conjPecasJog1, vetorFugas)){//causou xeque mate a si mesmo
                    this.setEstado(2);
                }else{//causou xeque a si mesmo
                    this.setEstado(1);
                }
            }else{ //caso se movimentar sem causar xeque a si mesmo
                this.setEstado(0);
            }
            
            this.salvaJogo(this.estado, this.tab, this.conjPecasJog1, this.conjPecasJog2, this.vezJogador, this.jog1, this.jog2);
            
            
            
            //jogador 2
            if(this.getEstado() == 0){
                while(this.getVezJogador() == 2){ //ensquanto jogador 2 
                    boolean valida = false;
                    try{    
                        li = sc.nextInt();
                        coli = sc.next(".").charAt(0);
                        lf = sc.nextInt();
                        colf = sc.next(".").charAt(0);
                        valida = true;
                    }catch(InputMismatchException e){ // le e verifica erro
                        System.out.println("Entrada inválida, digite novamente.");
                        sc.nextLine();
                    }
                    if(valida ){
                        if(!this.tab.checaValidade(li, coli, lf, colf, 'p') ){ //se não for valido retorna
                            System.out.println("Movimento inválido, insira novamente. ");
                        } else{ //se for valido testa se o movimento não causa xeque ou mate pra si mesmo, caso gere irá voltar o movimento
                            this.movimenta(li, coli, lf, colf);

                            if(this.tab.testaXeque('p', this.conjPecasJog2[12], conjPecasJog1) ){
                                if(this.tab.testaXequeMate('p', this.conjPecasJog2[12], conjPecasJog1, vetorFugas)){//causou xeque mate a si mesmo
                                    System.out.println("Movimento inválido, insira novamente.");
                                    this.movimenta(lf, colf, li, coli);
                                }else{//causou xeque a si mesmo
                                    System.out.println("Movimento inválido, insira novamente. ");
                                    this.movimenta(lf, colf, li, coli);
                                }

                            }else{ //caso se movimentar sem causar xeque a si mesmo
                                this.tab.imp();
                                this.salvaJogo(this.estado, this.tab, this.conjPecasJog1, this.conjPecasJog2, this.vezJogador, this.jog1, this.jog2);
                                this.setVezJogador(1);
                            }
                        }
                    }
                }
            }else if(this.getEstado() == 1){ // em xeque
                System.out.println( this.jog1.getNome() + " você está em xeque.");
                    
                while(this.getVezJogador() == 2){
                    boolean valida = false;
                    try{    
                        li = sc.nextInt();
                        coli = sc.next(".").charAt(0);
                        lf = sc.nextInt();
                        colf = sc.next(".").charAt(0);
                        valida = true;
                    }catch(InputMismatchException e){
                        System.out.println("Entrada inválida, digite novamente.");
                        sc.nextLine();
                    }
                    if(valida ){
                        if(!this.tab.checaValidade(li, coli, lf, colf, 'p') ){
                            System.out.println("Movimento inválido, insira novamente. ");
                        } else{
                            this.movimenta(li, coli, lf, colf);

                            if(this.tab.testaXeque('p', this.conjPecasJog2[12], conjPecasJog1) ){
                                if(this.tab.testaXequeMate('p', this.conjPecasJog2[12], conjPecasJog1, vetorFugas)){//causou xeque mate a si mesmo
                                    System.out.println("Movimento inválido, insira novamente. ");
                                    this.movimenta(lf, colf, li, coli);
                                }else{//causou xeque a si mesmo
                                    System.out.println("Movimento inválido, insira novamente. ");
                                    this.movimenta(lf, colf, li, coli);
                                }

                            }else{ //caso se movimentar sem causar xeque a si mesmo
                                this.tab.imp();
                                this.salvaJogo(this.estado, this.tab, this.conjPecasJog1, this.conjPecasJog2, this.vezJogador, this.jog1, this.jog2);
                                this.setVezJogador(1);
                            }
                        }
                    }
                }
            }else{
                System.out.println("Xeque-mate.");
                break;
            }
            
            
            
            if(this.tab.testaXeque('b', this.conjPecasJog1[12], conjPecasJog2) ){ //checa se houve xeque
                if(this.tab.testaXequeMate('b', this.conjPecasJog1[12], conjPecasJog2, vetorFugas)){//causou xeque mate ao inimigo
                    this.setEstado(2);
                }else{//causou xeque ao inimigo
                    this.setEstado(1);
                }
            }else{ //caso se movimentar sem causar xeque 
                this.setEstado(0);
            }
            this.salvaJogo(this.estado, this.tab, this.conjPecasJog1, this.conjPecasJog2, this.vezJogador, this.jog1, this.jog2);
            
            int opc = 0;//opção se irá fechar o jogo
            System.out.println("Digite 1 caso queira fechar o jogo");
            while(true){
                try{    
                    opc = sc.nextInt();//le a opçao
                    break;
                }catch(InputMismatchException e){
                    System.out.println("Entrada inválida, digite novamente.");
                    sc.nextLine();
                }
            
            }
            if(opc == 1){ //se for 1 fecha o arquivo e encerra o programa
                System.out.println("Fechando.");
                try{
                    if(dado != null){
                        dado.close();
                    }
                }catch(IOException ioException){
                    System.out.println("Erro ao fechar o arquivo.");
                }
                if(this.Op == 2){
                    try{
                        if(dadoleitura != null){
                            dadoleitura.close();
                        }
                    }catch(IOException ioException){
                        System.out.println("Erro ao fechar o arquivo.");
                    }
                }
                break;
            }
                
        }
    }
    
    private void movimenta(int li, char ci, int lf, char cf){ // movimenta a peça no tabuleiro
        
        int coli = (int) ci - 65;
        int colf = (int) cf - 65;
        li = li - 1;
        lf = lf - 1;
        
        this.tab.movi(li, coli, lf, colf);
    }
    
    private void salvaJogo(int estado, Tabuleiro tab, Peca conjPecasJog1[], Peca conjPecasJog2[], int vezJogador, Jogador jog1, Jogador jog2){ //salva o jogo 
          try{//escreve os dados do jogo no arquivo
              this.dado.writeInt(this.estado);
              this.dado.writeObject(this.tab);
              this.dado.writeObject(this.conjPecasJog1);
              this.dado.writeObject(this.conjPecasJog2);
              this.dado.writeInt(this.vezJogador);
              this.dado.writeObject(this.jog1);
              this.dado.writeObject(this.jog2);
          }catch(NoSuchElementException elementException){
              System.out.println("Dados inválidos.");
          }catch(IOException ioException){
              System.out.println("Erro ao gravar o arquivo.");
          }
    }
    
    private void leJogo(int estado, Tabuleiro tab, Peca conjPecasJog1[], Peca conjPecasJog2[], int vezJogador, Jogador jog1, Jogador jog2){
          try{//recupera os dados salvos
              this.estado = this.dadoleitura.readInt();
              this.tab = (Tabuleiro) this.dadoleitura.readObject();
              this.conjPecasJog1 =(Peca[]) this.dadoleitura.readObject();
              this.conjPecasJog2 =(Peca[]) this.dadoleitura.readObject();
              this.vezJogador = this.dadoleitura.readInt();
              this.jog1 = (Jogador) this.dadoleitura.readObject();
              this.jog2 =(Jogador) this.dadoleitura.readObject();
              
          }catch(EOFException endOfFileException){
          }catch(ClassNotFoundException classNotFoundException){
              System.out.println("Objeto não encontrado.");
          }catch(IOException ioException){
              System.out.println("Erro ao gravar o arquivo.");
          }
          
          
          
          
    }
    
    
    
    
}


