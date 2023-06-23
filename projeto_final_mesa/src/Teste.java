import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/*
NOMES:
- Vitor Gabriel Moreira dos Santos RA: 972311080
- Wendel Victor Santos Xavier RA: 972310140
 */

    public class Teste {
        static Connection conexao = null;
        static Scanner ler = new Scanner(System.in);
        private static Mesa mDadosAtualizados;


        public static void main(String[] args) throws Exception {
            int numMesa, capacidadeMax;
            String  situacaoMesa;

            System.out.println("Digite os dados da mesa");
            System.out.println("Digite o numero da mesa: ");
            numMesa = ler.nextInt();
            System.out.println("Digite a capacidade máxima da mesa: ");
            capacidadeMax = ler.nextInt();
            System.out.println("Digite a situação da mesa: ");
            situacaoMesa = ler.next();


            //INSERINDO UMA MESA
            Mesa m = new Mesa(numMesa, capacidadeMax, situacaoMesa);
            inserirMesa(m);

            //REMOVENDO UMA MESA
            System.out.println("\n Digite o numero da mesa que deseja remover:");
            int numMesaRemover = ler.nextInt();
            try {
                removerMesaPeloNumero(String.valueOf(numMesaRemover));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


            //BUSCANDO UMA MESA
            System.out.println("\nDigite o número da mesa que deseja buscar:");
            String numMesaBuscado = ler.next();
            Mesa mEncontrado;
            try {
                mEncontrado = buscarMesaPeloNumero(numMesaBuscado);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (mEncontrado == null) {
                System.out.println("Mesa não encontrada");
            } else {
                System.out.println("Numero mesa: " + mEncontrado.getNumMesa());
                System.out.println("Capacidade máxima: " + mEncontrado.getCapacidadeMax());
                System.out.print("Situação da mesa: " + mEncontrado.getSituacaoMesa());
                System.out.println("\n");
            }

            System.out.println("Digite a mesa que deseja atualizar:");
            numMesaBuscado = ler.next();
            Mesa mesaExistente = buscarMesaPeloNumero(numMesaBuscado);

            if (mesaExistente != null) {
                boolean sair = false;

                while (!sair) {
                    System.out.println("Digite os novos dados da mesa");
                    System.out.println("Digite o número da mesa");
                    numMesa = ler.nextInt();
                    System.out.println("Digite a capaciade máxima da mesa:");
                    capacidadeMax = ler.nextInt();
                    System.out.println("Digite a situação da mesa:");
                    situacaoMesa = ler.next();

                    Mesa mDadosAtualizados = new Mesa(numMesa, capacidadeMax, situacaoMesa);
                    alterarMesa(mDadosAtualizados);
                    System.out.println("Deseja atualizar mais informações da mesa? (S/N)");
                    String opcaoSair = ler.next();
                    if (opcaoSair.equalsIgnoreCase("N")) {
                        sair = true;
                    }

                }
            }
            else {
                System.out.println("Nenhum mesa encontrada com o número informado.");
            }
        }

        private static void inserirMesa(Mesa mLida) throws Exception {
            conexao = ConexaoBD.getInstance();
            String sql = "insert into mesa (numMesa, capacidadeMax, situacaoMesa)" +
                    "values (?,?,?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, mLida.getNumMesa());
            stmt.setInt(2, mLida.getCapacidadeMax());
            stmt.setString(3, mLida.getSituacaoMesa());

            stmt.execute();
            stmt.close();
        }

        public static void removerMesaPeloNumero(String numeroMesaRemovida) throws Exception {
            conexao = ConexaoBD.getInstance();
            String sql = "delete from mesa where numMesa like ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, numeroMesaRemovida);

            stmt.execute();
            stmt.close();
        }

        public static Mesa buscarMesaPeloNumero(String numeroBuscado) throws Exception {
            conexao = ConexaoBD.getInstance();
            String sql = "select * from mesa where numMesa like ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, numeroBuscado);
            ResultSet resultado; /*importar a classe ResulSet para recuperar
                                                        os dados que o select selecionou*/
            resultado = stmt.executeQuery();
            Mesa m = null;
            if (resultado.next()) {
                m = new Mesa(resultado.getInt("numMesa"),
                        resultado.getInt("capacidadeMax"),
                        resultado.getString("situacaoMesa"));
            }
            resultado.close();
            stmt.close();
            return m;
        }
        public static void alterarMesa(Mesa mSendoAlterado) throws Exception {
            try {
                conexao = ConexaoBD.getInstance();
                String sql = "UPDATE mesa SET numMesa = ?, capacidadeMax = ?, situacaoMesa = ?" + "WHERE numMesa = ?";
                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, mSendoAlterado.getNumMesa());
                stmt.setInt(2, mSendoAlterado.getCapacidadeMax());
                stmt.setString(3, mSendoAlterado.getSituacaoMesa());
                stmt.setInt(4, mSendoAlterado.getNumMesa());

                int linhasAfetadas = stmt.executeUpdate();
                System.out.println("Linhas afetadas: " + linhasAfetadas);
            } catch (SQLException e) {
                e.printStackTrace();
            }

//        stmt.execute();
//        stmt.close();

            /* não feche a conexão com o banco de dados para que possa continuar cadastranho
            conexao.close();
           */
        }

    }


