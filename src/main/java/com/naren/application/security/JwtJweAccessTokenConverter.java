package com.naren.application.security;

import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.SignedJWT;

public class JwtJweAccessTokenConverter extends JwtAccessTokenConverter {
	
	RSAKey recipientJWK, recipientPublicJWK;
	
	public JwtJweAccessTokenConverter() {
		 try {
	            recipientJWK = new RSAKeyGenerator(2048).keyID("456").keyUse(KeyUse.ENCRYPTION).generate();
	            recipientPublicJWK = recipientJWK.toPublicJWK();
	        } catch (JOSEException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}
	
	
	@Override
	protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		// TODO Auto-generated method stub
		 String jwt = super.encode(accessToken, authentication);
		// jwt is already signed at this point (by JwtAccessTokenConverter)
		 try {
		 SignedJWT parsed = SignedJWT.parse(jwt);
			/*
			 * JWTClaimsSet claims = new JWTClaimsSet.Builder() .claim("email",
			 * "sanjay@example.com") .claim("name", "Sanjay Patel") .build();
			 */
		 Payload payload = new Payload(parsed);
//		 Supported algorithms: [RSA1_5, RSA-OAEP, RSA-OAEP-256]
//		 JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.A128GCMKW, EncryptionMethod.A128GCM).contentType("JWT").build();
		 JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128CBC_HS256);
		// Create JWE object with signed JWT as payload
		 JWEObject jweObject = new JWEObject(header, payload);
		// Encrypt with the recipient's public key
		 JWEEncrypter jweEncrypter= new RSAEncrypter(recipientPublicJWK);
			jweObject.encrypt(jweEncrypter);
			// Serialise to JWE compact form
            String jweString = jweObject.serialize();
            return jweString;
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return jwt;
	}
	
	@Override
	protected Map<String, Object> decode(String token) {
		try {
			// basically treat the incoming token as an encrypted JWT
			EncryptedJWT parse = EncryptedJWT.parse(token);
            // decrypt it
            RSADecrypter dec = new RSADecrypter(recipientJWK);
            parse.decrypt(dec);
            // content of the encrypted token is a signed JWT (signed by
            // JwtAccessTokenConverter)
            SignedJWT signedJWT = parse.getPayload().toSignedJWT();
            // pass on the serialized, signed JWT to JwtAccessTokenConverter
            return super.decode(signedJWT.serialize());
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return super.decode(token);
	}
}