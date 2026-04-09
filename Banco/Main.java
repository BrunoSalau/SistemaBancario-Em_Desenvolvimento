import java.util.ArrayList;
import java.util.Scanner;

class Usuario{
    String nome;
    String cpf;
    private String senha;
    Conta conta;

    public Usuario(String nome, String cpf, String senha){
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
    }
    public String pegarSenha(){
        return senha;
    }





    public void setConta(Conta conta){
        this.conta = conta;
    }

}

class Conta{
    String idConta;
    double saldo;
    Usuario usuario;

    public Conta(String idConta,double saldo, Usuario usuario){
        this.idConta = idConta;
        this.saldo = saldo;
        this.usuario = usuario;
    }

}

class Sistema{
    ArrayList<Usuario> usuarios = new ArrayList<>();
    ArrayList<Conta> contas = new ArrayList<>();
    Scanner entradaS = new Scanner(System.in);

    Usuario uAdmin = new Usuario("Admin","000","000"); 
    Conta cAdmin = new Conta("admin",0,uAdmin);

                                                                                                //Registrar-------
    void registrar(){
        //REGISTRO DO NOME
            System.out.print("Digite seu nome completo:");
            String nome = entradaS.nextLine();
                if(nome == null || !nome.matches("[a-zA-ZÀ-ÿ]+( [a-zA-ZÀ-ÿ]+)+")){
                    throw new IllegalArgumentException("Por favor, digite seu nome completo!");
                }


        //REGISTRO DO CPF
            System.out.print("Digite seu CPF:");
            String cpf = entradaS.nextLine();
                if(!cpf.matches("\\d{11}")){
                    throw new IllegalArgumentException("O CPF deve conter 11 numeros!");
                }
                for (Usuario u : usuarios) {
                    if (u.cpf.equals(cpf)) {
                        throw new IllegalArgumentException("Esse CPF ja foi registrado!");
                    }
                }
            
        //REGISTRO DA SENHA
            System.out.print("Qual a sua SENHA:");
            String senha = entradaS.nextLine();
            if(senha == null || !senha.matches(".{8,}")){
                throw new IllegalArgumentException("Sua senha deve conter no minimo 8 caracteres");
            }
            
    
        //CRIAÇÃO DO OBJETO
            Usuario usuario = new Usuario(nome, cpf, senha);
    
            usuarios.add(usuario);
    
            System.out.println("Parabens, voce registrou em nosso banco!");
            System.out.println();
        }

