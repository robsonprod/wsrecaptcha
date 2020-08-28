package br.com.hapvida.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.hapvida.dto.RetornoDto;
import br.com.hapvida.dto.ChaveDTO;
import br.com.hapvida.service.RecaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping(path="autenticar")
@Api(tags={"Autenticação Recaptcha"})
public class RecaptchaController {

	private Logger log = LoggerFactory.getLogger(RecaptchaController.class);
	
	@Autowired
	public RecaptchaService recaptchaService;
	
	@ApiOperation(
		value="Recaptcha Token", 
		notes="Autentica Recaptcha V3 do Google", 
		nickname="validaRecaptcha",
		tags="Autenticação Recaptcha V3"
	)
	@ApiResponses(value={
		@ApiResponse(code=200, message="Recaptcha verificado."),
		@ApiResponse(code=400, message="Valor não enviado."),
		@ApiResponse(code=400, message="Ocorreu um problema.")
	})
	@ResponseBody
	@PostMapping
	@RequestMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> validaRecaptcha(@RequestBody ChaveDTO chave){
		RetornoDto retorno = new RetornoDto();
		try {
			if(chave.getChave() != null) {
				boolean validacao = recaptchaService.isValid(chave.getChave(), chave.getOrigem());
				retorno.setRetorno(validacao ? "true" : "false");
				return new ResponseEntity<>(retorno, HttpStatus.OK);

			}else {
				return new ResponseEntity<String>("Token vazio.", HttpStatus.BAD_REQUEST);
			}
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>("Ocorreu um problema.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Verifica se o serviço está funcionando.")
	@GetMapping
	@RequestMapping(produces = "text/html;charset=UTF-8")
	public ResponseEntity<?> verificacaoUrl() {
		return new ResponseEntity<>("<html><head><h1>OK</h1></head></html>", HttpStatus.OK);
	}
	
}
