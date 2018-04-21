package com.example.anhtuan.quanlyphongtro.contract;

public interface IContract {

    interface IOnClickItemPurchaseListener {
        void onClickItemPurchase(int position);

        void onClickItemDeletePurchase(int postition);
    }

    interface IViewLogin {
        void onSuccess();

        void onFailure();

        void checkUserSuccess();

        void checkUserFailure();
    }

    interface IViewPurchase {
        void onSuccess();

        void onFailure();

        void getTokenSuccess();

        void getTokenFailure();

        void getFlagSuccess();

        void getFlagFailure();
    }

    interface IViewMain {
        void getFlagSuccess();

        void getFlagFailure();
    }
}
