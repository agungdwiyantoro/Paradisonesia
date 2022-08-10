package com.devfutech.paradisonesia.external.config

/**
 * Created by devfutech on 9/10/2021.
 */
class BaseConfig {
    init {
        System.loadLibrary("credentials")
    }

    external fun getBaseUrlStagingCustomer(): String

    external fun getMidtransClientKeyStagingCustomer(): String
}