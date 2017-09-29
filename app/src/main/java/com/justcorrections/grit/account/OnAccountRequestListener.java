package com.justcorrections.grit.account;

/**
 * Created by Gus on 9/29/2017.
 */

public interface OnAccountRequestListener {
    void onResetRequest(String email);

    void onFail(String message);
}
