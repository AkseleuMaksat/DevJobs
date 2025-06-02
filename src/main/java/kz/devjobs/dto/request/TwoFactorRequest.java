package kz.devjobs.dto.request;

import lombok.Data;

@Data
public class TwoFactorRequest {
    private String email;
    private String code;
}
