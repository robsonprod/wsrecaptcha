package br.com.hapvida.service;

public interface RecaptchaService {

	public boolean isValid(String clientRecaptchaResponse, String origem) throws Exception;
	
	public String pesquisarSecretKeyV3() throws Exception;

	public String pesquisarPublicKeyV3() throws Exception;

	public String pesquisarRecaptchaFl() throws Exception;
	
	public String parametrizacaoValida() throws Exception;
	
}
