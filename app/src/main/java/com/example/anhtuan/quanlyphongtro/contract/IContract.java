package com.example.anhtuan.quanlyphongtro.contract;

import com.example.anhtuan.quanlyphongtro.model.Purchase;

import java.util.List;

public interface IContract {

    interface IOnClickItemPurchaseListener {
        void onClickItemPurchase(int position);

        void onClickItemDeletePurchase(int postition);
    }

    interface IViewLogin {
        void onSuccess();

        void onFailure(String notifi);
    }

    interface ILoginInteractor {
        void onCheckSuccess();

        void onCheckFailure();
    }

    interface IPurchaseInteractor {

        interface IDetailPurchaseInteractor {
            void getPurchaseSuccess(Purchase purchase);

            void getPurchaseFailure();

            void getIdSuccess(int id);

            void getIdFailure();
        }

        interface IPersonalMyPurchaseInteractor {
            void getTokenSuccess(String token);

            void getTokenFailure();

            void getMyPurchaseSuccess(List<Purchase> purchasesList);

            void getMyPurchaseFailure();

            void deleteSuccess();

            void deleteFailure();
        }

        interface IPostPurchaseInteractor {
            void getTokenSuccess(String token);

            void getTokenFailure();

            void getFlagSuccess(Purchase purchase, int id, int flag);

            void getFlagFailure();

            void postSuccess();

            void postFailure();

            void updateSuccess();

            void updateFailure();
        }

        void getPurchaseSuccess(List<Purchase> purchaseList);

        void getPurchaseFailure();

        void getTokenSuccess(String token);

        void getTokenFailure();

    }

    interface IViewPurchase {

        interface IViewDetailPurchase {
            void callPurchaseSuccess(Purchase purchase);

            void callPurchaseFailure();

            void callIdSuccess(int id);

            void callIdFailure();
        }

        interface IViewPersonalMyPurchase {
            void callTokenSuccess(String token);

            void callTokenFailure();

            void callMyPurchaseSuccess(List<Purchase> purchasesList);

            void callMyPurchaseFailure();

            void onDeleteSuccess();

            void ondDeleteFailure();
        }

        interface IViewPostPurchase {
            void callTokenSuccess(String token);

            void callTokenFailure();

            void callFlagSuccess(Purchase purchase, int id, int flag);

            void callFlagFailure();

            void onPostSuccess();

            void onPostFailure();

            void onUpdateSuccess();

            void onUpdateFailure();
        }

        void callPurchaseSuccess(List<Purchase> purchaseList);

        void callPurchaseFailure();

        void callTokenSuccess(String token);

        void callTokenFailure();

    }

    interface IViewMain {
        void getFlagSuccess();

        void getFlagFailure();
    }
}
