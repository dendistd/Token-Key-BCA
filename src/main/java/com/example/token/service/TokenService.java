package com.example.token.service;

import java.util.List;

import com.example.token.model.TokenModel;
import com.example.token.payload.token.InputCreateToken;
import com.example.token.payload.token.InputDeleteToken;
import com.example.token.payload.token.InputFindStatus;
import com.example.token.payload.token.InputUpdateToken;
import com.example.token.payload.token.InputUpdateTokenStatus;
import com.example.token.payload.token.ResponseCreateToken;
import com.example.token.payload.token.ResponseDataToken;
import com.example.token.payload.token.ResponseUpdateToken;
import com.example.token.payload.token.ResponseUpdateTokenStatus;

public interface TokenService {
	
	//GET ALL
	public List<TokenModel> getAllToken();
	//CREATE 
	public ResponseCreateToken createToken(InputCreateToken input);
	//DELETE
	public ResponseDataToken deleteTokenBySerialNumber (InputDeleteToken input);

	//UPDATE CABANG
	public ResponseUpdateToken updateTokenCabang (InputUpdateToken input);
		
	public List<ResponseDataToken> findTokenByStatus(InputFindStatus input);
}
