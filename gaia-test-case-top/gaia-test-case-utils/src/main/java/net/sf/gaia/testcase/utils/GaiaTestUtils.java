package net.sf.gaia.testcase.utils;

import java.io.File;

/**
 * Classe utilitária para gerenciar as constantes dos testes.
 * 
 * @author daniel.joppi
 *
 */
public abstract class GaiaTestUtils {

	/**
	 * Diretório padrão para os testes do gaia.
	 */
	public static final String GAIA_TEST_DIR = System.getProperty("user.dir") + File.separator + "target" + File.separator + ".gaia";
	
	/**
	 * Diretório padrão para os testes com as bibliotecas do gaia.
	 */
	public static final String GAIA_TEST_SERVICE_DIR = GAIA_TEST_DIR + File.separator + "lib";
	
	/**
	 * Diretório temporário padrão para os testes do gaia.
	 */
	public static final String GAIA_TEST_TEMP_DIR = GAIA_TEST_DIR + File.separator + "temp";
	
	/**
	 * Nome do banco de dados para teste.
	 */
	public static final String GAIA_TEST_DB = "gaiatest";
	
	/**
	 * Nome do usuário para acessar o banco de teste.
	 */
	public static final String GAIA_TEST_DB_USER = "gaiatest";
	
	/**
	 * Senha do usuário para acessar o banco de teste.
	 */
	public static final String GAIA_TEST_DB_PASS = "gaiatest";
	
	public static boolean safeBoolean(String obj) {
		if (obj == null)
			return false;
		String l = obj.toLowerCase();
		return l.startsWith("t") || l.startsWith("y") || l.startsWith("s");
	}
}
