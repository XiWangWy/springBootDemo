package com.bless.Service;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by stephan on 20.03.16.
 */
@Data
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;

    public JwtAuthenticationResponse(String token){
        this.token = token;
    }

}
