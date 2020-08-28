package br.com.hapvida.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.hapvida.dao.RecaptchaDao;
import br.com.hapvida.dto.RespostaDto;

@Service
public class RecaptchaServiceImpl implements RecaptchaService{

	private Logger log = LoggerFactory.getLogger(RecaptchaServiceImpl.class);
	
	@Autowired
	private RecaptchaDao dao;
	
	public static final String RECAPTCHA_SERVICE_URL = "https://www.google.com/recaptcha/api/siteverify";
	
	/**
	 * checks if a user is valid
	 * @param clientRecaptchaResponse
	 * @return true if human, false if bot
	 * @throws Exception 
	 */
	@Override
	public boolean isValid(String clientRecaptchaResponse, String origem) throws Exception {
		
		if (clientRecaptchaResponse == null || "".equals(clientRecaptchaResponse)) {
			return false;
		}
		
		String SECRET_KEY = pesquisarSecretKeyV3();
		
		URL obj = new URL(RECAPTCHA_SERVICE_URL);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		con.setRequestMethod("POST");

		//add client result as post parameter
		String postParams =
				"secret=" + SECRET_KEY +
				"&response=" + clientRecaptchaResponse;

		// send post request to google recaptcha server
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postParams);
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		in.close();

		Gson gson = new GsonBuilder().create();
		RespostaDto resposta = gson.fromJson(response.toString(), RespostaDto.class);

		String regra = parametrizacaoValida();
		Double regraInt = Double.parseDouble(regra);
		
		if(resposta.isSuccess() 
					&& resposta.getScore() >= regraInt) {
			log.info("Recaptcha Score -> "+resposta.getScore()+" Origem: "+origem);
			return true;
		}else {
			log.info("resultado recaptchaV3: não validou, Origem: "+origem);
		}
		return false;
	}
	
	@Override
	public String pesquisarSecretKeyV3() throws Exception{
		try {
			return dao.pesquisaSecretKey();
		} catch (Exception e) {
			throw new Exception("Consulta do RECAPTCHA_SECRET");
		}
	}

	@Override
	public String pesquisarPublicKeyV3() throws Exception {
		try {
			return dao.pesquisarPublicKey();
		} catch (Exception e) {
			throw new Exception("Consulta do RECAPTCHA_PUBLICA");
		}
	}

	@Override
	public String pesquisarRecaptchaFl() throws Exception {
		try {
			return dao.pesquisarRecaptchaFl();
		} catch (Exception e) {
			throw new Exception("Consulta do RECAPTCHA_FL");
		}
	}
	
	@Override
	public String parametrizacaoValida() throws Exception {
		try {
			return dao.parametrizacaoValida();
		} catch (Exception e) {
			throw new Exception("Consulta do RECAPTCHA_SCORE");
		}
	}
	
}
