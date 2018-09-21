package persistencia;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import com.google.gson.Gson;
import servidor.Gerenciador;

public class GerenciadorDePersistencia {
	
	private static final Gson GSON = new Gson();

    public static void gravar(String arquivo, Gerenciador gerenciador) {
        String conteudo = GSON.toJson(gerenciador);
        Path caminho = Paths.get(arquivo);
        try (PrintWriter gravador = new PrintWriter(Files.newBufferedWriter(caminho, Charset.forName("utf8")))) {
            gravador.print(conteudo);
        } catch (IOException excecao) {
            excecao.printStackTrace();
        }
    }
    public static Optional<Gerenciador> ler(String arquivo) {
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(Paths.get(arquivo));
        } catch (IOException excecao) {
            return Optional.empty();
        }
        return Optional.ofNullable(GSON.fromJson(new String(bytes, Charset.forName("utf8")), Gerenciador.class));
    }

}
