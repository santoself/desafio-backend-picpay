package tech.edson.picpay.service;

import org.springframework.stereotype.Service;
import tech.edson.picpay.client.AuthorizationClient;
import tech.edson.picpay.controller.dto.TransferDto;
import tech.edson.picpay.entity.Transfer;
import tech.edson.picpay.exception.PicPayException;

@Service
public class AuthorizationService {

    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public boolean isAuthorized(TransferDto transferDto) {

        var resp = authorizationClient.isAuthorized();

        if(resp.getStatusCode().isError()) {
            throw new PicPayException();
        }

         return resp.getBody().data().authorization();
    }
}
