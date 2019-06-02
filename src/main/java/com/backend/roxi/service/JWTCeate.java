package com.backend.roxi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.backend.roxi.bean.User;
import com.backend.roxi.excpetion.MyException;
import com.backend.roxi.utils.SetDate;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Roxi酱
 */
public class JWTCeate {
    private static final String SECRET="roxidiandian";
    private static final String ISSUER="Roxi";
    public static String createToken(User user){
        Algorithm algorithm=Algorithm.HMAC384(SECRET);
        Map<String,Object> map=new HashMap<>();
        map.put("alg","HMAC384");
        map.put("typ","JWT");
        Date date=new Date();
        String token= JWT.create()
                .withHeader(map)
                .withIssuer(ISSUER)
                .withSubject("login")

                .withClaim("id",user.getId())
                .withClaim("name",user.getName())

                .withAudience("user","用户")
                .withIssuedAt(date)
                .withExpiresAt(SetDate.setDates(0,0,0,0,10))
                .sign(algorithm);
        System.out.println("校验登陆"+token);
        return token;
    }

    public static User verifyToken(String token){
        User user=new User();
        try {
            Algorithm algorithm = Algorithm.HMAC384(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);

            Claim id = jwt.getClaims().get("id");
            Claim name = jwt.getClaims().get("name");
            user.setId(id.asInt());
            user.setName(name.asString());

        }catch (JWTVerificationException exception){
            try {
                throw new MyException("400","校验错误");
            } catch (MyException e) {
            }
        }
        return user;
    }
}
