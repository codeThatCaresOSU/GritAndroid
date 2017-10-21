package com.justcorrections.grit.account;

/**
 * Created by Gus on 9/26/2017.
 */

public interface AccountInterface {
    int TYPE_LOGIN = 0;
    int TYPE_RESET = 1;
    int TYPE_CREATE = 2;
    int TYPE_DETAILS = 3;
    int TYPE_ACCOUNT = 4;

    int getType();
}
