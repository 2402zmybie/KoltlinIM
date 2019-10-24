package com.hzhztech.koltlinim.contract

interface ContactContract  {

    interface Presenter:BasePresenter {
        fun loadContracts()
    }

    interface View {
        fun onLoadContractSuccess()
        fun onLoadContractsFailed()
    }
}