                                                                                                //CriarConta--------
    static int idAconts = 0;   
    Conta criarConta(){
            Scanner entradaCC = new Scanner(System.in);
            String criar;
            System.out.println("Deseja criar uma conta?");
            System.out.println("1 - Sim");
            System.out.println("2 - Não");
            System.out.print("Digite sua escolha:");
            criar = entradaCC.next();
            
            if (!criar.matches("\\d+")) {
                throw new  IllegalArgumentException("Você não pode usar outro caracter alem de numeros para escolher a opção desejada!");
            }
            
            switch (criar) {
                case "1":
                    System.out.println("Otimo! vamos criar a sua conta!");
                    System.out.println("Para começar, digite seu CPF(apenas numero):");
                    String cpf = entradaCC.next();
                    boolean next = false;
                    for (Conta c : contas){
                        if (c.usuario.cpf.equals(cpf)) {
                            throw  new IllegalArgumentException("Já existe uma conta com esse CPF!");
                        }
                    }
                    for (Usuario u  : usuarios) {
                        if (u.cpf.equals(cpf)){
                            next = true;
                            idAconts++;
                            String id = String.format("%05d", idAconts);
                            Conta c = new Conta(id,0,u);
                            contas.add(c);
                            u.conta = c;
                            System.out.println("Parabens, Você sua conta foi criada com Sucesso!");
                            return c;
                        }
                    }
                    if(next == false){
                        throw new IllegalArgumentException("esse CPF não esta registrado!");
                    }
                    
                case "2":
                    System.out.println("Tudo bem... Você pode criar uma conta em outro momento, até mais!"); 
                    
                    return null;
                    
                default:
                    throw new IllegalArgumentException("Essa Opção não existe!");
                    
            }
            





            }
                                                                                                //mostrarConta-------
    void mostrarConta(Conta conta){
        System.out.println("Essas são as informações da sua conta!");
        System.out.println("--------------------------------------");
        System.out.println("Nome: "+conta.usuario.nome);
        System.out.println("CPF: "+conta.usuario.cpf);
        System.out.println("Registro: "+conta.idConta);
        System.out.println("Saldo: "+conta.saldo);


    }
                                                                                                //Logar-------
    public Conta logar(){
        System.out.println("");
        System.out.println("Preciso que voce forneça as seguintes informações apra executarmos seu login!");
        System.out.print("Digite seu CPF:");
        String cpf = entradaS.nextLine();
        System.out.print("Digite sua senha:");
        String senha = entradaS.nextLine();
        for (Usuario u : usuarios) {
            if (u.cpf.equals(cpf)) {
                if (u.conta == null) {
                    System.out.println("Ola "+u.nome+" Vejo que voce não possui uma co2nta!");
                    criarConta();
                    if (u.conta != null) {
                        
                        return u.conta;
                    }
                    System.out.println("TESTESTESTES");
                   
                }


                else if(u.pegarSenha().equals(senha)){
                    System.out.println("Login executado com sucesso!");
                    System.out.println("Bem vindo "+u.nome);
                    return u.conta;
                }
            }
        }
        throw new IllegalArgumentException("Login Incorreto!");
    }
                                                                                                //Painel-------   
    void painel(Conta conta){
        System.out.println("=======Painel do banco=======");
        System.out.println("1 - Deposito");
        System.out.println("2 - Saque");
        System.out.println("3 - Transferencia");
        System.out.println("4 - Consultar Saldo");
        System.out.println("5 - Alterar Senha");
        System.out.println("6 - Dados da Conta");
        System.out.println("7 - Deslogar");
        
    }
}
    




public class Main{
    
public static void main(String[] args) {
    Scanner entrada = new Scanner(System.in);
    Sistema sistema = new Sistema();
    int menu = 0;
    Conta contaLogada = null;
    do {
        try {   
            System.out.println("");  
            System.out.println("");
            System.out.println("");
            System.out.println("Seja bem vindo ao Banco Central!");
            System.out.println("--------------------------------");
            System.out.println("Escolha uma opção");
            System.out.println("1 -> Registrar");
            System.out.println("2 -> Logar");    
            System.out.println("digite a opção desejada:");   
            String RegisLogar = entrada.nextLine();
            if (!RegisLogar.matches("\\d")) {
                throw new IllegalArgumentException("Por favor, escolha uma opção digitando 'NUMERO'!");
            }
            switch (RegisLogar) {
                case "1":
                    //REGISTRAR!!!!
                    boolean nextSistem;
                    //REGISTRAR USUARIO
                    do { 
                        try {
                            sistema.registrar();
                            nextSistem = true;
                        } catch (IllegalArgumentException e) {
                            System.err.println(e.getMessage());
                            nextSistem = false;
                        }
                        
                    } while (nextSistem == false);
            
                    //CRAIR CONTA
                    do { 
                        try {
                            contaLogada = sistema.criarConta();
                            if (contaLogada != null) {
                                sistema.mostrarConta(contaLogada);
                            }
            
            
                            nextSistem = true;
                        } catch (IllegalArgumentException e) {
                            System.err.println(e.getMessage());
                            nextSistem = false;
                        }
                        
                    } while (nextSistem == false);
                    break;
                case "2":
                    //LOGIN!
                    
                        try {
                            contaLogada = sistema.logar();
                            menu = 1;
                        } catch (IllegalArgumentException e) {
                            System.err.println(e.getMessage());
                        }
                    break;
                default:
                    throw new IllegalArgumentException("Essa Opção não existe!");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } 
        } while (menu == 0);


        //PAINEL PRINCIPAL
        System.out.println("=======Painel do banco=======");
        System.out.println("1 - Deposito");
        System.out.println("2 - Saque");
        System.out.println("3 - Transferencia");
        System.out.println("4 - Consultar Saldo");
        System.out.println("5 - Alterar Senha");
        System.out.println("6 - Deslogar");

        //System.out.println("Conta logada:" + contaLogada.usuario);

        
    

    }
}