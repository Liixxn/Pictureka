package Modelo;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

/**
 * 
 * Cifra las contrasenias
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */
public class Cifrado {

	
	/**
	 * @param pass La contrasenia a cifrar
	 * @return devuelve el cifrado de la contraseña
	 */ 
	public String hashing(String pass) {
        String sha256hex = Hashing.sha256()
                  .hashString(pass, StandardCharsets.UTF_8)
                  .toString();

        return sha256hex;

    }
	
	
}
