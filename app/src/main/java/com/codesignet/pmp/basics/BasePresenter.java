package com.codesignet.pmp.basics;

import android.content.Context;

import javax.inject.Inject;

public class BasePresenter<V extends BaseView, I extends BaseInteractour> {

    @Inject
    V mView;
    Context mContext;

    void attachContext(Context mContext) {
        this.mContext = mContext;
    }

    void deAttachContext() {
        mContext = null;
    }

    void attachView(BaseView view) {
        this.mView = (V) view;
    }

    void deAttachView() {
        mView = null;
    }
}
